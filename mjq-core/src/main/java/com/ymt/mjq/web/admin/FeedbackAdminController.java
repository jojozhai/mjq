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

import com.ymt.mjq.dto.FeedbackInfo;
import com.ymt.mjq.service.FeedbackService;

/**
 * @author zhailiang
 * @since 2016年5月5日
 */
@RestController
@Profile("admin")
public class FeedbackAdminController {
	
	@Autowired
	private FeedbackService feedbackService;

	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
	public FeedbackInfo create(@RequestBody FeedbackInfo feedbackInfo) {
		return feedbackService.create(feedbackInfo);
	}

	@RequestMapping(value = "/feedback", method = RequestMethod.GET)
	public Page<FeedbackInfo> query(FeedbackInfo feedbackInfo, Pageable pageable) {
		return feedbackService.query(feedbackInfo, pageable);
	}
	
	@RequestMapping(value = "/feedback/{id}", method = RequestMethod.GET)
	public FeedbackInfo getInfo(@PathVariable Long id) {
		return feedbackService.getInfo(id);
	}

	@RequestMapping(value = "/feedback/{id}", method = RequestMethod.PUT)
	public FeedbackInfo update(@RequestBody FeedbackInfo feedbackInfo) {
		return feedbackService.update(feedbackInfo);
	}

	@RequestMapping(value = "/feedback/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		feedbackService.delete(id);
	}
	
}
