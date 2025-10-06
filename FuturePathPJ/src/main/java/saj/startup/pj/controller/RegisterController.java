package saj.startup.pj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import saj.startup.pj.common.MessageConstant;
import saj.startup.pj.model.dto.UserDto;
import saj.startup.pj.model.service.UserService;

@Controller
public class RegisterController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/register")
	public String showRegister(@ModelAttribute UserDto webDto, Model model) {

		
		return "register";
	}
	
	@PostMapping("/register")
	public String postRegister(@ModelAttribute UserDto webDto,
			RedirectAttributes ra) {
		
		try {
			
			userService.saveUser(webDto);
			
			ra.addFlashAttribute("isSuccess", true);
			ra.addFlashAttribute("successMsg", MessageConstant.REGISTER_SUCCESS);
		}catch(Exception e) {
		
			e.printStackTrace();
			
			ra.addFlashAttribute("isError", true);
			ra.addFlashAttribute("errorMsg", MessageConstant.SOMETHING_WENT_WRONG);
		}
		
		return "redirect:/login";
	}
}
