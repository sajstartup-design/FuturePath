package saj.startup.pj.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import saj.startup.pj.model.dao.entity.AnswerData;
import saj.startup.pj.model.dao.entity.AnswerEntity;

public interface AnswerDao extends JpaRepository<AnswerEntity, Integer>{

	public static final String GET_ANSWERS_BY_QUESTION_ID_PK = """
			SELECT e.idPk, e.questionIdPk, e.answer, e.isCorrect
			FROM AnswerEntity e
			WHERE e.questionIdPk = :questionIdPk
			AND e.isDeleted = false
			ORDER BY e.idPk
			""";
	
	@Query(GET_ANSWERS_BY_QUESTION_ID_PK)
	public List<AnswerData> getAnswersByQuestionIdPk(@Param("questionIdPk") int questionIdPk) throws DataAccessException;
	
	public static final String GET_ANSWERS_ENTITY_BY_QUESTION_ID_PK = """
			SELECT e
			FROM AnswerEntity e
			WHERE e.questionIdPk = :questionIdPk
			AND e.isDeleted = false
			ORDER BY e.idPk
			""";
	
	@Query(GET_ANSWERS_ENTITY_BY_QUESTION_ID_PK)
	public List<AnswerEntity> getAnswersEntityByQuestionIdPk(@Param("questionIdPk") int questionIdPk) throws DataAccessException;
	
	
	public static final String IS_ANSWER_CORRECT = """
			SELECT e.isCorrect
			     FROM AnswerEntity e
			     WHERE e.idPk = :answerIdPk
			     AND e.isDeleted = false
			""";
	
	@Query(IS_ANSWER_CORRECT)
	public  Boolean isAnswerCorrect(@Param("answerIdPk") int answerIdPk); 
}
