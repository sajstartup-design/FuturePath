package saj.startup.pj.model.dto;

import java.util.List;

import lombok.Data;
import saj.startup.pj.model.object.NotificationObj;

@Data
public class NotificationDto {

	private int userIdPk;
	
	private String message;
	
	private List<NotificationObj> notifications;
}
