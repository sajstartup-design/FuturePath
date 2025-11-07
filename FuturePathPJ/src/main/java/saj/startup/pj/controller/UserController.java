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
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/admin/user")
	public String showUsers(Model model) {
		
		try {
			UserDto outDto = userService.getUserOverview();
			
			model.addAttribute("userDto", outDto);
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		model.addAttribute("page", "user");
		
		return "user/user-view";
	}
	
	@GetMapping("/admin/user/add")
	public String showAddUser(Model model, @ModelAttribute UserDto webDto) {
		
		model.addAttribute("page", "user");
		
		return "user/user-add";
	}
	
	@PostMapping("/admin/user/add")
	public String postAddUser(@ModelAttribute UserDto webDto, 
			RedirectAttributes ra) {
		
		try {
			
			userService.saveUser(webDto);
			
			ra.addFlashAttribute("isSuccess", true);
			ra.addFlashAttribute("successMsg", MessageConstant.USER_ADDED);
			
		}catch(Exception e) {
			e.printStackTrace();
			
			ra.addFlashAttribute("isError", true);
			ra.addFlashAttribute("errorMsg", MessageConstant.SOMETHING_WENT_WRONG);

		}
		
		return "redirect:/admin/user";
	}
	
	@GetMapping("/admin/user/edit")
	public String showEditUser(Model model, 
			@ModelAttribute UserDto webDto,
			RedirectAttributes ra) {
		
		try {
			
			UserDto outDto = userService.getUser(webDto);
			outDto.setIdPk(webDto.getIdPk());
			
			model.addAttribute("userDto", outDto);
			
		}catch(Exception e) {
			
			e.printStackTrace();
			
			ra.addFlashAttribute("isError", true);
			ra.addFlashAttribute("errorMsg", MessageConstant.SOMETHING_WENT_WRONG);
			
			return "redirect:/admin/user";
		}
		
		model.addAttribute("page", "user");
		
		return "user/user-edit";
	}
	
	@PostMapping("/admin/user/edit")
	public String postEditUser(@ModelAttribute UserDto webDto, 
			RedirectAttributes ra) {
		
		try {
			
			userService.updateUser(webDto);
			
			ra.addFlashAttribute("isSuccess", true);
			ra.addFlashAttribute("successMsg", MessageConstant.USER_EDITED);
			
		}catch(Exception e) {
			e.printStackTrace();
			
			ra.addFlashAttribute("isError", true);
			ra.addFlashAttribute("errorMsg", MessageConstant.SOMETHING_WENT_WRONG);

		}
		
		return "redirect:/admin/user";
	}
	
	@PostMapping("/admin/user/delete")
	public String deleteUser(@ModelAttribute UserDto webDto,
			RedirectAttributes ra) {
		
		try {
			
			userService.deleteUser(webDto);
			
			ra.addFlashAttribute("isSuccess", true);
			ra.addFlashAttribute("successMsg", MessageConstant.USER_DELETED);
		} catch(Exception e) {
			e.printStackTrace();
			
			ra.addFlashAttribute("isError", true);
			ra.addFlashAttribute("errorMsg", MessageConstant.SOMETHING_WENT_WRONG);
		}
		
		return "redirect:/admin/user";
	}
}
