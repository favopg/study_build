package study.common;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CustomAuditorAware implements AuditorAware<String>{
	
	@Autowired
	HttpSession session;
	
	@Override
	public Optional<String> getCurrentAuditor() {
				
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if("新規ユーザ".equals(authentication.getName())) {
			return Optional.of((String)session.getAttribute("userName"));
		}
				
		return Optional.of(((UserDetail) authentication.getPrincipal()).getUsername());
	}

}
