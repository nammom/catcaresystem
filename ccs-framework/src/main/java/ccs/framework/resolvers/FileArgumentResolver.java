package ccs.framework.resolvers;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import ccs.framework.model.FileParameter;

import javax.servlet.http.HttpServletRequest;



public class FileArgumentResolver implements HandlerMethodArgumentResolver{
	
	@Override
    public boolean supportsParameter(MethodParameter parameter) {
        return FileParameter.class.isAssignableFrom(parameter.getParameterType());
    }
 
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    	HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
    	return FileParameter.create(request);
    }
}