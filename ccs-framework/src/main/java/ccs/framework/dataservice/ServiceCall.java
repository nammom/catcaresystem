package ccs.framework.dataservice;


import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ccs.framework.util.StringUtility;




/**
 * bean으로 등록 되어 , Service를 Service Layer이나 Controller, jsp, jstl에서 동적으로 call 하여 사용하도록 한다.
 * jstl에서는 jstl 태그롤 통하여 콜한다.
 * @author Administrator
 *
 */
public class ServiceCall {

	private static final Logger logger = LogManager.getLogger(ServiceCall.class);
	
	
	private List<Method> methods = null;
	
	private Object instance;
	
	private Object data = null;
	
	public Object getInstance() {
		return instance;
	}

	public void setInstance(Object instance) {
		this.instance = instance;
	}
	
	private String methodName;
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName){
		return (T)ApplicationContextProvider.getApplicationContext().getBean(beanName);
	}
	
	
	public String getMethodName() {
		return methodName;
	}

	

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}


	public ServiceCall(){
		this((Object)null);
	}
	
	public ServiceCall(Object beanInstance){
		this(beanInstance,null);
	}
	public ServiceCall(String beanName){
		this(beanName,null);
	}
	public ServiceCall(String beanName, String methodName){
		this(ApplicationContextProvider.getApplicationContext().getBean(beanName),methodName);
	}
	
	public static Object executeMethodWithArrayList(String beanName, String methodName,List<Object> parameters) throws Exception{
		Object[] arrayParameters = null;
		if(parameters != null){
			arrayParameters = new Object[parameters.size()];
			for(int i=0;i < parameters.size();i++){
				arrayParameters[i] = parameters.get(i);
			}
			return executeMethodWithArray(beanName,methodName,arrayParameters);
		} else{
			return executeMethod(beanName,methodName);
		}
	}
	
	public static Object executeMethodWithArray(String beanName, String methodName, Object[] parameters) throws Exception{
		Object instance = ApplicationContextProvider.getApplicationContext().getBean(beanName);
		List<Method> methods = new ArrayList<Method>();
		Method executeMethod = null;
		Object executeParemeter[] = null;
		if(instance != null){
			Class<? extends Object> cls  = instance.getClass();
			java.lang.reflect.Method methodlist[] = cls.getDeclaredMethods();
			if(cls != null && !StringUtility.isNullOrBlank(methodName)){
				for (int i = 0; i < methodlist.length; i++) {
					java.lang.reflect.Method m = methodlist[i];
					if(StringUtility.isEqual(m.getName(), methodName)){
						/*파라미터 갯수 체크*/
						Class<?>[] parameterTypes = m.getParameterTypes();
						if(parameters.length == parameterTypes.length){
							Object arglist[] = new Object[parameterTypes.length+1];
							boolean matchedParameter = true;
							
							int iParameter = 1;
							arglist[0] = instance;
							for(Class<?> parameterType : parameterTypes){
								if(parameters[iParameter-1] != null){
									// try cast
									if(!isCompatible( parameters[iParameter-1], parameterType)){
										try{
											arglist[iParameter] = ConvertUtils.convert(parameters[iParameter-1], parameterType);
										} catch(Exception ex){
											matchedParameter = false;
										} 
									} else{
										arglist[iParameter] = parameters[iParameter-1];
									}
								} else{
									arglist[iParameter] = null;
								}
								i++;
							}
							
							if(matchedParameter){
								executeMethod = m;
								executeParemeter = arglist;
								break;
							}
							
						}
					}
				}
				
				if(executeMethod != null){
					MethodHandle handle = MethodHandles.lookup().unreflect(executeMethod);
					if(executeMethod.getReturnType().equals(Void.TYPE)){
						try {
							
							handle.invokeWithArguments(executeParemeter);
							return null;
						} catch (Throwable e) {
							throw new Exception(e);
						}
					} else{
						try {
							return handle.invokeWithArguments(executeParemeter);
						} catch (Throwable e) {
							throw new Exception(e);
						}
					}
				}
				
				
			}
		}
		return null;
	}
	
	public static Object executeMethod(String beanName, String methodName, Object... parameters) throws Exception{
		Object instance = ApplicationContextProvider.getApplicationContext().getBean(beanName);
		List<Method> methods = new ArrayList<Method>();
		Method executeMethod = null;
		Object executeParemeter[] = null;
		if(instance != null){
			Class<? extends Object> cls  = instance.getClass();
			java.lang.reflect.Method methodlist[] = cls.getDeclaredMethods();
			if(cls != null && !StringUtility.isNullOrBlank(methodName)){
				for (int i = 0; i < methodlist.length; i++) {
					java.lang.reflect.Method m = methodlist[i];
					if(StringUtility.isEqual(m.getName(), methodName)){
						/*파라미터 갯수 체크*/
						Class<?>[] parameterTypes = m.getParameterTypes();
						if(parameters.length == parameterTypes.length){
							Object arglist[] = new Object[parameterTypes.length+1];
							boolean matchedParameter = true;
							
							int iParameter = 1;
							arglist[0] = instance;
							for(Class<?> parameterType : parameterTypes){
								if(parameters[iParameter-1] != null){
									// try cast
									if(!isCompatible( parameters[iParameter-1], parameterType)){
										try{
											arglist[iParameter] = ConvertUtils.convert(parameters[iParameter-1], parameterType);
										} catch(Exception ex){
											matchedParameter = false;
										} 
									} else{
										arglist[iParameter] = parameters[iParameter-1];
									}
								} else{
									arglist[iParameter] = null;
								}
								i++;
							}
							
							if(matchedParameter){
								executeMethod = m;
								executeParemeter = arglist;
								break;
							}
							
						}
					}
				}
				
				if(executeMethod != null){
					MethodHandle handle = MethodHandles.lookup().unreflect(executeMethod);
					if(executeMethod.getReturnType().equals(Void.TYPE)){
						try {
							
							handle.invokeWithArguments(executeParemeter);
							return null;
						} catch (Throwable e) {
							throw new Exception(e);
						}
					} else{
						try {
							return handle.invokeWithArguments(executeParemeter);
						} catch (Throwable e) {
							throw new Exception(e);
						}
					}
				}
				
				
			}
		}
		return null;
	}
	
	
	public ServiceCall(Object beanInstance, String methodName){
		this.setInstance(beanInstance);
		this.setMethodName(methodName);
		this.methods = new ArrayList<Method>();
		if(instance != null){
			Class<? extends Object> cls  = instance.getClass();
			java.lang.reflect.Method methodlist[] = cls.getDeclaredMethods();
			if(cls != null && !StringUtility.isNullOrBlank(methodName)){
				for (int i = 0; i < methodlist.length; i++) {
					java.lang.reflect.Method m = methodlist[i];
					if(StringUtility.isEqual(m.getName(), methodName)){
						this.methods.add(m);
						/*this.method = m;
						logger.debug("method called : " + m.getName());
						break;*/
					}
				}
			}
		}
	}
	

	// determine whether a single object is compatible with
	// a single parameter type
	// careful: the object may be null
	private static boolean isCompatible(final Object object, final Class<?> paramType) throws Exception{
	    if(object == null){
	        // primitive parameters are the only parameters
	        // that can't handle a null object
	        return !paramType.isPrimitive();
	    }
	    // handles same type, super types and implemented interfaces
	    if(paramType.isInstance(object)){
	        return true;
	    }
	    // special case: the arg may be the Object wrapper for the
	    // primitive parameter type
	    if(paramType.isPrimitive()){
	        return isWrapperTypeOf(object.getClass(), paramType);
	    }
	    return false;

	}
	
	/*
	  awful hack, can be made much more elegant using Guava:
	  return Primitives.unwrap(candidate).equals(primitiveType);
	*/
	private static boolean isWrapperTypeOf(final Class<?> candidate, final Class<?> primitiveType) throws Exception{
	    try{
	        return !candidate.isPrimitive()
	            && candidate
	                .getDeclaredField("TYPE")
	                .get(null)
	                .equals(primitiveType);
	    } catch(final NoSuchFieldException e){
	        return false;
	    } catch(final Exception e){
	        throw e;
	    }
	}
	
	private void writeLog(String message){
		if(logger.isDebugEnabled()) logger.debug(message);
	}
	
	public void callMethod(Object... parameters) throws Exception{
		if(this.instance == null || this.methods.size() == 0) return;
		
		
		Method method = null;
		Class<?>[] parameterTypes = null;
		/**
		 * find method method count
		 */
		for(int j=0;j < this.methods.size() ;j++){
			Method m = this.methods.get(j);
			parameterTypes = m.getParameterTypes();
			if(parameters.length == parameterTypes.length){
				method = m;
				break;
			}
		}
		if(method == null) return;
		
		writeLog("" + method.getName() + " was called!!!!");
		
		MethodHandle handle = MethodHandles.lookup().unreflect(method);
		
		Object arglist[] = new Object[parameterTypes.length+1];
		
		int i = 1;
		arglist[0] = this.instance;
		for(Class<?> parameterType : parameterTypes){
			if(parameters[i-1] != null){
				// try cast
				if(!isCompatible( parameters[i-1], parameterType)){
					arglist[i] = ConvertUtils.convert(parameters[i-1], parameterType);
					//arglist[i] = parameterType.cast(parameters[i]);
				} else{
					arglist[i] = parameters[i-1];
				}
			} else{
				arglist[i] = null;
			}
			i++;
		}
		if(method.getReturnType().equals(Void.TYPE)){
			try {
				
				handle.invokeWithArguments(arglist);
			} catch (Throwable e) {
				throw new Exception(e);
			}
		} else{
			try {
				this.setData(handle.invokeWithArguments(arglist));
			} catch (Throwable e) {
				throw new Exception(e);
			}
		}
	}

	

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}