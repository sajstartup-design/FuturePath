package saj.startup.pj.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import saj.startup.pj.model.dao.entity.AnalyticsEntity;

public interface AnalyticsDao extends JpaRepository<AnalyticsEntity, Integer>{

}
