package saj.startup.pj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HistoryController {
	

	@GetMapping("/history")
	public String showHistory(Model model) {
			
		model.addAttribute("page", "history");
		
		return "history/history-list";
	}
}
