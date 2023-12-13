package study.common;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CustomAuditorAware implements AuditorAware<String>{

	@Override
	public Optional<String> getCurrentAuditor() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if("新規ユーザ".equals(authentication.getName())) {
			return null;
		}
				
		return Optional.of(((UserDetail) authentication.getPrincipal()).getUsername());
	}

}
