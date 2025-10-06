package saj.startup.pj.model.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import saj.startup.pj.model.dao.entity.QuestionEntity;
import saj.startup.pj.model.dao.entity.QuestionOverviewData;

public interface QuestionDao extends JpaRepository<QuestionEntity, Integer>{

	public final String GET_QUESTION_OVERVIEW =
		    "SELECT new saj.startup.pj.model.dao.entity.QuestionOverviewData( " +
		    "CAST(COUNT(e) AS INTEGER), " + 
		    "CAST(SUM(CASE WHEN s.category = 'DEGREE' AND e.isActive = true THEN 1 ELSE 0 END) AS INTEGER), " + 
		    "CAST(SUM(CASE WHEN s.category = 'STRAND' AND e.isActive = true THEN 1 ELSE 0 END) AS INTEGER), " + 
		    "CAST(SUM(CASE WHEN e.isActive = true THEN 1 ELSE 0 END) AS INTEGER), " + // total active
		    "CAST(SUM(CASE WHEN e.isActive = false THEN 1 ELSE 0 END) AS INTEGER) " + // total inactive
		    ") " +
		    "FROM QuestionEntity e " +
		    "LEFT JOIN StrandegreeEntity s ON s.idPk = e.strandegreeIdPk " +
		    "WHERE e.isDeleted = false";

	
	@Query(GET_QUESTION_OVERVIEW)
	public QuestionOverviewData getQuestionOverview() throws DataAccessException;
}
