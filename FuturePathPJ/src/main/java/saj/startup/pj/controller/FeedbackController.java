package saj.startup.pj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import saj.startup.pj.common.MessageConstant;
import saj.startup.pj.model.dto.FeedbackDto;
import saj.startup.pj.model.service.FeedbackService;

@Controller
public class FeedbackController {
	
	@Autowired
	private FeedbackService feedbackService;

	@PostMapping("/feedback")
	public String saveFeedback(@ModelAttribute FeedbackDto webDto,
			RedirectAttributes ra) {
		
		System.out.println(webDto.getRatings());
		System.out.println(webDto.getFeedback());
		
		try {
			
			feedbackService.saveFeedback(webDto);
			
			ra.addFlashAttribute("isSuccess", true);
			ra.addFlashAttribute("successMsg", MessageConstant.FEEDBACK_SUBMITTED);
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
			ra.addFlashAttribute("isError", true);
			ra.addFlashAttribute("errorMsg", MessageConstant.SOMETHING_WENT_WRONG);
		}
		
		
		return "redirect:/assessment/result/view?resultIdPk=" + webDto.getResultIdPk();
	}
}
