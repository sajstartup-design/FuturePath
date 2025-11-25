package saj.startup.pj.model.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import saj.startup.pj.model.dao.entity.FeedbackEntity;

public interface FeedbackDao extends JpaRepository<FeedbackEntity, Integer> {

	public final String GET_FEEDBACK_BY_RESULT_ID = "SELECT e "
			+ "FROM FeedbackEntity e "
			+ "WHERE e.resultIdPk = :resultIdPk ";
	
	@Query(GET_FEEDBACK_BY_RESULT_ID)
	public FeedbackEntity getFeedbackByResultId(@Param("resultIdPk") int resultIdPk) throws DataAccessException;
}
