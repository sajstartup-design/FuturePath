package saj.startup.pj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import saj.startup.pj.common.MessageConstant;
import saj.startup.pj.model.dto.QuestionDto;
import saj.startup.pj.model.dto.StrandegreeDto;
import saj.startup.pj.model.service.QuestionService;
import saj.startup.pj.model.service.StrandegreeService;

@Controller
public class QuestionController {
	
	@Autowired
	private StrandegreeService strandegreeService;
	
	@Autowired
	private QuestionService questionService;

	@GetMapping("/admin/questions")
	public String showQuestions(Model model) {
		
		try {
			
			StrandegreeDto strandegreeOutDto = strandegreeService.getStrandegreesQuestionsOverview();
			
			QuestionDto questionOutDto = questionService.getQuestionOverview();
			
			model.addAttribute("strandegreeDto", strandegreeOutDto);
			model.addAttribute("questionDto", questionOutDto);
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		model.addAttribute("page", "questions");
		
		return "question/question-view";
	}
	
	@GetMapping("/admin/questions/add")
	public String showQuestionsAdd(Model model) {
		
		try {
			
			StrandegreeDto outDto = strandegreeService.getAllStrandegreesNoPageable();
			
			model.addAttribute("strandegreeDto", outDto);
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return "question/question-add";
	}
	
	@PostMapping("/admin/questions/add")
	public String postQuestionsAdd(@ModelAttribute QuestionDto questionDto, Model model,
			RedirectAttributes ra) {

	    // Print everything to console (for debugging)
	    System.out.println("Category: " + questionDto.getCategory());
	    System.out.println("StrandegreeIdPk: " + questionDto.getStrandegreeIdPk());
	    System.out.println("Question: " + questionDto.getQuestion());
	    
	    if (questionDto.getAnswers() != null && !questionDto.getAnswers().isEmpty()) {
	        System.out.println("Answers:");
	        for (int i = 0; i < questionDto.getAnswers().size(); i++) {
	            System.out.println("  " + (i + 1) + ". " + questionDto.getAnswers().get(i));
	        }
	    } else {
	        System.out.println("No answers provided.");
	    }
	    
	    try {
	    	questionService.saveQuestion(questionDto);
	    	
	    	ra.addFlashAttribute("isSuccess", true);
	    	ra.addFlashAttribute("successMsg", MessageConstant.QUESTION_ADDED);
	    }catch(Exception e) {
	    	
	    	e.printStackTrace();
	    	
	    	ra.addFlashAttribute("isError", true);
	    	ra.addFlashAttribute("errorMsg", MessageConstant.SOMETHING_WENT_WRONG);
	    	
	    }

	    return "redirect:/admin/questions";
	}
	
	@GetMapping("/admin/questions/edit")
	public String showQuestionsEdit(Model model,
			@ModelAttribute QuestionDto webDto,
			RedirectAttributes ra) {
		
		try {
			
			QuestionDto outDto = questionService.getQuestionByIdPk(webDto);
			outDto.setIdPk(webDto.getIdPk());
			
			StrandegreeDto strandegreeOutDto = strandegreeService.getAllStrandegreesNoPageable();
			
			model.addAttribute("strandegreeDto", strandegreeOutDto);
			model.addAttribute("questionDto", outDto);
		}catch(Exception e) {
			
			e.printStackTrace();
			
			ra.addFlashAttribute("isError", true);
	    	ra.addFlashAttribute("errorMsg", MessageConstant.SOMETHING_WENT_WRONG);
		}
		
		return "question/question-edit";
	}
	
	@PostMapping("/admin/questions/edit")
	public String postQuestionsEdit(@ModelAttribute QuestionDto webDto,
			RedirectAttributes ra) {
		
		try {
			
			questionService.updateQuestion(webDto);
			
	    	ra.addFlashAttribute("isSuccess", true);
	    	ra.addFlashAttribute("successMsg", MessageConstant.QUESTION_EDITED);
			
		}catch(Exception e) {
			
			e.printStackTrace();
			
			ra.addFlashAttribute("isError", true);
	    	ra.addFlashAttribute("errorMsg", MessageConstant.SOMETHING_WENT_WRONG);
		}
		
		return "redirect:/admin/questions";
	}

}
