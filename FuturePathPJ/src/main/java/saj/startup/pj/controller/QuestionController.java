package saj.startup.pj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuestionController {

	@GetMapping("/admin/questions")
	public String showQuestions() {
		
		return "question/question-view";
	}
	
	@GetMapping("/admin/questions/add")
	public String showQuestionsAdd() {
		
		return "question-question-add";
	}
}
