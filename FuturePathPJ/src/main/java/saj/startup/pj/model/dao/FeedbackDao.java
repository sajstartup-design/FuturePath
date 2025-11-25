package saj.startup.pj.model.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import saj.startup.pj.model.dao.entity.FeedbackData;
import saj.startup.pj.model.dao.entity.FeedbackEntity;
import saj.startup.pj.model.dao.entity.FeedbackOverviewData;

public interface FeedbackDao extends JpaRepository<FeedbackEntity, Integer> {
	
	public final String GET_FEEDBACK_OVERVIEW =
		    "SELECT new saj.startup.pj.model.dao.entity.FeedbackOverviewData( " +
		    "CAST(COALESCE(COUNT(f), 0) AS INTEGER), " +                                 
		    "CAST(COALESCE(SUM(CASE WHEN f.ratings = 5 THEN 1 ELSE 0 END), 0) AS INTEGER), " + 
		    "CAST(COALESCE(SUM(CASE WHEN f.ratings = 4 THEN 1 ELSE 0 END), 0) AS INTEGER), " + 
		    "CAST(COALESCE(SUM(CASE WHEN f.ratings = 3 THEN 1 ELSE 0 END), 0) AS INTEGER), " + 
		    "CAST(COALESCE(SUM(CASE WHEN f.ratings = 2 THEN 1 ELSE 0 END), 0) AS INTEGER), " + 
		    "CAST(COALESCE(SUM(CASE WHEN f.ratings = 1 THEN 1 ELSE 0 END), 0) AS INTEGER) " +  
		    ") " +
		    "FROM FeedbackEntity f";


	@Query(GET_FEEDBACK_OVERVIEW)
	public FeedbackOverviewData getFeedbackOverview() throws DataAccessException;
	

	public final String GET_FEEDBACK_BY_RESULT_ID = "SELECT e "
			+ "FROM FeedbackEntity e "
			+ "WHERE e.resultIdPk = :resultIdPk ";
	
	@Query(GET_FEEDBACK_BY_RESULT_ID)
	public FeedbackEntity getFeedbackByResultId(@Param("resultIdPk") int resultIdPk) throws DataAccessException;
	
	public final String GET_ALL_FEEDBACK = """
				SELECT
					e.resultIdPk,
					u.firstName,
					u.lastName,
					u.username,
					e.ratings,
					e.feedback
				FROM FeedbackEntity e
				LEFT JOIN AssessmentResultEntity r ON r.idPk = e.resultIdPk
				LEFT JOIN UserEntity u ON u.idPk = r.userIdPk
				WHERE ( 
				   (:search IS NOT NULL AND :search <> '' AND ( 
				       LOWER(u.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR 
				       LOWER(u.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR 
				       LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) OR 
				       LOWER(CAST(e.ratings AS CHARACTER)) LIKE LOWER(CONCAT('%', :search, '%')) OR 
				       LOWER(e.feedback) LIKE LOWER(CONCAT('%', :search, '%'))
				   )) 
				   OR (:search IS NULL OR :search = '') 
				)
			""";
	
	@Query(GET_ALL_FEEDBACK)
	public Page<FeedbackData> getAllFeedbacks(Pageable pageable,
			@Param("search") String search) throws DataAccessException;
}
