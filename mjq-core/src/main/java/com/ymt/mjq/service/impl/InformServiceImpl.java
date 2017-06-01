/**
 * 
 */
package com.ymt.mjq.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ymt.mirage.user.repository.UserRepository;
import com.ymt.mirage.user.service.UserService;
import com.ymt.mjq.domain.Inform;
import com.ymt.mjq.domain.InformStatus;
import com.ymt.mjq.dto.InformInfo;
import com.ymt.mjq.repository.InformRepository;
import com.ymt.mjq.repository.spec.InformSpec;
import com.ymt.mjq.service.InformService;
import com.ymt.pz365.data.jpa.support.AbstractDomain2InfoConverter;
import com.ymt.pz365.data.jpa.support.QueryResultConverter;
import com.ymt.pz365.framework.core.exception.PzException;
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
	
	@Autowired
	private UserService userService;
	
	/**
	 * 受理通知
	 */
	@Value("${mjq.message.template.response:4IS4Y_FcUaSMsfSW3lDLOLysM-9JcAnfbv1zc5zMG9E}")
	private String responseTemplateId;
	
	@Value("${mjq.message.template.finish:fn2eA5-lO2kIBDVFXiDIDPTu98xYe_TDX2suSmdeOlc}")
	private String finishTemplateId;
	
	
    @Override
    public Page<InformInfo> query(InformInfo informInfo, Pageable pageable) {
        Page<Inform> pageData = informRepository.findAll(new InformSpec(informInfo), pageable);
        return QueryResultConverter.convert(pageData, pageable, new AbstractDomain2InfoConverter<Inform, InformInfo>() {
			@Override
			protected void doConvert(Inform domain, InformInfo info) throws Exception {
				info.setUserId(domain.getUser().getId());
				info.setUsername(domain.getUser().getNickname());
				info.setUserhead(domain.getUser().getHeadimgurl());
			}
		});
    }

    @Override
    public InformInfo create(InformInfo informInfo) {
        Inform inform = new Inform();
        BeanUtils.copyProperties(informInfo, inform);
        inform.setStatus(InformStatus.WAITING);
        inform.setUser(userRepository.getOne(informInfo.getUserId()));
        inform.setCreatedTime(new Date());
        informInfo.setId(informRepository.save(inform).getId());
        return informInfo;
    }

    @Override
    public InformInfo getInfo(Long id) {
        Inform inform = informRepository.findOne(id);
        InformInfo info = new InformInfo();
        BeanUtils.copyProperties(inform, info);
        info.setUserId(inform.getUser().getId());
		info.setUsername(inform.getUser().getNickname());
		info.setUserhead(inform.getUser().getHeadimgurl());
        return info;
    }

    @Override
    public InformInfo update(InformInfo informInfo) {
        Inform inform = informRepository.findOne(informInfo.getId());
//        BeanUtils.copyProperties(informInfo, inform);
        if(inform.getStatus().equals(InformStatus.WORKING)) {
        	inform.setImages2(informInfo.getImages2());
            inform.setStatus(InformStatus.WORKED);
            informRepository.save(inform);
            return informInfo;
        }else{
        	throw new PzException("办理失败，状态异常");
        }
        
    }

	@Override
	public InformInfo modify(InformInfo informInfo) {
		Inform inform = informRepository.findOne(informInfo.getId());
		inform.setImages(informInfo.getImages());
		inform.setImages2(informInfo.getImages2());
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
		if(inform.getStatus().equals(InformStatus.WORKED)) {
			int amount = new Integer(paramService.getParam("mjq.inform.bonus", "5").getValue());
//			weixinService.sendRedpack(inform.getId(), "127.0.0.1", inform.getUser().getWeixinOpenId(), amount);
			if(StringUtils.equals(inform.getType(), "露天烧烤") || StringUtils.equals(inform.getType(), "小广告")){
				amount = 10;
			}
			
			userService.changePoint(inform.getUser().getId(), amount, "创城随手拍");
			
			inform.setBonus(amount);
			inform.setBonusTime(new Date());
			inform.setStatus(InformStatus.FINISH);
			
			String url = "http://wx.norej.cn/mjq-weixin/html/details.html?id="+id;
			
			TemplateMessage message = new TemplateMessage(inform.getUser().getWeixinOpenId(), finishTemplateId, url);
			message.addValue("first", paramService.getParam("template_finish_first", "您好，您反馈的问题已经处理完成。").getValue());
			message.addValue("keyword1", inform.getId().toString());
			message.addValue("keyword2", paramService.getParam("template_finish_keyword3", "已办结").getValue());
			message.addValue("remark", paramService.getParam("template_finish_remark", "请点击这个文本框，查看处理结果。感谢您的支持和参与，美丽城市，全民共建！").getValue());
			weixinService.pushTemplateMessage(message);
			
		}else{
			throw new PzException("办结失败，状态异常");
		}
	}

	@Override
	public void accept(Long id) throws Exception {
		Inform inform = informRepository.findOne(id);
		if(inform.getStatus().equals(InformStatus.WAITING)) {
			inform.setStatus(InformStatus.WORKING);
			//推送模板消息
			String defaultResponseUser = paramService.getParam("default_response", "oua4YwKiGeNNC4-VjcDjIzbs4TWk,oua4YwHTrfoEHi8MoKtH9Q_U9M4M").getValue();
			String value = paramService.getParam("response_"+inform.getType(), defaultResponseUser).getValue();
			sendToUser(inform);
			if(StringUtils.contains(value, ",")){
				String[] users = StringUtils.splitByWholeSeparatorPreserveAllTokens(value, ",");
				for (String user : users) {
					sendToResponse(inform, user);
				}
			}else{
				sendToResponse(inform, value);
			}
			
		}else{
			throw new PzException("受理失败,状态异常");
		}
		
	}
	
	private void sendToResponse(Inform inform, String toUser) throws Exception {
		String url = "http://wx.norej.cn/mjq-weixin/html/details.html?id="+inform.getId()+"&type=oper";
		TemplateMessage message = new TemplateMessage(toUser, responseTemplateId, url);
		message.addValue("first", paramService.getParam("template_response_first", "有新的爆料，请点击查看详情").getValue());
		message.addValue("keyword1", new DateTime(inform.getCreatedTime()).toString("yyyy-MM-dd"));
		message.addValue("keyword2", inform.getContent());
		message.addValue("keyword3", new DateTime().toString("yyyy-MM-dd"));
		message.addValue("remark", paramService.getParam("template_response_remark", "请尽快处理").getValue());
		weixinService.pushTemplateMessage(message);
	}

	private void sendToUser(Inform inform) throws Exception {
		TemplateMessage message = new TemplateMessage(inform.getUser().getWeixinOpenId(), responseTemplateId);
		message.addValue("first", paramService.getParam("template_accept_first", "您好，您反映的问题我们已经受理，我们将安排工作人员尽快依据职责权限进行处理。").getValue());
		message.addValue("keyword1", new DateTime(inform.getCreatedTime()).toString("yyyy-MM-dd"));
		message.addValue("keyword2", inform.getContent());
		message.addValue("keyword3", new DateTime().toString("yyyy-MM-dd"));
		message.addValue("remark", paramService.getParam("template_accept_remark", "感谢您的支持和参与，我们一起努力，共建美丽马驹桥！").getValue());
		weixinService.pushTemplateMessage(message);
	}

	@Override
	public void deny(Long id) throws Exception {
		Inform inform = informRepository.findOne(id);
		if(inform.getStatus().equals(InformStatus.WAITING)) {
			inform.setStatus(InformStatus.DENY);
			//推送模板消息
			TemplateMessage message = new TemplateMessage(inform.getUser().getWeixinOpenId(), "GPH-Fo1bcl55uH37h-YBCE6ThpZF49r0C_X0PemUzEQ");
			message.addValue("first", paramService.getParam("template_deny_first", "您好，因问题描述不清、职责范围所限、问题不在马驹桥行政区域内、处理时问题早已解决等原因，您提交的信息暂未被受理。").getValue());
			message.addValue("keyword1", inform.getId().toString());
			message.addValue("keyword2", inform.getContent());
			message.addValue("remark", paramService.getParam("template_deny_remark", "感谢您的理解、支持和参与，如有疑问，请于工作日拨打010-61580181咨询（上午9点至11点，下午14点至17点）。").getValue());
			weixinService.pushTemplateMessage(message);
			
		}else{
			throw new PzException("拒绝受理失败,状态异常");
		}
	}
}
