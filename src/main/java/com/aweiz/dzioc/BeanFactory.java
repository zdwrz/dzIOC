package com.aweiz.dzioc;

import com.aweiz.dzioc.annotations.DzBean;
import com.aweiz.dzioc.annotations.DzConfigure;
import com.aweiz.dzioc.annotations.DzInject;
import org.apache.log4j.Logger;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by daweizhuang on 5/16/16.
 */
public class BeanFactory {

    private static Logger LOGGER = Logger.getLogger(BeanFactory.class);

    private Map<Class, Object> beanContext = new ConcurrentHashMap<Class, Object>();
    private Map<String, Object> beanNames = new ConcurrentHashMap<String, Object>();

    public BeanFactory(String... packageToScan) throws Exception {
       scan(packageToScan);
    }

    public BeanFactory(Class<?> testConfigureClass) throws Exception {
        Annotation[] ann = testConfigureClass.getDeclaredAnnotations();
        for(Annotation a : ann) {
            if (a instanceof DzConfigure) {
                String[] pckgs = ((DzConfigure) a).packagesToScan();
                if(pckgs != null && pckgs.length > 0){
                    scan(pckgs);
                }
                scanClassToInitBean(testConfigureClass);
                return;
            }
        }
        throw new Exception("Bad configure class");
    }

    private void scanClassToInitBean(Class<?> testConfigureClass) {

    }

    public <T> T getBean(String name, Class<T> clazz) {
        return (T)beanNames.get(name);
    }

    public Object getBean(String name) {
        return getBean(name,Object.class);
    }

    public <T> T getBean(Class<T> clazz) {
        for (Object obj : beanContext.values()) {
            if(clazz.isInstance(obj)){
                return (T)obj;
            }
        }
        return (T)beanContext.get(clazz);
    }

    private void scan(String... packageToScan) throws Exception {
        if(packageToScan == null || packageToScan.length < 1){
            //Throw Exception
            throw new Exception("DzIOC - packageToScan is empty");
        }
        for(String pckg: packageToScan){
            String path = "/" + pckg.replace(".","/") ;
            URL url = this.getClass().getResource(path);
            File pckgDir = new File(url.toURI());
            // LOGGER.debug(path);
            if(pckgDir==null || !pckgDir.exists()){
                throw new Exception("DzIOC - Package Doesn't Exist.");
            }
            recursiveScan(pckgDir,pckg);
            resolveDependencies();
        }
        LOGGER.debug(beanContext.size());
        LOGGER.debug(beanNames.size());
    }
    private void resolveDependencies() throws Exception {
        for(Object bean : beanContext.values()){
            Class clazz = bean.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                Annotation[] ann = f.getDeclaredAnnotations();
                for(Annotation a : ann){
                    if(a instanceof DzInject){
                        Object obj;
                        if(!((DzInject) a).value().trim().equals("")){
                         //injection with named bean
                            obj = this.getBean(((DzInject) a).value());
                        }else {
                            //injection with type
                            obj = this.getBean(f.getType());
                        }
                        if (obj == null) {
                            throw new Exception("No such bean found :" + f.getType());
                        } else {
                            f.setAccessible(true);
                            f.set(bean, obj);
                            LOGGER.debug("Dependency injected :" + obj);
                        }
                    }
                }
            }
        }
    }

    /**
     * Create all the beans with annotation @DzBean.
     * @param pckgDir
     * @param pckg
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
                LOGGER.debug("Created:"+ beanName + " from class:"+ classFile);
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
}
