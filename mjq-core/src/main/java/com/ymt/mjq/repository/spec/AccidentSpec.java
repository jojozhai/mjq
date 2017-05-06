/**
 * 
 */
package com.ymt.mjq.repository.spec;

import com.ymt.mjq.domain.Accident;
import com.ymt.mjq.dto.AccidentInfo;
import com.ymt.pz365.data.jpa.repository.spec.PzSimpleSpecification;
import com.ymt.pz365.data.jpa.repository.spec.QueryWraper;

/**
 * @author zhailiang
 *
 */
public class AccidentSpec extends PzSimpleSpecification<Accident, AccidentInfo> {

	public AccidentSpec(AccidentInfo condition) {
		super(condition);
	}

	@Override
	protected void addCondition(QueryWraper<Accident> queryWraper) {
		addLikeCondition(queryWraper, "title");
	}

}
