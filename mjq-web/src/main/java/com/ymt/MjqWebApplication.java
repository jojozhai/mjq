/**
 * 
 */
package com.ymt;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ymt.pz365.framework.core.utils.SpringBoot;

/**
 * @author zhailiang
 *
 */
@SpringBootApplication
public class MjqWebApplication {

	public static void main(String[] args) {
		SpringBoot.start(MjqWebApplication.class, args);
	}

}
