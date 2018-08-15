package chat.me.spring.config;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.StringUtils;

import chat.me.dto.MessageTrnDto;

public class CustomCacheKeyGenerator implements KeyGenerator{

	@Override
	public Object generate(Object target, Method method, Object... params) {
		if(target.getClass().getName().contains("MessengerServiceImpl")
				&& method.getName().equals("fetchAllMessageBySourceAndDest")) {
			return "MessengerServiceImpl_fetchAllMessageBySourceAndDest_" 
					+ ((MessageTrnDto)params[0]).getSourceId() + "_"
					+ ((MessageTrnDto)params[0]).getDestinationId();
		}
		return target.getClass().getSimpleName() + "_"
		          + method.getName() + "_"
		          + StringUtils.arrayToDelimitedString(params, "_");
	}

}
