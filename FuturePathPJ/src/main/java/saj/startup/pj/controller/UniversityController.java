package saj.startup.pj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import saj.startup.pj.model.dto.StrandegreeDto;
import saj.startup.pj.model.dto.UniversityDto;
import saj.startup.pj.model.service.StrandegreeService;

@Controller
public class UniversityController {
	
	@Autowired
	private StrandegreeService strandegreeService;

	@GetMapping("/admin/universities")
	public String showUniversities(Model model) {
		
		model.addAttribute("page", "universities");
		
		return "university/university-view";
	}
	
	@GetMapping("/admin/universities/add")
	public String showUniversitiesAdd(Model model, @ModelAttribute UniversityDto webDto) {
		
		try {
			
			StrandegreeDto outDto = strandegreeService.getAllStrandegreesNoPageable();
			
			model.addAttribute("strandegreeDto", outDto);
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		model.addAttribute("page", "universities");
		
		return "university/university-add";
	}
}
