/**
 * 
 */
package com.ymt.mjq.repository;

import java.util.List;

import com.ymt.mjq.domain.Location;
import com.ymt.pz365.data.jpa.repository.PzRepository;

/**
 * @author zhailiang
 *
 */
public interface LocationRepository extends PzRepository<Location> {

	List<Location> findByType(String type);

}
