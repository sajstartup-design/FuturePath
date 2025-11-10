package saj.startup.pj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import saj.startup.pj.model.dto.QuestionDto;
import saj.startup.pj.model.dto.StrandegreeDto;
import saj.startup.pj.model.dto.UniversityDto;
import saj.startup.pj.model.dto.UserDto;
import saj.startup.pj.model.service.QuestionService;
import saj.startup.pj.model.service.StrandegreeService;
import saj.startup.pj.model.service.UniversityService;
import saj.startup.pj.model.service.UserService;

@Controller
public class DashboardController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StrandegreeService strandegreeService;
	
	@Autowired
	private UniversityService universityService;
	
	@Autowired
	private QuestionService questionService;

	@GetMapping("/admin/dashboard")
	public String showAdminDashboard(Model model) {
		
		try {
			UserDto userOutDto = userService.getUserOverview();
			StrandegreeDto strandegreeOutDto = strandegreeService.getStrandegreeOverview();
			UniversityDto universityOutDto = universityService.getUniversitiesOverview();
			QuestionDto questionOutDto = questionService.getQuestionOverview();
					
			model.addAttribute("userDto", userOutDto);
			model.addAttribute("strandegreeDto", strandegreeOutDto);
			model.addAttribute("universityDto", universityOutDto);
			model.addAttribute("questionDto", questionOutDto);
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		
		model.addAttribute("page", "dashboard");
		
		return "dashboard/admin-dashboard";
	}
	
	@GetMapping("/dashboard")
	public String showUserDashboard(Model model) {
		
		return "dashboard/user-dashboard";
	}
}
