/**
 * 
 */
package com.ymt.mjq.web.weixin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
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
@Profile("weixin")
public class FeedbackWeixinController {
	
	@Autowired
	private FeedbackService feedbackService;

	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
	public FeedbackInfo create(@RequestBody FeedbackInfo feedbackInfo) {
		return feedbackService.create(feedbackInfo);
	}
	
}
