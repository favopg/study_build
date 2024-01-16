package study.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

/**
 * ユーザテーブル
 * @author イッシー
 *
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class UserEntity extends CommonEntity{
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "name", unique = true)
	private String name;
	
	@Column(name = "password")
	private String password;
		
	@Column(name = "role")
	private String role;
			
	@OneToOne(mappedBy = "userEntity")
	private IntroduceEntity introduceEntity;

}
