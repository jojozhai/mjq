/**
 * 
 */
package com.ymt.mjq.web.weixin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ymt.mjq.dto.InformInfo;
import com.ymt.mjq.service.InformService;

/**
 * @author zhailiang
 * @since 2016年5月5日
 */
@RestController
@Profile("weixin")
public class InformWeixinController {
	
	@Autowired
	private InformService informService;

	@RequestMapping(value = "/inform", method = RequestMethod.POST)
	public InformInfo create(@RequestBody InformInfo informInfo) {
		return informService.create(informInfo);
	}

	@RequestMapping(value = "/inform", method = RequestMethod.GET)
	public Page<InformInfo> query(InformInfo informInfo, Pageable pageable) {
		return informService.query(informInfo, pageable);
	}
	
	@RequestMapping(value = "/inform/{id}", method = RequestMethod.GET)
	public InformInfo getInfo(@PathVariable Long id) {
		return informService.getInfo(id);
	}

	@RequestMapping(value = "/inform/{id}", method = RequestMethod.PUT)
	public InformInfo update(@RequestBody InformInfo informInfo) {
		return informService.update(informInfo);
	}
	
}
