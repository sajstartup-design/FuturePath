package saj.startup.pj.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import saj.startup.pj.model.dao.entity.HistoryQuestionEntity;

public interface HistoryQuestionDao extends JpaRepository<HistoryQuestionEntity, String> {

}
