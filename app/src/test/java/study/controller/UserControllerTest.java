package study.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import study.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	UserService userServive;
	
	@Test
	@DisplayName("ログイン画面にたどりつける")
	public void loginTest() throws Exception {
		mockMvc.perform(get("/login"))
			.andExpect(status().isOk())
			.andExpect(view().name("login/custom_login"))
			.andReturn();
	}

}
