package study.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import study.data.UpdateRequestForm;
import study.entity.CommunityEntity;
import study.entity.IntroduceEntity;
import study.repository.CommunityRepository;
import study.service.IntroduceService;
import study.service.UserService;

@Controller
@SessionAttributes({"communityId", "communityName", "authentication"})
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
	private PasswordEncoder passwordEncoder;
	
	/**
	 * 自己紹介一覧を表示する
	 * @param authentication 認証情報
	 * @param model
	 * @param session
	 * @return 自己紹介一覧
	 */
	@GetMapping("/introduce")
	public String show(Model model, HttpSession session) {
						
		String communityName = (String) session.getAttribute("communityName");
		
		CommunityEntity comunityEntity = communityRepository.findByName(communityName);
		
		List<IntroduceEntity> introduces = comunityEntity.getIntroduceEntity();
		
		model.addAttribute("introduces", introduces);
		model.addAttribute("developEnv", developEnv);
					
		return "introduce/introduce_show";
	}
	
	@PostMapping("/keyword")
	public String searchKeyword(@RequestParam String keyword, Model model, HttpSession session) {
		
		int communityId = (int) session.getAttribute("communityId");				
		List<IntroduceEntity> keywordIntroduces = introduceService.getKeywordIntroduces(keyword, communityId);
		model.addAttribute("introduces", keywordIntroduces);
		model.addAttribute("developEnv", developEnv);

		return "introduce/keyword";
	}
	
	@GetMapping("/keyword")
	public String keywordTransition(Model model) {
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
	public String edit(Model model, @ModelAttribute UpdateRequestForm form, HttpSession session) {
		
		Authentication authentication = (Authentication) session.getAttribute("authentication");
		
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
			HttpSession session
			) throws IOException {

		System.out.println("仕事"+ form.getJob());
		System.out.println("一言メッセージ" + form.getOneMessage());
		System.out.println("得意分野"+ form.getMyField());
		
		Authentication authentication = (Authentication) session.getAttribute("authentication");
		
		if (bindResult.hasErrors()) {
			model.addAttribute("myFields", introduceService.getMyFields());
			
			return "introduce/edit";
		}
		
		introduceService.update(form, authentication.getName());
		
		return "redirect:/introduce";
	}	
	
	@PostMapping("/community/login")
	public String introduce(@RequestParam("communityName") String communityName, 
			@RequestParam("secret") String secret,
			Model model) {
		
		if (communityName == null || communityName.equals("") || secret == null || secret.equals("")) {
			model.addAttribute("communityError", "コミュニティ情報は必須ですよん");

			return "login/login_community";
		}
				
		CommunityEntity community = communityRepository.findByName(communityName);
		
		if(community == null) {
			model.addAttribute("communityError", "コミュニティ情報不整合のため入力内容確認してください");
			return "login/login_community";	
		}
		
		// コミュニティに紐づいてる自己紹介リストを取得
		List<IntroduceEntity> introduces = community.getIntroduceEntity();
				
		if (!passwordEncoder.matches(secret, community.getSecret())) {
			model.addAttribute("communityError", "コミュニティ情報をよく確認してください");
			return "login/login_community";
		}

		model.addAttribute("communityId", community.getId());
		model.addAttribute("communityName", communityName);
		model.addAttribute("communityError", "");
		model.addAttribute("introduces", introduces);
		model.addAttribute("developEnv", developEnv);
		
		return "introduce/introduce_show";
	}
}
