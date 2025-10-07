package saj.startup.pj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuizController {

	@GetMapping("/quiz")
	public String showQuizHome() {
		
		return "quiz/quiz-home";
	}
	
	@PostMapping(value="/quiz", params="degree")
	public String showQuizDegree(@RequestParam String degree) {
	    System.out.println("Selected: " + degree);
	    if ("college".equals(degree)) {
	        return "quiz/quiz-degree";
	    } else if ("strand".equals(degree)) {	
	        return "quiz/quiz-strand";
	    }
	    return "redirect:/dashboard";
	}
}
