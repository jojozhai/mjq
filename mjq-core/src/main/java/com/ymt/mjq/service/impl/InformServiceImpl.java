/**
 * 
 */
package com.ymt.mjq.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ymt.mjq.domain.Inform;
import com.ymt.mjq.domain.InformStatus;
import com.ymt.mjq.dto.InformInfo;
import com.ymt.mjq.repository.InformRepository;
import com.ymt.mjq.repository.spec.InformSpec;
import com.ymt.mjq.service.InformService;
import com.ymt.pz365.data.jpa.support.QueryResultConverter;
import com.ymt.pz365.framework.param.service.ParamService;
import com.ymt.pz365.framework.weixin.service.WeixinService;

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
	private WeixinService weixinService;
	
	@Autowired
	private ParamService paramService;
	
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
	public void accept(Long id) {
		Inform inform = informRepository.findOne(id);
		inform.setStatus(InformStatus.WORKING);
		//推送模板消息
//		weixinService.pushTemplateMessage(null);
	}
}
