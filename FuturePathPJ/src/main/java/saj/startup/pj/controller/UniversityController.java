package saj.startup.pj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import saj.startup.pj.common.MessageConstant;
import saj.startup.pj.model.dto.StrandegreeDto;
import saj.startup.pj.model.dto.UniversityDto;
import saj.startup.pj.model.service.StrandegreeService;
import saj.startup.pj.model.service.UniversityService;

@Controller
public class UniversityController {
	
	@Autowired
	private StrandegreeService strandegreeService;
	
	@Autowired
	private UniversityService universityService;

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
	
	@PostMapping("/admin/universities/add")
	public String postUniversitiesAdd(@ModelAttribute UniversityDto webDto,
			RedirectAttributes ra) {
	      
	    try {
		    universityService.saveUniversity(webDto);
		    
		    ra.addFlashAttribute("isSuccess", true);
		    ra.addFlashAttribute("successMsg", MessageConstant.UNIVERSITY_ADDED);
		    
	    } catch(Exception e) {
	    	
	    	e.printStackTrace();
	    	
			ra.addFlashAttribute("isError", true);
			ra.addFlashAttribute("errorMsg", MessageConstant.SOMETHING_WENT_WRONG);
	    }
	   
	    return "redirect:/admin/universities";
	}

}
