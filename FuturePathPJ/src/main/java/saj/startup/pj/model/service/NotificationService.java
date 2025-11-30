package saj.startup.pj.model.service;

import org.springframework.stereotype.Service;

import saj.startup.pj.model.dto.NotificationDto;

@Service
public interface NotificationService {

	public void saveNotification(NotificationDto inDto) throws Exception;
	
	public NotificationDto getAllNotifications() throws Exception;
}
