package study.common;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import study.entity.UserEntity;

/**
 * 認証情報クラス
 * @author favor
 *
 */
public class UserDetail extends User {

	
	public UserDetail(UserEntity userEntity, List<GrantedAuthority> roles) {
		super(
				userEntity.getName(), 
				userEntity.getPassword(), 
				true, 
				true, 
				true, 
				true, 
				roles
		);
	}

}
