package saj.startup.pj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import saj.startup.pj.common.MessageConstant;
import saj.startup.pj.model.dao.entity.UserEntity;
import saj.startup.pj.model.dto.UserDto;
import saj.startup.pj.model.service.UserService;

@Controller
@RequestMapping("/profile")
public class ProfileController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;

	@GetMapping
	public String showProfilePage(Model model,
			RedirectAttributes ra) {
		
		try {
			UserEntity user = userService.getUserActive();
			
			UserDto inDto = new UserDto();
			
			inDto.setIdPk(user.getIdPk());
			
			UserDto outDto = userService.getUser(inDto);
			outDto.setIdPk(user.getIdPk());
			
			model.addAttribute("userDto", outDto);
			
		}catch(Exception e) {
			
			e.printStackTrace();
			
			ra.addFlashAttribute("isError", true);
			ra.addFlashAttribute("errorMsg", MessageConstant.SOMETHING_WENT_WRONG);
			
			return "redirect:/dashboard";
		}
		
		return "user/profile";
	}
	
	@PostMapping("/save")
	public String editProfilePage(@ModelAttribute UserDto webDto,
			Model model,
			RedirectAttributes ra) {
		
		try {
			
			userService.updateUser(webDto);
			
			ra.addFlashAttribute("isSuccess", true);
			ra.addFlashAttribute("successMsg", MessageConstant.PROFILE_UPDATED);
			
			session.setAttribute("fullname", webDto.getFirstName() + " " + webDto.getLastName());

			session.setAttribute("userId", webDto.getUsername());
			
		}catch(Exception e) {
			e.printStackTrace();
			
			ra.addFlashAttribute("isError", true);
			ra.addFlashAttribute("errorMsg", MessageConstant.SOMETHING_WENT_WRONG);

		}
		
		return "redirect:/profile";
	}
}
