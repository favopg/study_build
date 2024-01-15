package study.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import study.entity.CommunityEntity;
import study.service.CommunityService;

@Controller
@SessionAttributes({"communityName", "authentication"})
public class CommunityController {
	
	@Autowired
	private CommunityService communityService;

	/**
	 * たぶん使用されていない
	 * 承認とか作ろうとしたけどそうじゃないってなったはず
	 * @param model
	 * @return
	 */
	@GetMapping("/community")
	public String role(Model model) {
		
		System.out.println("ADMINユーザしかアクセスできないよ");
		
		List<CommunityEntity> communities = communityService.getCommunityList();
		
		model.addAttribute("communities", communities);
				
		return "community/community_show";
		
	}
		
	@GetMapping("/login/community")
	public String init(Authentication authentication, Model model) {
		model.addAttribute("communityName", "");
		model.addAttribute("authentication", authentication);		
		return "login/login_community";
	}	
}
