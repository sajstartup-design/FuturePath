package saj.startup.pj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import saj.startup.pj.model.dto.StrandegreeDto;
import saj.startup.pj.model.service.StrandegreeService;

@Controller
public class StrandegreeController {
	
	@Autowired
	private StrandegreeService strandegreeService;

	@GetMapping("/admin/strandegrees") 
	public String showStrandegrees(Model model) {
		
		try {
			
			StrandegreeDto outDto = strandegreeService.getStrandegreeOverview();
			
			model.addAttribute("strandegreeDto", outDto);
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		model.addAttribute("page", "strandegrees");
		
		return "strandegree/strandegree-view";
	}
	
	@GetMapping("/admin/strandegrees/add")
	public String showStrandegreesAdd(Model model) {
		
		model.addAttribute("page", "strandegrees");
		
		return "strandegree/strandegree-add";
	}
	
	@PostMapping("/admin/strandegrees/add")
	public String postStrandegreeAdd(@ModelAttribute StrandegreeDto webDto) {
		
		try {
			
			strandegreeService.saveStrandegree(webDto);
			
		}catch(Exception e) {
			
			e.printStackTrace();
					
		}	
		
		return "redirect:/admin/strandegrees";
	}
}
