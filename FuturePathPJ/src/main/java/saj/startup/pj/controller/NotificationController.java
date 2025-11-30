package saj.startup.pj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import saj.startup.pj.common.MessageConstant;
import saj.startup.pj.model.dto.NotificationDto;
import saj.startup.pj.model.service.NotificationService;

@Controller
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;

	@GetMapping("/admin/push-notification")
	public String showPushNotificationPage(@ModelAttribute NotificationDto inDto) {
		
		return "notification/push-notification";
	}
	
	@PostMapping("/admin/push-notification")
	public String postPushNotificationPage(@ModelAttribute NotificationDto inDto,
			RedirectAttributes ra) {
		
		try {
			
			notificationService.saveNotification(inDto);
			
			ra.addFlashAttribute("isSuccess", true);
			ra.addFlashAttribute("successMsg", MessageConstant.PUSH_NOTIFICATION_SENT);
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
			ra.addFlashAttribute("isError", true);
			ra.addFlashAttribute("errorMsg", MessageConstant.SOMETHING_WENT_WRONG);
		}

		return "redirect:/admin/push-notification";
	}
}
