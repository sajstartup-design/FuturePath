package saj.startup.pj.model.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saj.startup.pj.common.util.TimeAgoUtil;
import saj.startup.pj.model.dao.entity.NotificationEntity;
import saj.startup.pj.model.dao.entity.UserEntity;
import saj.startup.pj.model.dto.NotificationDto;
import saj.startup.pj.model.logic.NotificationLogic;
import saj.startup.pj.model.object.NotificationObj;
import saj.startup.pj.model.service.NotificationService;
import saj.startup.pj.model.service.UserService;

@Service
public class NotificationServiceImpl implements NotificationService{
	
	@Autowired
	private NotificationLogic notificationLogic;

	@Autowired
	private UserService userService;

	@Override
	public void saveNotification(NotificationDto inDto) throws Exception{
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		NotificationEntity notification = new NotificationEntity();
		
		notification.setUserIdPk(inDto.getUserIdPk());
		notification.setMessage(inDto.getMessage());
		notification.setIsDeleted(false);
		notification.setCreatedAt(timestamp);

		notificationLogic.saveNotification(notification);
	}

	@Override
	public NotificationDto getAllNotifications() throws Exception {
		
		NotificationDto outDto = new NotificationDto();
		
		UserEntity active = userService.getUserActive();
		
		List<NotificationEntity> allNotifications = notificationLogic.getAllNotifications(active.getIdPk());
		
		List<NotificationObj> notifications = new ArrayList<>();
		
		for(NotificationEntity notification : allNotifications) {
			
			NotificationObj obj = new NotificationObj();
			
			obj.setIdPk(notification.getIdPk());
			obj.setMessage(notification.getMessage());
			obj.setTimeAgo(TimeAgoUtil.toTimeAgo(notification.getCreatedAt()));
			
			notifications.add(obj);
		}
		
		outDto.setNotifications(notifications);
		
		return outDto;
	}

}
