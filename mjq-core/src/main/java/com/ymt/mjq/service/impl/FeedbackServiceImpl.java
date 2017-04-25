/**
 * 
 */
package com.ymt.mjq.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ymt.mjq.domain.Feedback;
import com.ymt.mjq.dto.FeedbackInfo;
import com.ymt.mjq.repository.FeedbackRepository;
import com.ymt.mjq.repository.spec.FeedbackSpec;
import com.ymt.mjq.service.FeedbackService;
import com.ymt.pz365.data.jpa.support.QueryResultConverter;

/**
 * @author zhailiang
 *
 */
@Service("feedbackService")
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
    private FeedbackRepository feedbackRepository;
    
    @Override
    public Page<FeedbackInfo> query(FeedbackInfo feedbackInfo, Pageable pageable) {
        Page<Feedback> pageData = feedbackRepository.findAll(new FeedbackSpec(feedbackInfo), pageable);
        return QueryResultConverter.convert(pageData, FeedbackInfo.class, pageable);
    }

    @Override
    public FeedbackInfo create(FeedbackInfo feedbackInfo) {
        Feedback feedback = new Feedback();
        BeanUtils.copyProperties(feedbackInfo, feedback);
        feedbackInfo.setId(feedbackRepository.save(feedback).getId());
        return feedbackInfo;
    }

    @Override
    public FeedbackInfo getInfo(Long id) {
        Feedback feedback = feedbackRepository.findOne(id);
        FeedbackInfo info = new FeedbackInfo();
        BeanUtils.copyProperties(feedback, info);
        return info;
    }

    @Override
    public FeedbackInfo update(FeedbackInfo feedbackInfo) {
        Feedback feedback = feedbackRepository.findOne(feedbackInfo.getId());
        BeanUtils.copyProperties(feedbackInfo, feedback);
        feedbackRepository.save(feedback);
        return feedbackInfo;
    }

    @Override
    public void delete(Long id) {
        feedbackRepository.delete(id);       
    }

}
