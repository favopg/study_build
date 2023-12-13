package study.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import study.entity.IntroduceEntity;
import study.repository.IntroduceRepository;

@RestController
public class IntroduceRestController {

	@Autowired
	IntroduceRepository introduce;
	
	@GetMapping("/hoge")
	@Operation(summary = "自己紹介一覧を全件取得します。")
	public List<IntroduceEntity> hoge(){
		return introduce.findAll();
	}
	
	@GetMapping("/find_id")
	@Operation(summary = "自己紹介一覧を1件取得します。")
	public IntroduceEntity find(){
		return introduce.findById(5).orElseThrow();
	}
	
	@GetMapping("/search")
	@Operation(summary = "非同期通信用メソッド")
	public Model search(Model model){
		model.addAttribute("name", "名前");
		
		return model;
		
	}

}
