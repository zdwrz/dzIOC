package com.aweiz.dzioc;

import com.aweiz.dzaop.AOPMethodAdvisor;
import com.aweiz.dzaop.DynamicProxyHandler;
import com.aweiz.dzaop.annotations.DzAspect;
import com.aweiz.dzioc.Exceptions.ConfigurationNotAcceptedException;
import com.aweiz.dzioc.Exceptions.NoBeanFoundException;
import com.aweiz.dzioc.Exceptions.PackageErrorException;
import com.aweiz.dzioc.annotations.DzBean;
import com.aweiz.dzioc.annotations.DzConfigure;
import com.aweiz.dzioc.annotations.DzInject;
import org.apache.log4j.Logger;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bean Factory module.
 * Bean creation and dependency injection.
 * Annotation configured.
 * Support Interface/Named Bean injection.
 * Auto-scan packages including sub-packages.
 * Beans without a name will be singleton.
 * Beans with names that have the same type will be created separately.
 * Configuration Class will be loaded. Use {@link com.aweiz.dzioc.annotations.DzBean @DzBean} to define a bean.
 *
 * Dependencies will be resolved after the beans are firstly scanned.
 * Use {@link com.aweiz.dzioc.annotations.DzInject @DzInject} to perform the DI.
 *
 * Created by daweizhuang on 5/16/16.
 */
public class BeanFactory {

    private static Logger LOGGER = Logger.getLogger(BeanFactory.class);

    /**
     * All beans with different type will be saved here.
     * Key - class type
     * Value - bean
     */
    private Map<Class, Object> beanContext = new ConcurrentHashMap<Class, Object>();

    /**
     * All beans including beanContext will be saved here.
     * Key - name of the beans, if indicated in @DzBean, the name will used, otherwise, the name is Class Qualified name.
     * Value - the bean
     * The bean will be share in both beanContext and beanNames. No duplicated objects are created.
     * This should be bigger than beanContext since here we can have multiple beans with same type but different names.
     */
    private Map<String, Object> beanNames = new ConcurrentHashMap<String, Object>();

    /**
     * To initialize the beans using packages array.
     * Notice : No configuration class will be taken into consideration.
     * @param packageToScan as name indicated
     * @throws Exception
     */
    public BeanFactory(String... packageToScan) throws Exception {
        scan(packageToScan);
        resolveDependencies();
    }

    /**
     * To initialize the beans using Configuration class with Annotation {@link com.aweiz.dzioc.annotations.DzConfigure @DzConfigure}
     * It will
     * 1. scan the packages defined inside {@link com.aweiz.dzioc.annotations.DzConfigure @DzConfigure} packagesToScan
     * 2. scan all the method annotated with {@link com.aweiz.dzioc.annotations.DzBean @DzBean} to initialize the beans.
     * 3. resolve the Dependencies.
     * @param configClass the configClass
     * @throws Exception
     */
    public BeanFactory(Class<?> configClass) throws Exception {
        Annotation[] ann = configClass.getDeclaredAnnotations();
        for(Annotation a : ann) {
            if (a instanceof DzConfigure) {
                String[] pckgs = ((DzConfigure) a).packagesToScan();
                if(pckgs != null && pckgs.length > 0){
                    scan(pckgs);
                }
                scanClassToInitBean(configClass);
                resolveDependencies();
                LOGGER.debug(beanContext.size() +" bean(s) created as singleton");
                LOGGER.debug(beanNames.size() + " bean(s) created as named bean");
                return;
            }
        }
        throw new ConfigurationNotAcceptedException("Bad configure class");
    }

    /**
     * It will scan all method inside the configuration class to create the beans.
     * Both beanContext and beanNames will be set.
     * @param configClass the configClass
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    private void scanClassToInitBean(Class<?> configClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Method[] methods = configClass.getDeclaredMethods();
        Object configClassObj = configClass.newInstance();
        for (Method m : methods) {
            Annotation[] ann = m.getDeclaredAnnotations();
            for(Annotation a : ann) {
                if (a instanceof DzBean) {
                    m.setAccessible(true);
                    Object obj = m.invoke(configClassObj,null);
                    Class returnType = m.getReturnType();
                    beanContext.put(returnType, obj);
                    String beanName = ((DzBean) a).value();
                    beanName = beanName.trim().equals("")?returnType.toString():beanName;
                    beanNames.put(beanName,obj);
                }
            }
        }
    }

    /**
     * A helper method that get bean from the beanNames using the name of the bean.
     * return the Bean with a specific type.
     * @param name name of the bean
     * @param clazz type of bean
     * @return bean of Type T with the specific name.
     */
    public <T> T getBean(String name, Class<T> clazz) {
        return (T)beanNames.get(name);
    }

    /**
     * A helper method that get bean from the beanNames using the name of the bean.
     * return the Object typed bean.
     * @param name name of the bean
     * @return bean of Type Object with the specific name.
     */
    public Object getBean(String name) {
        return getBean(name,Object.class);
    }

    /**
     * A helper method that get bean from the beanNames with the specific type T.
     * If there is no exact type of the injection type, to implement the interface injection,
     * if the injection type is a interface or superclass, it will traverse the whole map to find a suitable type.
     * @param clazz type of bean
     * @return bean of Type T
     */
    public <T> T getBean(Class<T> clazz) {
        if(beanContext.get(clazz) == null) {
            for (Object obj : beanContext.values()) {
                if (clazz.isInstance(obj)) {
                    return (T) obj;
                }
            }
        }
        return (T)beanContext.get(clazz);
    }

    /**
     * Scan all packages to initialize the beans.
     * Recursion is used.
     * First it will convert the package into file system folder.
     * Then iterate the files and folders.
     * @param packageToScan packages to be scanned.
     * @throws Exception
     */
    private void scan(String... packageToScan) throws PackageErrorException{
        if(packageToScan == null || packageToScan.length < 1){
            //Throw Exception
            throw new PackageErrorException("DzIOC - packageToScan is empty");
        }
        for(String pckg: packageToScan){
            String path = "/" + pckg.replace(".","/") ;
            URL url = this.getClass().getResource(path);
            File pckgDir = null;
            try {
                pckgDir = new File(url.toURI());
            }catch (URISyntaxException e){
                throw new PackageErrorException("DzIOC - packageToScan has wrong URL");
            }
            // LOGGER.debug(path);
            if(pckgDir==null || !pckgDir.exists()){
                throw new PackageErrorException("DzIOC - Package Doesn't Exist.");
            }
            recursiveScan(pckgDir,pckg);
        }
    }

    /**
     * Create all the beans with annotation @DzBean recursively.
     * @param pckgDir the folder to traverse.
     * @param pckg package name
     */
    private void recursiveScan(File pckgDir, String pckg) {
        if(pckgDir.isDirectory()){
            for(File iF:pckgDir.listFiles()) {
                if(iF.isDirectory()){
                    recursiveScan(iF, pckg +"." +iF.getName());
                }else{
                    //LOGGER.debug("Scanned Class " + iF.getName());
                    initBean(iF, pckg);
                }
            }
        }else{
            LOGGER.debug("Scanned Class " + pckgDir.getName());
            initBean(pckgDir, pckg);
        }
    }

    /**
     * It will convert the file name into class qualified name. If class has {@link com.aweiz.dzaop.annotations.DzAspect @DzAspect} annotation
     * AOP will be processed
     * @param iF the file to be loaded and instantiated.
     * @param pckg the package name of the class
     */
    private void initBean(File iF, String pckg) {
        String classFile = pckg + "."+iF.getName();
        classFile = classFile.substring(0,classFile.lastIndexOf("."));
        try {
            Class clazz = Class.forName(classFile);
            if(isBeanAnnoted(clazz)) {
                Object bean = clazz.newInstance();
                beanContext.put(clazz, bean);
                String beanName = getBeanName(clazz);
                beanNames.put(beanName,bean);
                LOGGER.debug("Bean Created:"+ beanName + " from class:"+ classFile);
            }else if(isAspectAnnoted(clazz)){
                processAspect(clazz);
                LOGGER.debug("AOP Created:"+ clazz.getName() + " from class:"+ classFile);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (InstantiationException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Bean is aspect.
     * @param aspectClass - type of the aspect
     */
    private void processAspect(Class<?> aspectClass) throws IllegalAccessException, InstantiationException {
        //scan the class
        //find the @DzAdvice
        //create Aspect object
        //set it into AOPMethodAdvisor
        //Object aspectBean = aspectClass.newInstance();

    }

    /**
     * It will traverse the whole beanNames to get the field with {@link com.aweiz.dzioc.annotations.DzBean @DzBean}, then
     * perform Dependency Injection.
     * Use beanNames instead of beanContext is becuase beanNames contains all the beans.
     * @throws Exception - if the dependency is not found.
     */
    private void resolveDependencies() throws NoBeanFoundException {
        for(Object bean : beanNames.values()){
            Class clazz = bean.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                Annotation[] ann = f.getDeclaredAnnotations();
                for(Annotation a : ann){
                    if(a instanceof DzInject){
                        Object obj;
                        if(!((DzInject) a).value().trim().equals("")){
                            //injection with named bean
                            obj = DynamicProxyHandler.newInstance(this.getBean(((DzInject) a).value()));
                        }else {
                            //injection with type
                            obj = DynamicProxyHandler.newInstance(this.getBean(f.getType()));
                        }
                        if (obj == null) {
                            throw new NoBeanFoundException("No such bean found :" + f.getType());
                        } else {
                            f.setAccessible(true);
                            try {
                                f.set(bean, obj);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                                throw new NoBeanFoundException("IllegalAccessException :" + e+" // " + f.getType());
                            }
                            //LOGGER.debug("Dependency injected :" + obj);
                        }
                    }
                }
            }
        }
    }

    /**
     * return the bean name in {@link com.aweiz.dzioc.annotations.DzBean @DzBean}
     * or if not defined, the qualified name of the class.
     * @param clazz - type of the bean
     * @return bean name
     */
    private String getBeanName(Class clazz) {
        String beanName = clazz.getName();
        Annotation[] ann = clazz.getDeclaredAnnotations();
        for (Annotation a : ann) {
            if(a instanceof DzBean){
                if(!"".equals(((DzBean) a).value().trim())){
                    beanName = ((DzBean) a).value();
                }
            }
        }
        return beanName;
    }

    /**
     * return true if the Class has annotation {@link com.aweiz.dzioc.annotations.DzBean @DzBean}
     * @param clazz the class type
     * @return if the class has {@link com.aweiz.dzioc.annotations.DzBean @DzBean} annotation
     */
    private boolean isBeanAnnoted(Class clazz) {
        boolean res = false;
        Annotation[] ann = clazz.getDeclaredAnnotations();
        for (Annotation a : ann) {
            if(a instanceof DzBean){
                res = true;
            }
        }
        return  res;
    }

    /**
     * return true if the Class has annotation {@link com.aweiz.dzaop.annotations.DzAspect @DzAspect}
     * @param clazz the class type
     * @return if the class has {@link com.aweiz.dzaop.annotations.DzAspect @DzAspect} annotation
     */
    private boolean isAspectAnnoted(Class clazz) {
        boolean res = false;
        Annotation[] ann = clazz.getDeclaredAnnotations();
        for (Annotation a : ann) {
            if(a instanceof DzAspect){
                res = true;
            }
        }
        return  res;
    }
}
