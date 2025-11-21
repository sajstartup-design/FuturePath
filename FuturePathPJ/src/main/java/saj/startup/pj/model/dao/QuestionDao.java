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
import saj.startup.pj.model.dao.entity.AssessmentCheckerData;
import saj.startup.pj.model.dao.entity.QuestionData;
import saj.startup.pj.model.dao.entity.QuestionEntity;
import saj.startup.pj.model.dao.entity.QuestionOverviewData;

public interface QuestionDao extends JpaRepository<QuestionEntity, Integer>{

	public final String GET_QUESTION_OVERVIEW =
		    "SELECT new saj.startup.pj.model.dao.entity.QuestionOverviewData( " +
		    "CAST(COALESCE(COUNT(e), 0) AS INTEGER), " + 
		    "CAST(COALESCE(SUM(CASE WHEN s.category = 'DEGREE' AND e.isActive = true THEN 1 ELSE 0 END), 0) AS INTEGER), " + 
		    "CAST(COALESCE(SUM(CASE WHEN s.category = 'STRAND' AND e.isActive = true THEN 1 ELSE 0 END), 0) AS INTEGER), " + 
		    "CAST(COALESCE(SUM(CASE WHEN e.isActive = true THEN 1 ELSE 0 END), 0) AS INTEGER), " + // total active
		    "CAST(COALESCE(SUM(CASE WHEN e.isActive = false THEN 1 ELSE 0 END), 0) AS INTEGER) " + // total inactive
		    ") " +
		    "FROM QuestionEntity e " +
		    "LEFT JOIN StrandegreeEntity s ON s.idPk = e.strandegreeIdPk " +
		    "WHERE e.isDeleted = false";


	
	@Query(GET_QUESTION_OVERVIEW)
	public QuestionOverviewData getQuestionOverview() throws DataAccessException;
	
	public final String GET_ALL_QUESTIONS = "SELECT new saj.startup.pj.model.dao.entity.QuestionData("
			+ " e.idPk, s.category, e.question, s.code, e.isActive, e.createdAt "
			+ ") "
			+ "FROM QuestionEntity e "
			+ "LEFT JOIN StrandegreeEntity s ON s.idPk = e.strandegreeIdPk "
			+ "WHERE e.isDeleted = false "
			+ "AND ( "
		    + "   (:search IS NOT NULL AND :search <> '' AND ( " 
		    + "       LOWER(s.category) LIKE LOWER(CONCAT('%', :search, '%')) OR " 
		    + "       LOWER(e.question) LIKE LOWER(CONCAT('%', :search, '%')) OR " 
		    + "       LOWER(s.code) LIKE LOWER(CONCAT('%', :search, '%')) OR " 
		    + "       LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " 
		    + "       LOWER(CAST(e.isActive AS CHARACTER)) LIKE LOWER(CONCAT('%', :search, '%'))" 
		    + "   )) " 
		    + "   OR (:search IS NULL OR :search = '') " 
		    + ") "
		    + "ORDER BY e.idPk";
	
	@Query(GET_ALL_QUESTIONS)
	public Page<QuestionData> getAllQuestions(Pageable pageable,
			@Param("search") String search) throws DataAccessException;
	
	public final String GET_QUESTIONS_FOR_ASSESSMENT = """
		    SELECT 
		        e.id_pk AS questionIdPk,
		        s.category AS category,
		        e.question AS question,
		        s.code AS strandegree,
		        e.is_active AS isActive,
		        e.created_at AS createdAt
		    FROM question e
		    INNER JOIN strandegrees s ON s.id_pk = e.strandegree_id_pk
		    WHERE e.is_deleted = false 
		      AND s.category = :category
		      AND s.code IN :programs
		    ORDER BY RANDOM()
		    LIMIT :limit
		    """;

	@Query(value = GET_QUESTIONS_FOR_ASSESSMENT, nativeQuery = true)
	public List<QuestionData> getQuestionsForAssessment(@Param("programs") List<String> programs,
			@Param("category") String category,
			@Param("limit") int limit) throws DataAccessException;
	
	public final String GET_QUESTIONS_FOR_ASSESSMENT_BY_RIASEC_CODE = """
		    SELECT 
		        e.id_pk AS questionIdPk,
		        s.category AS category,
		        e.question AS question,
		        s.code AS strandegree,
		        e.is_active AS isActive,
		        e.created_at AS createdAt
		    FROM question e
		    INNER JOIN strandegrees s ON s.id_pk = e.strandegree_id_pk
		    WHERE e.is_deleted = false 
		      AND s.category = :category
		      AND s.riasec_code IN :riasecCodes
		    ORDER BY RANDOM()
		    LIMIT :limit
		    """;

	@Query(value = GET_QUESTIONS_FOR_ASSESSMENT_BY_RIASEC_CODE, nativeQuery = true)
	public List<QuestionData> getQuestionsForAssessmentByRiasecCode(@Param("riasecCodes") List<Integer> riasecCodes,
			@Param("category") String category,
			@Param("limit") int limit) throws DataAccessException;

	
	public final String QUESTION_ASSESSMENT_CHECKER = """
				
				SELECT 
					s.idPk,
					s.code,
					s.name,
					q.idPk,
					a.idPk,
					a.isCorrect
				FROM QuestionEntity q 
				LEFT JOIN AnswerEntity a ON a.questionIdPk = q.idPk AND a.idPk = :answerIdPk
				LEFT JOIN StrandegreeEntity s ON s.idPk = q.strandegreeIdPk
				WHERE q.idPk = :questionIdPk
				
			""";
	
	@Query(QUESTION_ASSESSMENT_CHECKER)
	public AssessmentCheckerData getQuestionAssessmentChecker(@Param("questionIdPk") int questionIdPk,
			@Param("answerIdPk") int answerIdPk) throws DataAccessException;
	
	public final String GET_QUESTION = """
				SELECT q FROM QuestionEntity q WHERE q.idPk = :idPk
			""";
	
	@Query(value=GET_QUESTION)
	public QuestionEntity getQuestion(@Param("idPk") int idPk) throws DataAccessException;
	
	public final String DELETE_QUESTION = """
			UPDATE question
			SET is_deleted = true
			WHERE id_pk = :idPk
		""";

	@Transactional
	@Modifying
	@Query(value=DELETE_QUESTION, nativeQuery=true)
	public void deleteQuestion(@Param("idPk") int idPk) throws DataAccessException;
	
}
