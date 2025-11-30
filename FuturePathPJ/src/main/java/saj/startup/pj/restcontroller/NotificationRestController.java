package saj.startup.pj.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import saj.startup.pj.model.dto.NotificationDto;
import saj.startup.pj.model.service.NotificationService;

@RestController
@RequestMapping("/api/notification")
public class NotificationRestController {

	@Autowired
	private NotificationService notificationService;
	
	@GetMapping
	public NotificationDto getNotifications() {
		try {
			NotificationDto outDto = notificationService.getAllNotifications();
			
			return outDto;
		}catch(Exception e) {
			
			e.printStackTrace();
			
			return new NotificationDto();
		}
	}
}
