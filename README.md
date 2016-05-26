## Description
A IOC/AOP framework created to replace the currently used IOC framework in dzWiKi project.

## Bean Factory module.
Bean creation and dependency injection.
Annotation configured.
Support Interface/Named Bean injection.
Auto-scan packages including sub-packages.
Beans without a name will be singleton.
Beans with names that have the same type will be created separately.

Annotations
@DzBean  -- used to define a bean in the configure class and used in any class which will be created as a bean.
         -- Can hava a name @DzBean("nameOfBean").
@DzInject
         -- Perform injection, by default if type injection.
         -- Can have a value to indicate the name of the injected bean @DzInject("nameOfBean")
@DzConfigure
         -- Indicate the bean is used for configuration
         -- Inside it, methods with @DzBean annotation can be used to create bean which we don't have access to the source code.
         -- Duplicated type will override. Only the last one will be there. Bean with names won't be removed.
         
## AOP
Currently the AOP is just a prototype. The limitations are:
1. Only the classes with interfaces can be proxied.
2. Only the injected Bean can be proxied.
3. The Aspect cannot overlap which means only one aop can be applied to a method.
4. With all limitations of Java Dynamic Proxy interface.
5. The pointcut checking is just to check if the method name contains the pointcut.


Annotations
@DzAspect
         -- Used to define a class as aspect class.
@DzAdvice
         -- Used to define a pointcut and advice above a method.