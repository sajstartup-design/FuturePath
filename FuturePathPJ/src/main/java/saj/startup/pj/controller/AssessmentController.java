package saj.startup.pj.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

import saj.startup.pj.common.CommonConstant;
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
	
	@GetMapping("/assessment/riasec")
	public String showAssessmentRiasec() {
		
		return "assessment/assessment-riasec";
	}
	
	@PostMapping("/assessment/riasec/result")
	public String showAssessmentRiasecResult(Model model, AssessmentDto webDto) throws Exception {

	    // 🔹 Create input DTO and copy all RIASEC totals from the web form
	    AssessmentDto inDto = new AssessmentDto();
	    inDto.setAnsweredJson(webDto.getAnsweredJson());
	    inDto.setMode(webDto.getMode());
	    inDto.setRealistic(webDto.getRealistic());
	    inDto.setInvestigative(webDto.getInvestigative());
	    inDto.setArtistic(webDto.getArtistic());
	    inDto.setSocial(webDto.getSocial());
	    inDto.setEnterprising(webDto.getEnterprising());
	    inDto.setConventional(webDto.getConventional());

	    inDto.setCombination(webDto.getCombination());

	    // 🔹 Debug: log totals
	    System.out.println("RIASEC totals from form:");
	    System.out.println("R: " + inDto.getRealistic());
	    System.out.println("I: " + inDto.getInvestigative());
	    System.out.println("A: " + inDto.getArtistic());
	    System.out.println("S: " + inDto.getSocial());
	    System.out.println("E: " + inDto.getEnterprising());
	    System.out.println("C: " + inDto.getConventional());

	    AssessmentDto dto = assessmentService.getAssessmentRIASECResult(inDto);

	    model.addAttribute("assessmentDto", dto);

	    return "assessment/assessment-riasec-result";
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
