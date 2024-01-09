package study.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import study.entity.CommunityEntity;
import study.repository.UserRepository;
import study.service.CommunityService;

@Controller
public class CommunityController {
	
	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/community")
	public String role(Model model) {
		
		System.out.println("ADMINユーザしかアクセスできないよ");
		
		List<CommunityEntity> communities = communityService.getCommunityList();
		
		System.out.println("コミュニティ作成件数" + communities.size());
		//System.out.println("コミュニティ作成フラグ" + communities.get(1).getUserEntity().getIsCommunity());
				
		model.addAttribute("communities", communities);
				
		return "community/community_show";
		
	}
		
	@GetMapping("/login/community")
	public String init(Authentication authentication, Model model) {
		model.addAttribute("authentication", authentication);		
		return "login/login_community";
	}	
}
