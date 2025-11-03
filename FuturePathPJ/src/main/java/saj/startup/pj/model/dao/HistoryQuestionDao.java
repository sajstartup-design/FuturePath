package saj.startup.pj.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import saj.startup.pj.model.dao.entity.HistoryQuestionData;
import saj.startup.pj.model.dao.entity.HistoryQuestionEntity;
import saj.startup.pj.model.dao.entity.key.HistoryQuestionKey;

public interface HistoryQuestionDao extends JpaRepository<HistoryQuestionEntity, HistoryQuestionKey> {

	public final String GET_HISTORY_QUESTIONS_BY_RESULT_ID_PK = """
				SELECT 
				    COALESCE(q.question, '[Question not found]') AS question,
				    COALESCE(ca.answer, '[No correct answer recorded]') AS correct_answer,
				    COALESCE(ua.answer, '[No user answer]') AS user_answer,
				    COALESCE(s.code, '[No Code]') AS code,
				    COALESCE(s.name, '[No Name]') AS name,
				    COALESCE(ua.is_correct, false) AS is_correct
				FROM history_question hq
				LEFT JOIN question q 
				    ON q.id_pk = hq.question_id_pk
				LEFT JOIN answer ca 
				    ON ca.question_id_pk = hq.question_id_pk 
				   AND ca.is_correct = true
				LEFT JOIN answer ua 
				    ON ua.id_pk = hq.answer_id_pk
				LEFT JOIN strandegrees s
					ON s.id_pk = q.strandegree_id_pk
				WHERE
					hq.result_id_pk = :resultIdPk;
			""";
	
	@Query(value=GET_HISTORY_QUESTIONS_BY_RESULT_ID_PK, nativeQuery=true)
	public List<HistoryQuestionData> getHistoryQuestionsByResultIdPk(int resultIdPk) throws DataAccessException;
	
}
