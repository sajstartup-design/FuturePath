package saj.startup.pj.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import saj.startup.pj.common.MessageConstant;
import saj.startup.pj.model.dto.AssessmentDto;
import saj.startup.pj.model.dto.QuestionDto;
import saj.startup.pj.model.service.AssessmentService;
import saj.startup.pj.model.service.QuestionService;

@Controller
public class AssessmentController {
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AssessmentService assessmentService;

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
	    	
	    }catch(Exception e) {
	    	e.printStackTrace();
	    	
	    	ra.addFlashAttribute("isError", true);
	    	ra.addFlashAttribute("errorMsg", MessageConstant.SOMETHING_WENT_WRONG);
	    	
	    	return "redirect:/dashboard";
	    }
	    
	    model.addAttribute("mode", webDto.getMode());
	    
	    return "assessment/assessment";
	}
	
	@PostMapping("/assessment/result")
	public String submitAssessment(Model model, 
			@ModelAttribute AssessmentDto assessmentDto,
            @RequestParam("answeredJson") String answeredJson,
            RedirectAttributes ra) {

		try {
			AssessmentDto outDto = assessmentService.getAssessmentResult(assessmentDto);
			
			model.addAttribute("assessmentDto", outDto);
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
			ra.addFlashAttribute("isError", true);
			ra.addFlashAttribute("errorMsg", MessageConstant.SOMETHING_WENT_WRONG);
			
			return "redirect:/dashobard";
			
		}
		

		return "assessment/assessment-result";
	}
}
