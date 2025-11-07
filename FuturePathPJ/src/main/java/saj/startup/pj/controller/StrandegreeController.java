package saj.startup.pj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import saj.startup.pj.common.CommonConstant;
import saj.startup.pj.common.MessageConstant;
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
	public String postStrandegreeAdd(@ModelAttribute StrandegreeDto webDto,
			RedirectAttributes ra) {
		
		try {
			
			strandegreeService.saveStrandegree(webDto);
			
			ra.addFlashAttribute("isSuccess", true);
			
			if(CommonConstant.DEGREE.equals(webDto.getCategory())) {
				
				ra.addFlashAttribute("successMsg", MessageConstant.DEGREE_ADDED);
			}else {
				ra.addFlashAttribute("successMsg", MessageConstant.STRAND_ADDED);
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
			
			ra.addFlashAttribute("isError", true);
			ra.addFlashAttribute("errorMsg", MessageConstant.SOMETHING_WENT_WRONG);
					
		}	
		
		return "redirect:/admin/strandegrees";
	}
	
	@GetMapping("/admin/strandegrees/edit")
	public String showStrandegreeEdit(@ModelAttribute StrandegreeDto webDto,
			Model model,
			RedirectAttributes ra) {
		
		try {
			
			StrandegreeDto outDto = strandegreeService.getStrandegree(webDto);
			outDto.setIdPk(webDto.getIdPk());
			
			model.addAttribute("strandegreeDto", outDto);
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
			ra.addFlashAttribute("isError", true);
			ra.addFlashAttribute("errorMsg", MessageConstant.SOMETHING_WENT_WRONG);
			
			return "redirect:/admin/strandegrees";
		}
			
		model.addAttribute("page", "strandegrees");
		
		return "strandegree/strandegree-edit";
	}
	
	@PostMapping("/admin/strandegrees/edit")
	public String postStrandegreeEdit(@ModelAttribute StrandegreeDto webDto,
			RedirectAttributes ra) {
		
		try {
			
			strandegreeService.updateStrandegree(webDto);
			
			ra.addFlashAttribute("isSuccess", true);
			
			if(CommonConstant.DEGREE.equals(webDto.getCategory())) {
				
				ra.addFlashAttribute("successMsg", MessageConstant.DEGREE_EDITED);
			}else {
				ra.addFlashAttribute("successMsg", MessageConstant.STRAND_EDITED);
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
			
			ra.addFlashAttribute("isError", true);
			ra.addFlashAttribute("errorMsg", MessageConstant.SOMETHING_WENT_WRONG);
					
		}	
		
		return "redirect:/admin/strandegrees";
	}
	
	@PostMapping("/admin/strandegrees/delete")
	public String deleteStrandegrees(@ModelAttribute StrandegreeDto webDto,
			RedirectAttributes ra) {
		
		try {
			
			strandegreeService.deleteStrandegree(webDto);
			
			ra.addFlashAttribute("isSuccess", true);
			
			if(CommonConstant.DEGREE.equals(webDto.getCategory())) {
				
				ra.addFlashAttribute("successMsg", MessageConstant.DEGREE_DELETED);
			}else {
				ra.addFlashAttribute("successMsg", MessageConstant.STRAND_DELETED);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			
			ra.addFlashAttribute("isError", true);
			ra.addFlashAttribute("errorMsg", MessageConstant.SOMETHING_WENT_WRONG);
		}
		
		return "redirect:/admin/strandegrees";
	}
	
	/*
	 * USER
	 * 
	 */
	@GetMapping("/strandegrees")
	public String showUniversities() {
		
		
		
		return "strandegree/strandegree-list";
	}
}
