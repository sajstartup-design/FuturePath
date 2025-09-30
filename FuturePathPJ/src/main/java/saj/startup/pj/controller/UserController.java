package saj.startup.pj.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import saj.startup.pj.model.dto.UserDto;
import saj.startup.pj.model.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/admin/user")
	public String showUsers(@ModelAttribute UserDto webDto) {
		
		try {
			UserDto outDto = userService.getUserOverview();
			
			webDto.setOverview(outDto.getOverview());
		}catch(Exception e) {
			
			e.printStackTrace();

		}
		
		return "user/user-view";
	}
	
	@GetMapping("/admin/user/add")
	public String showAddUser(@ModelAttribute UserDto webDto) {
		
		return "user/user-add";
	}
	
	@PostMapping("/admin/user/add")
	public String postShowAddUser(@ModelAttribute UserDto webDto, 
			RedirectAttributes ra) {
		
		try {
			
			userService.saveUser(webDto);
			
			return "redirect:/admin/user";
			
		}catch(Exception e) {
			e.printStackTrace();
			
			return "redirect:/admin/user";
		}
	}
}
