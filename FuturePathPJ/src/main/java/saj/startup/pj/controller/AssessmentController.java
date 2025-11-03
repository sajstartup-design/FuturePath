package saj.startup.pj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import saj.startup.pj.common.CommonConstant;
import saj.startup.pj.common.MessageConstant;
import saj.startup.pj.model.dto.AssessmentDto;
import saj.startup.pj.model.dto.QuestionDto;
import saj.startup.pj.model.dto.StrandegreeDto;
import saj.startup.pj.model.service.AssessmentService;
import saj.startup.pj.model.service.QuestionService;
import saj.startup.pj.model.service.StrandegreeService;

@Controller
public class AssessmentController {
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AssessmentService assessmentService;
	
	@Autowired
	private StrandegreeService strandegreeService;

	@GetMapping("/assessment")
	public String showQuizHome(Model model) {
		
		try {
			
			StrandegreeDto strandegreeOutDto = strandegreeService.getStrandegreesQuestionsOverview();
			
			System.out.println(strandegreeOutDto);
			
			model.addAttribute("strandegreeDto", strandegreeOutDto);
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return "assessment/assessment-home";
	}
	
	@GetMapping("/assessment/riasec/degree")
	public String showAssessmentRiasec() {
		
		return "assessment/assessment-riasec";
	}
	
	@PostMapping("/assessment/riasec/result")
	public String showAssessmentRiasecResult(AssessmentDto webDto, RedirectAttributes ra) throws Exception {

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

	    AssessmentDto outDto = assessmentService.getAssessmentRIASECResult(inDto);

	    ra.addAttribute("assessmentDto", outDto);

	    return "assessment/assessment-riasec-result";
	}
		
	@PostMapping(value = "/assessment", params = "mode")
	public String showQuizDegree(
	        Model model,
	        @ModelAttribute AssessmentDto webDto,
	        RedirectAttributes ra) {

	    System.out.println("Selected mode: " + webDto.getMode());

	    QuestionDto inDto = new QuestionDto();
	    inDto.setMode(webDto.getMode());
	    inDto.setDegrees(webDto.getDegrees());
	    inDto.setStrands(webDto.getStrands());
	    System.out.println(webDto.getDegrees());
	    
	    try {
	        switch (webDto.getMode()) {

	            case CommonConstant.DEGREE_DEFAULT_MODE:
	                // Default degree assessment redirects directly
	                return "redirect:/assessment/riasec/degree";

	            case CommonConstant.DEGREE_CUSTOM_MODE:
	                // Custom degree assessment: fetch questions
	                QuestionDto outDto = questionService.getQuestionsForAssessment(inDto);
	                model.addAttribute("questionDto", outDto);
	                break;

	            case CommonConstant.STRAND_DEFAULT_MODE:
	                System.out.println("UNDER CONSTRUCTION");
	                break;

	            case CommonConstant.STRAND_CUSTOM_MODE:
	            	System.out.println("UNDER CONSTRUCTION");
	                break;

	            default:
	                // Invalid mode
	                ra.addFlashAttribute("isError", true);
	                ra.addFlashAttribute("errorMsg", MessageConstant.SOMETHING_WENT_WRONG);
	                return "redirect:/dashboard";
	        }

	        model.addAttribute("mode", webDto.getMode());

	        return "assessment/assessment";

	    } catch (Exception e) {
	        e.printStackTrace();
	        ra.addFlashAttribute("isError", true);
	        ra.addFlashAttribute("errorMsg", MessageConstant.SOMETHING_WENT_WRONG);
	        return "redirect:/dashboard";
	    }
	}

	
	@PostMapping("/assessment/result")
	public String submitAssessment(Model model, 
			@ModelAttribute AssessmentDto assessmentDto,
            RedirectAttributes ra) {

		try {
			AssessmentDto outDto = assessmentService.saveAssessmentResult(assessmentDto);
			
			model.addAttribute("assessmentDto", outDto);
			
			return "redirect:/assessment/result/view?resultIdPk=" + outDto.getResultIdPk();
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
			ra.addFlashAttribute("isError", true);
			ra.addFlashAttribute("errorMsg", MessageConstant.SOMETHING_WENT_WRONG);
			
			return "redirect:/assessment";
			
		}
	}
	
	@GetMapping("/assessment/result/view")
	public String showAssessmentRiasecResult(Model model, AssessmentDto assessmentDto) throws Exception {
		
		AssessmentDto outDto = assessmentService.getAssessmentResult(assessmentDto);
		
		model.addAttribute("assessmentDto", outDto);

	    return "assessment/assessment-result";
	}
	
}
