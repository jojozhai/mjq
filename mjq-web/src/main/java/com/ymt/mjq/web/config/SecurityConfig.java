/**
 * 
 */
package com.ymt.mjq.web.config;

import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ymt.mirage.user.service.UserService;
import com.ymt.pz365.framework.core.web.security.SecurityRequestConfig;

/**
 * @author zhailiang
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private Set<SecurityRequestConfig> securityRequestConfigs;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	    auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}
	
	protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable() 
//	    	.httpBasic()
//	    		.and()
	        .authorizeRequests()
	        .antMatchers(HttpMethod.GET, getRequests("get")).authenticated()
	        .antMatchers(HttpMethod.POST, getRequests("post")).authenticated()
	        .antMatchers(HttpMethod.PUT, getRequests("put")).authenticated()
	        .antMatchers(HttpMethod.DELETE, getRequests("delete")).authenticated()
	        .anyRequest().permitAll()
	        	.and()
	        .headers().frameOptions().disable();
	    
	}

    private String[] getRequests(String flag) {
        String[] result = new String[]{};
        for (SecurityRequestConfig config : securityRequestConfigs) {
            if(StringUtils.equals(flag, "get")) {
                result = (String[]) ArrayUtils.addAll(result, config.getGetRequests());
            }else if(StringUtils.equals(flag, "post")) {
                result = (String[]) ArrayUtils.addAll(result, config.getPostRequests());
            }else if(StringUtils.equals(flag, "put")) {
                result = (String[]) ArrayUtils.addAll(result, config.getPutRequests());
            }else if(StringUtils.equals(flag, "delete")) {
                result = (String[]) ArrayUtils.addAll(result, config.getDeleteRequests());
            }
        }
        return result;
    }

}
