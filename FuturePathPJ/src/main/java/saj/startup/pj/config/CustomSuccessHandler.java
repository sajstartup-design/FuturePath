package saj.startup.pj.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import saj.startup.pj.model.dao.entity.UserEntity;
import saj.startup.pj.model.logic.UserLogic;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired
	private UserLogic userLogic;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		if (authorities.stream().anyMatch(role -> role.getAuthority().equals("ADMIN"))) {

			response.sendRedirect("/admin/dashboard");
		} else if (authorities.stream().anyMatch(role -> role.getAuthority().equals("USER"))) { 
																									
			response.sendRedirect("/dashboard");
			
		} else {
			response.sendRedirect("/login");
		}
		
		UserEntity user = userLogic.getUserByUsername(authentication.getName());

		if (authentication != null) {
			String userId = authentication.getName();
			
			//request.getSession().setAttribute("role", user.getRole());

			request.getSession().setAttribute("fullname", user.getFirstName() + " " + user.getLastName());

			request.getSession().setAttribute("userId", userId);

			//request.getSession().setAttribute("id", user.getId());

		}
		
	}

}
