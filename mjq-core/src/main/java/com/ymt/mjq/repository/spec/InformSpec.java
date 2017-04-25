/**
 * 
 */
package com.ymt.mjq.repository.spec;

import com.ymt.mjq.domain.Inform;
import com.ymt.mjq.dto.InformInfo;
import com.ymt.pz365.data.jpa.repository.spec.PzSimpleSpecification;
import com.ymt.pz365.data.jpa.repository.spec.QueryWraper;

/**
 * @author zhailiang
 *
 */
public class InformSpec extends PzSimpleSpecification<Inform, InformInfo> {

	public InformSpec(InformInfo condition) {
		super(condition);
	}

	@Override
	protected void addCondition(QueryWraper<Inform> queryWraper) {
		
		addEqualsCondition(queryWraper, "type");
		addEqualsCondition(queryWraper, "status");
		addEqualsCondition(queryWraper, "userId", "user.id");
		
		addLikeCondition(queryWraper, "content");
		addLikeCondition(queryWraper, "location");
		addLikeCondition(queryWraper, "phone");
		
		if(getCondition().isBonus()){
			addGreaterThanConditionToColumn(queryWraper, "bonus", 0);
		}
	}
	

}
