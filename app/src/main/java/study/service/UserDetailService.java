package study.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
				
		UserEntity entity = userRepository.findByName(username);
		
		if(entity == null) {
			throw new UsernameNotFoundException("そんなユーザいないよ" + username);
		}
		
		// ユーザ毎の権限付与
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		SimpleGrantedAuthority role = new SimpleGrantedAuthority(entity.getRole());
		roles.add(role);
		
		return new UserDetail(entity, roles);
	}
}
