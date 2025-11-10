package saj.startup.pj.model.dao;


import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import saj.startup.pj.model.dao.entity.AssessmentResultEntity;
import saj.startup.pj.model.dao.projection.AssessmentResultData;
import saj.startup.pj.model.dao.projection.AssessmentStatisticsData;

public interface AssessmentResultDao extends JpaRepository<AssessmentResultEntity, Integer>{

	public final String GET_ASSESSMENT_RESULT = """
				SELECT *
				FROM assessment_result
				WHERE id_pk = :resultIdPk
			""";
	
	@Query(value=GET_ASSESSMENT_RESULT, nativeQuery=true)
	public AssessmentResultEntity getAssessmentResult(@Param("resultIdPk") int resultIdPk) throws DataAccessException;
	
	public final String GET_ALL_ASSESSMENT_RESULT_BY_USER = """
			SELECT 
		        id_pk AS result_id_pk,
		        correct AS total_correct,
		        incorrect AS total_incorrect,
		        total_question AS total_question,
		        score AS score,
		        date_taken AS date_taken
		    FROM assessment_result
		    WHERE user_id_pk = :userIdPk
		      AND (
		          :search IS NULL OR :search = '' OR 
		          CAST(correct AS TEXT) ILIKE CONCAT('%', :search, '%') OR
		          CAST(incorrect AS TEXT) ILIKE CONCAT('%', :search, '%') OR
		          CAST(total_question AS TEXT) ILIKE CONCAT('%', :search, '%') OR
		          CAST(score AS TEXT) ILIKE CONCAT('%', :search, '%') OR
		          TO_CHAR(date_taken, 'YYYY-MM-DD HH24:MI') ILIKE CONCAT('%', :search, '%')
		      )
		    ORDER BY date_taken ASC
		""";

	@Query(value=GET_ALL_ASSESSMENT_RESULT_BY_USER, nativeQuery=true)
	public Page<AssessmentResultData> getAllAssessmentResultByUser(Pageable pageable,
			@Param("search") String search,
			@Param("userIdPk") int userIdPk) throws DataAccessException;
	
	public final String GET_ASSESSMENT_STATISTICS = """
				SELECT
				    COALESCE(CAST(COUNT(*) AS INT), 0) AS takenAssessments,
				    COALESCE(ROUND(CAST(AVG(score) AS NUMERIC), 2), 0) AS averageScores,
				    COALESCE(CAST(COUNT(CASE WHEN category = 'DEGREE' THEN 1 END) AS INT), 0) AS degreeAssessments,
				    COALESCE(ROUND(CAST(AVG(CASE WHEN category = 'DEGREE' THEN score END) AS NUMERIC), 2), 0) AS degreeAverageScores,
				    COALESCE(CAST(COUNT(CASE WHEN category = 'STRAND' THEN 1 END) AS INT), 0) AS strandAssessments,
				    COALESCE(ROUND(CAST(AVG(CASE WHEN category = 'STRAND' THEN score END) AS NUMERIC), 2), 0) AS strandAverageScores,
				    
				    -- Count assessments in each score bracket
				    COALESCE(CAST(COUNT(CASE WHEN score BETWEEN 0 AND 25 THEN 1 END) AS INT), 0) AS score0_25,
				    COALESCE(CAST(COUNT(CASE WHEN score BETWEEN 26 AND 50 THEN 1 END) AS INT), 0) AS score26_50,
				    COALESCE(CAST(COUNT(CASE WHEN score BETWEEN 51 AND 75 THEN 1 END) AS INT), 0) AS score51_75,
				    COALESCE(CAST(COUNT(CASE WHEN score BETWEEN 76 AND 100 THEN 1 END) AS INT), 0) AS score76_100
				
				FROM assessment_result;
			""";
	
	@Query(value=GET_ASSESSMENT_STATISTICS, nativeQuery=true)
	public AssessmentStatisticsData getAssessmentStatistics() throws DataAccessException;
}
