package saj.startup.pj.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import saj.startup.pj.model.dao.entity.AssessmentResultEntity;
import saj.startup.pj.model.dao.projection.AssessmentResultData;

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

}
