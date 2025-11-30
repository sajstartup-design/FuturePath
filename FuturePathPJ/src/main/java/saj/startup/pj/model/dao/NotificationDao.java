package saj.startup.pj.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import saj.startup.pj.model.dao.entity.NotificationEntity;

public interface NotificationDao extends JpaRepository<NotificationEntity, Integer>{

	public final String GET_ALL_NOTIFICATIONS = "SELECT e "
			+ "FROM NotificationEntity e "
			+ "WHERE e.userIdPk = :userIdPk "
			+ "OR e.userIdPk = 0 ";
	
	@Query(GET_ALL_NOTIFICATIONS)
	public List<NotificationEntity> getAllNotifications(@Param("userIdPk") int userIdPk) throws DataAccessException;
}
