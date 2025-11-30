package saj.startup.pj.model.logic;

import java.util.List;

import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.entity.NotificationEntity;

@Service
public interface NotificationLogic {

	public void saveNotification(NotificationEntity entity);
	
	public List<NotificationEntity> getAllNotifications(int userIdPk);
}
