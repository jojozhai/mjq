/**
 * 
 */
package com.ymt.mjq.repository.spec;

import com.ymt.mjq.domain.Feedback;
import com.ymt.mjq.dto.FeedbackInfo;
import com.ymt.pz365.data.jpa.repository.spec.PzSimpleSpecification;
import com.ymt.pz365.data.jpa.repository.spec.QueryWraper;

/**
 * @author zhailiang
 *
 */
public class FeedbackSpec extends PzSimpleSpecification<Feedback, FeedbackInfo> {

	public FeedbackSpec(FeedbackInfo condition) {
		super(condition);
	}

	@Override
	protected void addCondition(QueryWraper<Feedback> queryWraper) {
		addLikeCondition(queryWraper, "content");
	}

}
