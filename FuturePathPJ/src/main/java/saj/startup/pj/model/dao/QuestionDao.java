package saj.startup.pj.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import saj.startup.pj.model.dao.entity.QuestionEntity;

public interface QuestionDao extends JpaRepository<QuestionEntity, Integer>{

}
