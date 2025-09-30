package saj.startup.pj.model.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import saj.startup.pj.model.dao.entity.StrandegreeEntity;
import saj.startup.pj.model.dao.entity.StrandegreeOverviewData;

public interface StrandegreeDao extends JpaRepository<StrandegreeEntity, Integer> {

	public final String GET_STRANDEGREE_OVERVIEW =
	        "SELECT new saj.startup.pj.model.dao.entity.StrandegreeOverviewData( " +
	        "CAST(SUM(CASE WHEN e.category = 'STRAND' THEN 1 ELSE 0 END) AS INTEGER), " + // totalStrand
	        "CAST(SUM(CASE WHEN e.category = 'STRAND' AND e.isActive = true THEN 1 ELSE 0 END) AS INTEGER), " + // activeStrand
	        "CAST(SUM(CASE WHEN e.category = 'STRAND' AND e.isActive = false THEN 1 ELSE 0 END) AS INTEGER), " + // inactiveStrand
	        "CAST(SUM(CASE WHEN e.category = 'DEGREE' THEN 1 ELSE 0 END) AS INTEGER), " + // totalDegree
	        "CAST(SUM(CASE WHEN e.category = 'DEGREE' AND e.isActive = true THEN 1 ELSE 0 END) AS INTEGER), " + // activeDegree
	        "CAST(SUM(CASE WHEN e.category = 'DEGREE' AND e.isActive = false THEN 1 ELSE 0 END) AS INTEGER) " + // inactiveDegree
	        ") " +
	        "FROM StrandegreeEntity e " +
	        "WHERE e.isDeleted = false";
	
	@Query(GET_STRANDEGREE_OVERVIEW)
	public StrandegreeOverviewData getUserOverview() throws DataAccessException;
}
