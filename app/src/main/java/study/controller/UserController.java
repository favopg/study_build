package study.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import study.data.UserForm;
import study.service.UserService;

@Controller
public class UserController {
		
	@Autowired
	private UserService userService;
	
	/**
	 * ユーザ情報変更画面に遷移する
	 * @param authentication
	 * @param model
	 * @return
	 */
	@GetMapping("/user/user_transition")
	public String userScreen(@ModelAttribute UserForm userForm, Authentication authentication, Model model) {
		model.addAttribute("authentication", authentication);
		
		return "user/user_edit";
	}
	
	/**
	 * ユーザ情報を更新し、ログイン画面にリダイレクトする
	 * @param authentication 認証情報
	 * @param model モデル
	 * @return リダイレクト先(ログイン画面)
	 */
	@PostMapping("/user/edit")
	public String editUser(@ModelAttribute UserForm userForm, Authentication authentication, Model model) {
		model.addAttribute("authentication", authentication);
		
		userService.updateUser(userForm, authentication.getName());

		return "redirect:/login";
	}

	
	@GetMapping("/user_transition")
	public String userTransition(@ModelAttribute UserForm userForm, Authentication authentication, Model model) {
		model.addAttribute("authentication", authentication);
		
		return "user/register_user";
	}
	
	@PostMapping("/user_register")
	public String userRegister(@ModelAttribute UserForm userForm, Authentication authentication, Model model) {
		
		System.out.println("ユーザ名" + userForm.getName());
		System.out.println("パスワード" + userForm.getPassword());

		userService.registerUser(userForm);
		
		model.addAttribute("authentication", authentication);
		
		return "top/custom_login";
	}

}
