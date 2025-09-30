package saj.startup.pj.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
	
	public final String GET_ALL_STRANDEGREES = "SELECT e "
			+ "FROM StrandegreeEntity e "
			+ "WHERE e.isDeleted = false "
			+ "AND ( "
		    + "   (:search IS NOT NULL AND :search <> '' AND ( " 
		    + "       LOWER(e.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " 
		    + "       LOWER(e.code) LIKE LOWER(CONCAT('%', :search, '%')) OR " 
		    + "       LOWER(e.category) LIKE LOWER(CONCAT('%', :search, '%')) OR " 
		    + "       LOWER(CAST(e.isActive AS CHARACTER)) LIKE LOWER(CONCAT('%', :search, '%'))" 
		    + "   )) " 
		    + "   OR (:search IS NULL OR :search = '') " 
		    + ")";
	
	@Query(GET_ALL_STRANDEGREES)
	public Page<StrandegreeEntity> getAllStrandegrees(Pageable pageable,
			@Param("search") String search) throws DataAccessException;
	
	public final String GET_ALL_STRANDEGREES_NO_PAGEABLE = "SELECT e "
			+ "FROM StrandegreeEntity e "
			+ "WHERE isDeleted = false ";
	
	@Query(GET_ALL_STRANDEGREES_NO_PAGEABLE)
	public List<StrandegreeEntity> getAllStrandegreesNoPageable() throws DataAccessException;
}
