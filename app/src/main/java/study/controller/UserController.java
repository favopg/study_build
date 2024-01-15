package study.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import study.data.UserForm;
import study.service.UserService;

@Controller
@SessionAttributes({"authentication","communityName"})
public class UserController {
		
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String login(Model model) {		
		return "login/custom_login";
	}
	
	/**
	 * ユーザ情報変更画面に遷移する
	 * @param authentication
	 * @param model
	 * @return
	 */
	@GetMapping("/user/user_transition")
	public String userScreen(@ModelAttribute UserForm userForm) {		
		return "user/user_edit";
	}
	
	/**
	 * ユーザ情報を更新し、ログイン画面にリダイレクトする
	 * @param userForm 入力されたユーザ情報
	 * @param session セッション情報
	 * @return リダイレクト先(ログイン画面)
	 */
	@PostMapping("/user/edit")
	public String editUser(@ModelAttribute UserForm userForm, HttpSession session) {
		
		Authentication authentication = (Authentication) session.getAttribute("authentication");
		
		userService.updateUser(userForm, authentication.getName());

		return "redirect:/login";
	}

	
	@GetMapping("/user_transition")
	public String userTransition(@ModelAttribute UserForm userForm) {		
		return "user/register_user";
	}
	
	@PostMapping("/user_register")
	public String userRegister(@ModelAttribute UserForm userForm) {
		
		System.out.println("ユーザ名" + userForm.getName());
		System.out.println("パスワード" + userForm.getPassword());

		userService.registerUser(userForm);
				
		return "login/custom_login";
	}

}
