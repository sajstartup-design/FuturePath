package saj.startup.pj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ch.qos.logback.core.model.Model;

@Controller
public class StrandegreeController {

	@GetMapping("/admin/strandegrees") 
	public String showStrandegrees(Model model) {
		
		return "strandegree/strandegree-view";
	}
}
