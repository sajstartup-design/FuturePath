package futurepath.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class DashboardController {
	
	@GetMapping("/dashboard")
	public String showDashboard(HttpServletRequest request) {
		
		System.out.println("YAWA");
	    System.out.println("YAWA path: " + request.getRequestURI());

		
		return "dashboard";
	}
}
