package study.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import study.data.UserForm;
import study.entity.IntroduceEntity;
import study.entity.UserEntity;
import study.repository.IntroduceRepository;
import study.repository.UserRepository;

/**
 * ユーザについてのサービスクラス
 * @author 
 *
 */
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private IntroduceRepository introduce;

	/**
	 * ユーザ情報登録
	 * @param userForm リクエストのユーザ情報
	 */
	@Transactional
	public void registerUser(UserForm userForm) {
		UserEntity entity = new UserEntity();
		entity.setName(userForm.getName());
		entity.setPassword(passwordEncoder.encode(userForm.getPassword()));
		
		IntroduceEntity introduceEntity = new IntroduceEntity();
		introduceEntity.setJob("サンプル仕事です");
		introduceEntity.setMyField("サンプル得意分野です");
		introduceEntity.setIcon("");
		introduceEntity.setOneMessage("サンプルメッセージです");
		introduceEntity.setLanguage("サンプル言語です");
		
		introduceEntity.setUserEntity(entity);
		entity.setIntroduceEntity(introduceEntity);

		userRepository.save(entity);
		introduce.save(introduceEntity);
		
	}
	
	/**
	 * ユーザ情報更新
	 * @param userForm リクエストのユーザ情報
	 * @param username 認証済のユーザ名
	 */
	@Transactional
	public void updateUser(UserForm userForm, String username) {
		UserEntity entity = userRepository.findByName(username);
				
		if (entity == null) {
			throw new RuntimeException("ユーザデータないよ");
		}

		entity.setName(userForm.getName());
		entity.setPassword(passwordEncoder.encode(userForm.getPassword()));
	
	}
	
	/**
	 * ユーザ情報取得
	 * @param username 認証済ユーザー名
	 * @return 取得したユーザ情報
	 */
	public UserEntity getUser(String username) {
		return userRepository.findByName(username);
		
	}
	
}
