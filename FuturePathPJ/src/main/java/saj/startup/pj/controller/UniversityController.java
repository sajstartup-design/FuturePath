package saj.startup.pj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UniversityController {

	@GetMapping("/admin/universities")
	public String showUniversities() {
		
		return "university/university-view";
	}
}
