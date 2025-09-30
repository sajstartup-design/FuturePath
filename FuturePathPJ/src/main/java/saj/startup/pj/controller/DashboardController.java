package saj.startup.pj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

	@GetMapping("/admin/dashboard")
	public String showAdminDashboard(Model model) {
		
		model.addAttribute("page", "dashboard");
		
		return "dashboard/dashboard";
	}
}
