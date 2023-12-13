package study.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import study.common.UserDetail;
import study.entity.UserEntity;
import study.repository.UserRepository;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserDetailService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("UserDetailService：いつ呼ばれているか検証");
		
		UserEntity entity = userRepository.findByName(username);
		
		
		if(entity == null) {
			throw new UsernameNotFoundException("そんなユーザいないよ" + username);
		}
				
		return new UserDetail(entity);
	}

}
