package saj.startup.pj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import saj.startup.pj.common.MessageConstant;
import saj.startup.pj.model.dto.HistoryDto;
import saj.startup.pj.model.service.HistoryService;

@Controller
public class HistoryController {
	

	@GetMapping("/history")
	public String showHistory(Model model) {
			
		return "history/history-list";
	}
}
