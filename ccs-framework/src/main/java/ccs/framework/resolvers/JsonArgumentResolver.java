package ccs.framework.resolvers;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import ccs.framework.model.JsonParameter;


public class JsonArgumentResolver implements HandlerMethodArgumentResolver {
	private static final Logger logger = LoggerFactory.getLogger(JsonArgumentResolver.class);
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return JsonParameter.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		try{
			HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
			return JsonParameter.create(request);
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}

}

