package study.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import study.data.UpdateRequestForm;
import study.entity.CommunityEntity;
import study.entity.IntroduceEntity;
import study.repository.CommunityRepository;
import study.service.IntroduceService;
import study.service.UserService;

@Controller
public class IntroduceController {
	
	@Autowired
	private IntroduceService introduceService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommunityRepository communityRepository;
		
	@Value("${develop.env}")
	private String developEnv;

	@Autowired
	ResourceLoader resourceLoader;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

		
	@GetMapping("/introduce")
	public String init(@RequestParam("communityName") String communityName, Authentication authentication, Model model) {
		
		
		List<CommunityEntity> communities = communityRepository.findAll();
		List<IntroduceEntity> introduces = new ArrayList<IntroduceEntity>();

		for(int i= 0; i<communities.size(); i++) {
			
			if (communities.get(i).getName().equals(communityName)) {
				//introduces.add(communities.get(i).getIntroduceEntity());
			}
		}
		
		//List<IntroduceEntity> introduces = introduceService.getIntroduces();
		model.addAttribute("introduces", introduces);
		model.addAttribute("authentication", authentication);
		model.addAttribute("developEnv", developEnv);
					
		return "introduce/introduce_show";
	}
	
	@PostMapping("/keyword")
	public String searchKeyword(@RequestParam String keyword, Authentication authentication, Model model) {
				
		List<IntroduceEntity> keywordIntroduces = introduceService.getKeywordIntroduces(keyword);
		model.addAttribute("introduces", keywordIntroduces);
		model.addAttribute("authentication", authentication);

		return "introduce/keyword";
	}
	
	@GetMapping("/keyword")
	public String keywordTransition(Authentication authentication, Model model) {
		
		model.addAttribute("authentication", authentication);
		model.addAttribute("introduces", new ArrayList<IntroduceEntity>());
		
		return "introduce/keyword";
	}
			
	/**
	 * 自己紹介変更画面に遷移する
	 * @param model
	 * @param id
	 * @return 
	 */
	@GetMapping("/introduce/edit/screen")
	public String edit(Model model, @ModelAttribute UpdateRequestForm form, Authentication authentication) {
		
		IntroduceEntity introduce = userService.getUser(authentication.getName()).getIntroduceEntity();
		
		BeanUtils.copyProperties(introduce, form);
		
		model.addAttribute("introduce", introduce);
		model.addAttribute("myFields", introduceService.getMyFields());
		model.addAttribute("authentication", authentication);
		
		return "introduce/edit";
	}
	
	/**
	 * 自己紹介更新を行う
	 * @param form
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@PostMapping("/introduce/update")
	public String update(@Valid @ModelAttribute  UpdateRequestForm form,
			BindingResult bindResult,  
			Model model,
			Authentication authentication
			) throws IOException {

		System.out.println("仕事"+ form.getJob());
		System.out.println("一言メッセージ" + form.getOneMessage());
		System.out.println("得意分野"+ form.getMyField());
		
		model.addAttribute("authentication", authentication);    
		if (bindResult.hasErrors()) {
			model.addAttribute("myFields", introduceService.getMyFields());
			
			return "introduce/edit";
		}
		
		introduceService.update(form, authentication.getName());
		
		return "redirect:/introduce";
	}	
	
	@PostMapping("/community/login")
	public String introduce(@RequestParam("communityName") String communityName, @RequestParam("secret") String secret, Authentication authentication, Model model) {
		if (communityName == null || secret == null) {
			return "login/login_community";
		}
		
		CommunityEntity community = communityRepository.findByName(communityName);
		List<IntroduceEntity> introduces = community.getIntroduceEntity();
		
		System.out.println("合言葉認証" + passwordEncoder.matches(secret, community.getSecret()));
				
		model.addAttribute("introduces", introduces);
		model.addAttribute("authentication", authentication);
		model.addAttribute("developEnv", developEnv);

		return "introduce/introduce_show";
	}

}
