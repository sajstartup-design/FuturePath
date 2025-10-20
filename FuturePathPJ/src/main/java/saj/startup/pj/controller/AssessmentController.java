package saj.startup.pj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import saj.startup.pj.common.MessageConstant;
import saj.startup.pj.model.dto.AssessmentDto;
import saj.startup.pj.model.dto.QuestionDto;
import saj.startup.pj.model.service.QuestionService;

@Controller
public class AssessmentController {
	
	@Autowired
	private QuestionService questionService;

	@GetMapping("/assessment")
	public String showQuizHome() {
		
		return "assessment/assessment-home";
	}
	
	@PostMapping(value="/assessment", params="mode")
	public String showQuizDegree(Model model, @ModelAttribute AssessmentDto webDto,
			RedirectAttributes ra) {
		
	    System.out.println("Selected: " + webDto.getMode());

	    QuestionDto inDto = new QuestionDto();
	    
	    inDto.setMode(webDto.getMode());
	    
	    inDto.setDegrees(webDto.getDegrees());
	    
	    inDto.setStrands(webDto.getStrands());
	    
	    try {
	    	QuestionDto outDto = questionService.getQuestionsForAssessment(inDto);
	    	
	    	model.addAttribute("questionDto", outDto);
	    	
	    	System.out.println("AW");
	    	
	    }catch(Exception e) {
	    	e.printStackTrace();
	    	
	    	ra.addFlashAttribute("isError", true);
	    	ra.addFlashAttribute("errorMsg", MessageConstant.SOMETHING_WENT_WRONG);
	    	
	    	return "redirect:/dashboard";
	    }
	    
	    model.addAttribute("mode", webDto.getMode());
	    
	    return "assessment/assessment";
	}
}
