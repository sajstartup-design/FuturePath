package saj.startup.pj.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import saj.startup.pj.model.dao.entity.StrandegreeEntity;
import saj.startup.pj.model.dao.entity.StrandegreeOverviewData;
import saj.startup.pj.model.dao.entity.StrandegreeQuestionData;

public interface StrandegreeDao extends JpaRepository<StrandegreeEntity, Integer> {

	public final String GET_STRANDEGREE_OVERVIEW =
		    "SELECT new saj.startup.pj.model.dao.entity.StrandegreeOverviewData( " +
		    "CAST(COALESCE(SUM(CASE WHEN e.category = 'STRAND' THEN 1 ELSE 0 END), 0) AS INTEGER), " + // totalStrand
		    "CAST(COALESCE(SUM(CASE WHEN e.category = 'STRAND' AND e.isActive = true THEN 1 ELSE 0 END), 0) AS INTEGER), " + // activeStrand
		    "CAST(COALESCE(SUM(CASE WHEN e.category = 'STRAND' AND e.isActive = false THEN 1 ELSE 0 END), 0) AS INTEGER), " + // inactiveStrand
		    "CAST(COALESCE(SUM(CASE WHEN e.category = 'DEGREE' THEN 1 ELSE 0 END), 0) AS INTEGER), " + // totalDegree
		    "CAST(COALESCE(SUM(CASE WHEN e.category = 'DEGREE' AND e.isActive = true THEN 1 ELSE 0 END), 0) AS INTEGER), " + // activeDegree
		    "CAST(COALESCE(SUM(CASE WHEN e.category = 'DEGREE' AND e.isActive = false THEN 1 ELSE 0 END), 0) AS INTEGER) " + // inactiveDegree
		    ") " +
		    "FROM StrandegreeEntity e " +
		    "WHERE e.isDeleted = false";

	
	@Query(GET_STRANDEGREE_OVERVIEW)
	public StrandegreeOverviewData getStrandegreeOverview() throws DataAccessException;
	
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
		    + ") "
		    + "ORDER BY e.idPk ASC " ;
	
	@Query(GET_ALL_STRANDEGREES)
	public Page<StrandegreeEntity> getAllStrandegrees(Pageable pageable,
			@Param("search") String search) throws DataAccessException;
	
	public final String GET_ALL_STRANDEGREES_NO_PAGEABLE = "SELECT e "
			+ "FROM StrandegreeEntity e "
			+ "WHERE isDeleted = false "
			+ "ORDER BY e.idPk ASC  ";
	
	@Query(GET_ALL_STRANDEGREES_NO_PAGEABLE)
	public List<StrandegreeEntity> getAllStrandegreesNoPageable() throws DataAccessException;
	
	public final String GET_ALL_STRANDEGREE_WITH_TOTAL_QUESTIONS = ""
		    + "SELECT new saj.startup.pj.model.dao.entity.StrandegreeQuestionData( "
		    + "e.category, e.code, e.name, CAST(COUNT(q.idPk) AS INTEGER) ) "
		    + "FROM StrandegreeEntity e "
		    + "LEFT JOIN QuestionEntity q ON q.strandegreeIdPk = e.idPk AND q.isDeleted = false "
		    + "GROUP BY e.idPk, e.category, e.code, e.name "
		    + "ORDER BY e.idPk";

	
	
	@Query(GET_ALL_STRANDEGREE_WITH_TOTAL_QUESTIONS)
	public List<StrandegreeQuestionData> getAllStrandegreeQuestion() throws DataAccessException;
	
	public final String GET_STRANDEGREE_BY_ID_PK = """
				SELECT e FROM StrandegreeEntity e WHERE e.idPk = :idPk
			""";
	
	@Query(GET_STRANDEGREE_BY_ID_PK)
	public StrandegreeEntity getStrandegreeByIdPk(@Param("idPk") int idPk) throws DataAccessException;
	
	public final String DELETE_STRANDEGREE = """
				UPDATE strandegrees 
				SET is_deleted = true
				WHERE id_pk = :idPk
			""";
	
	@Transactional
	@Modifying
	@Query(value=DELETE_STRANDEGREE, nativeQuery=true)
	public void deleteStrandegree(@Param("idPk") int idPk) throws DataAccessException;
}
