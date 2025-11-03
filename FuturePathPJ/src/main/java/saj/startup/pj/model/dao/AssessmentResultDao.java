package saj.startup.pj.model.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import saj.startup.pj.model.dao.entity.AssessmentResultEntity;

public interface AssessmentResultDao extends JpaRepository<AssessmentResultEntity, Integer>{

	public final String GET_ASSESSMENT_RESULT = """
				SELECT *
				FROM assessment_result
				WHERE id_pk = :resultIdPk
			""";
	
	@Query(value=GET_ASSESSMENT_RESULT, nativeQuery=true)
	public AssessmentResultEntity getAssessmentResult(@Param("resultIdPk") int resultIdPk) throws DataAccessException;

}
