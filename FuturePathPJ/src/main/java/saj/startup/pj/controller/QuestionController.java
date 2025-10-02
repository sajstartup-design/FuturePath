package saj.startup.pj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import saj.startup.pj.model.dto.QuestionDto;
import saj.startup.pj.model.dto.StrandegreeDto;
import saj.startup.pj.model.service.StrandegreeService;

@Controller
public class QuestionController {
	
	@Autowired
	private StrandegreeService strandegreeService;

	@GetMapping("/admin/questions")
	public String showQuestions(Model model) {
		
		try {
			
			StrandegreeDto outDto = strandegreeService.getAllStrandegreesNoPageable();
			
			model.addAttribute("strandegreeDto", outDto);
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
	public String postQuestionsAdd(@ModelAttribute QuestionDto questionDto, Model model) {

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

	    // You can also add to model if needed
	    model.addAttribute("questionDto", questionDto);

	    return "redirect:/admin/questions";
	}

}
