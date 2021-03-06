/**
 * 
 */
package com.ymt.mjq.web.admin;

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
@Profile("admin")
public class InformAdminController {
	
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
	
	@RequestMapping(value = "/inform/{id}/bonus", method = RequestMethod.POST)
	public void bonus(@PathVariable Long id) throws Exception {
		informService.bonus(id);
	}
	
	@RequestMapping(value = "/inform/{id}/accept", method = RequestMethod.PUT)
	public void accept(@PathVariable Long id) throws Exception {
		informService.accept(id);
	}
	
	@RequestMapping(value = "/inform/{id}/deny", method = RequestMethod.PUT)
	public void deny(@PathVariable Long id) throws Exception {
		informService.deny(id);
	}

	@RequestMapping(value = "/inform/{id}", method = RequestMethod.PUT)
	public InformInfo update(@RequestBody InformInfo informInfo) {
		return informService.update(informInfo);
	}
	
	@RequestMapping(value = "/inform/{id}/modify", method = RequestMethod.PUT)
	public InformInfo modify(@RequestBody InformInfo informInfo) {
		return informService.modify(informInfo);
	}

	@RequestMapping(value = "/inform/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		informService.delete(id);
	}
	
}
