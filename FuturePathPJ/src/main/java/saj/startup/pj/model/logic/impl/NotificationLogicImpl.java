package saj.startup.pj.model.logic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.NotificationDao;
import saj.startup.pj.model.dao.entity.NotificationEntity;
import saj.startup.pj.model.logic.NotificationLogic;

@Service
public class NotificationLogicImpl implements NotificationLogic{
	
	@Autowired
	private NotificationDao notificationDao;

	@Override
	public void saveNotification(NotificationEntity entity) {
		
		notificationDao.save(entity);
	}

	@Override
	public List<NotificationEntity> getAllNotifications(int userIdPk) {
		
		return notificationDao.getAllNotifications(userIdPk);
	}

}
