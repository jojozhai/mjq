/**
 * 
 */
package com.ymt.mjq.spi.router;

import java.net.URLDecoder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ymt.pz365.framework.weixin.spi.router.WeixinRouter;

/**
 * @author zhailiang
 * @since 2016年5月3日
 */
@Component("weixinRouter")
public class MjqWeixinRouter implements WeixinRouter {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	/* (non-Javadoc)
	 * @see com.ymt.pz365.framework.weixin.spi.router.WeixinRouter#route(java.lang.String, org.springframework.security.core.userdetails.UserDetails)
	 */
	@Override
	public String route(String state, UserDetails user) throws Exception {
		if(StringUtils.equals(state, "test")){
			return "#/index/";
		}
		state = URLDecoder.decode(state, "UTF-8");
		
		if(StringUtils.startsWith(state, "http")){
		    return state;
		}
		
		if(StringUtils.equals(state, "index")) {
			state = "index/";
		}
			
		if(!StringUtils.startsWith(state, "/")){
			state = "/"+state;
		}
		
		state = StringUtils.replace(state, "-", "/");
		
		logger.info("route to "+state);
		
		return state;
 	}

}
