/**
 * 
 */
package com.ymt.mjq.web.weixin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ymt.mjq.dto.AccidentInfo;
import com.ymt.mjq.service.AccidentService;

/**
 * @author zhailiang
 * @since 2016年5月5日
 */
@RestController
@Profile("!admin")
public class AccidentAdminController {
	
	@Autowired
	private AccidentService accidentService;

	@RequestMapping(value = "/accident", method = RequestMethod.GET)
	public Page<AccidentInfo> query(AccidentInfo accidentInfo, Pageable pageable) {
		return accidentService.query(accidentInfo, pageable);
	}
	
}
