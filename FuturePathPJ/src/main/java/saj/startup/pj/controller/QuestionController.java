package saj.startup.pj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
