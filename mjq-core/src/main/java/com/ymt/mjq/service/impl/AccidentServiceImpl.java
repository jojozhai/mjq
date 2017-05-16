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

import com.ymt.mjq.domain.Accident;
import com.ymt.mjq.dto.AccidentInfo;
import com.ymt.mjq.repository.AccidentRepository;
import com.ymt.mjq.repository.spec.AccidentSpec;
import com.ymt.mjq.service.AccidentService;
import com.ymt.pz365.data.jpa.support.QueryResultConverter;

/**
 * @author zhailiang
 *
 */
@Service("accidentService")
@Transactional
public class AccidentServiceImpl implements AccidentService {

	@Autowired
    private AccidentRepository accidentRepository;
    
    @Override
    public Page<AccidentInfo> query(AccidentInfo accidentInfo, Pageable pageable) {
        Page<Accident> pageData = accidentRepository.findAll(new AccidentSpec(accidentInfo), pageable);
        return QueryResultConverter.convert(pageData, AccidentInfo.class, pageable);
    }

    @Override
    public AccidentInfo create(AccidentInfo accidentInfo) {
        Accident accident = new Accident();
        BeanUtils.copyProperties(accidentInfo, accident);
        accident.setCreatedTime(new Date());
        accidentInfo.setId(accidentRepository.save(accident).getId());
        return accidentInfo;
    }

    @Override
    public AccidentInfo getInfo(Long id) {
        Accident accident = accidentRepository.findOne(id);
        AccidentInfo info = new AccidentInfo();
        BeanUtils.copyProperties(accident, info);
        return info;
    }

    @Override
    public AccidentInfo update(AccidentInfo accidentInfo) {
        Accident accident = accidentRepository.findOne(accidentInfo.getId());
        BeanUtils.copyProperties(accidentInfo, accident);
        accidentRepository.save(accident);
        return accidentInfo;
    }

    @Override
    public void delete(Long id) {
        accidentRepository.delete(id);       
    }

}
