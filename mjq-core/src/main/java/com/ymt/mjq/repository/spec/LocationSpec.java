/**
 * 
 */
package com.ymt.mjq.repository.spec;

import com.ymt.mjq.domain.Location;
import com.ymt.mjq.dto.LocationInfo;
import com.ymt.pz365.data.jpa.repository.spec.PzSimpleSpecification;
import com.ymt.pz365.data.jpa.repository.spec.QueryWraper;

/**
 * @author zhailiang
 *
 */
public class LocationSpec extends PzSimpleSpecification<Location, LocationInfo> {

	public LocationSpec(LocationInfo condition) {
		super(condition);
	}

	@Override
	protected void addCondition(QueryWraper<Location> queryWraper) {
		addEqualsCondition(queryWraper, "type");
		addLikeCondition(queryWraper, "desc");
	}

}
