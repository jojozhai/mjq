/**
 * 
 */
package com.ymt.mjq.service.impl;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ymt.mirage.user.repository.UserRepository;
import com.ymt.mjq.domain.Inform;
import com.ymt.mjq.domain.InformStatus;
import com.ymt.mjq.dto.InformInfo;
import com.ymt.mjq.repository.InformRepository;
import com.ymt.mjq.repository.spec.InformSpec;
import com.ymt.mjq.service.InformService;
import com.ymt.pz365.data.jpa.support.QueryResultConverter;
import com.ymt.pz365.framework.param.service.ParamService;
import com.ymt.pz365.framework.weixin.service.WeixinService;
import com.ymt.pz365.framework.weixin.support.message.TemplateMessage;

/**
 * @author zhailiang
 *
 */
@Service("informService")
@Transactional
public class InformServiceImpl implements InformService {

	@Autowired
    private InformRepository informRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WeixinService weixinService;
	
	@Autowired
	private ParamService paramService;
	
	@Value("${mjq.message.template.response:_6bvHV_RPuTy_O3Yfht2CLrq9Pl4KU0sq0hbzC9Sj60}")
	private String responseTemplateId;
	
    @Override
    public Page<InformInfo> query(InformInfo informInfo, Pageable pageable) {
        Page<Inform> pageData = informRepository.findAll(new InformSpec(informInfo), pageable);
        return QueryResultConverter.convert(pageData, InformInfo.class, pageable);
    }

    @Override
    public InformInfo create(InformInfo informInfo) {
        Inform inform = new Inform();
        BeanUtils.copyProperties(informInfo, inform);
        inform.setStatus(InformStatus.WAITING);
        inform.setUser(userRepository.getOne(informInfo.getUserId()));
        informInfo.setId(informRepository.save(inform).getId());
        return informInfo;
    }

    @Override
    public InformInfo getInfo(Long id) {
        Inform inform = informRepository.findOne(id);
        InformInfo info = new InformInfo();
        BeanUtils.copyProperties(inform, info);
        return info;
    }

    @Override
    public InformInfo update(InformInfo informInfo) {
        Inform inform = informRepository.findOne(informInfo.getId());
//        BeanUtils.copyProperties(informInfo, inform);
        inform.setImages2(informInfo.getImages2());
        inform.setStatus(InformStatus.FINISH);
        informRepository.save(inform);
        return informInfo;
    }

    @Override
    public void delete(Long id) {
        informRepository.delete(id);       
    }

	@Override
	public void bonus(Long id) throws Exception {
		Inform inform = informRepository.findOne(id);
		int amount = new Integer(paramService.getParam("mjq.inform.bonus", "3").getValue());
		weixinService.sendRedpack(inform.getId(), "127.0.0.1", inform.getUser().getWeixinOpenId(), amount);
		inform.setBonus(amount);
		inform.setBonusTime(new Date());
	}

	@Override
	public void accept(Long id) throws Exception {
		Inform inform = informRepository.findOne(id);
		inform.setStatus(InformStatus.WORKING);
		//推送模板消息
		String defaultResponseUser = paramService.getParam("default_response", "oua4YwKiGeNNC4-VjcDjIzbs4TWk").getValue();
		String value = paramService.getParam("response_"+inform.getType(), defaultResponseUser).getValue();
		sendToUser(inform);
		sendToResponse(inform, value);
	}
	
	private void sendToResponse(Inform inform, String toUser) throws Exception {
		TemplateMessage message = new TemplateMessage(toUser, responseTemplateId, "http://www.baidu.com");
		message.addValue("first", paramService.getParam("template_response_first", "有新的爆料，请点击查看详情").getValue());
		message.addValue("keyword1", inform.getId().toString());
		message.addValue("keyword2", new DateTime().toString("yyyy-MM-dd"));
		message.addValue("keyword3", paramService.getParam("template_response_keyword3", "马驹桥镇政府").getValue());
		message.addValue("remark", paramService.getParam("template_response_remark", "请尽快处理").getValue());
		weixinService.pushTemplateMessage(message);
	}

	private void sendToUser(Inform inform) throws Exception {
		TemplateMessage message = new TemplateMessage(inform.getUser().getWeixinOpenId(), responseTemplateId);
		message.addValue("first", paramService.getParam("template_accept_first", "您好！您举报的问题已被受理，我们会针对您举报问题展开调查，办理进展会及时反馈").getValue());
		message.addValue("keyword1", inform.getId().toString());
		message.addValue("keyword2", new DateTime().toString("yyyy-MM-dd"));
		message.addValue("keyword3", paramService.getParam("template_accept_keyword3", "马驹桥镇政府").getValue());
		message.addValue("remark", paramService.getParam("template_accept_remark", "感谢您对政府工作的支持").getValue());
		weixinService.pushTemplateMessage(message);
	}
}
