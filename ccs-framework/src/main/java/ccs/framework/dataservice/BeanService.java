package ccs.framework.dataservice;

public class BeanService {

	public static <T> T getBeanService(String beanName,Class<T> type){
		return type.cast(ApplicationContextProvider.getApplicationContext().getBean(beanName));
	}
}
