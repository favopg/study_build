package study.common;

import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import study.entity.UserEntity;

/**
 * 認証情報クラス
 * @author favor
 *
 */
public class UserDetail extends User {

	
	public UserDetail(UserEntity userEntity) {
		super(
				userEntity.getName(), 
				userEntity.getPassword(), 
				true, 
				true, 
				true, 
				true, 
				new ArrayList<GrantedAuthority>()
		);
	}

}
