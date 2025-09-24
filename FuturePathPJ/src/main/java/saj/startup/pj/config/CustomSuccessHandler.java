package saj.startup.pj.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		if (authorities.stream().anyMatch(role -> role.getAuthority().equals("ADMIN"))) {

			response.sendRedirect("/admin/dashboard");
		} else if (authorities.stream().anyMatch(role -> role.getAuthority().equals("LENDER"))) { 
																									
			response.sendRedirect("/lender/dashboard");
			
		} else if (authorities.stream().anyMatch(role -> role.getAuthority().equals("BORROWER"))) { 
																									
			response.sendRedirect("/dashboard");
			
		} else {
			response.sendRedirect("/login");
		}

		if (authentication != null) {
			String userId = authentication.getName();
			
			//request.getSession().setAttribute("role", user.getRole());

			//request.getSession().setAttribute("fullname", user.getFirstName() + " " + user.getFamilyName());

			request.getSession().setAttribute("userId", userId);

			//request.getSession().setAttribute("id", user.getId());

		}
		
	}

}
