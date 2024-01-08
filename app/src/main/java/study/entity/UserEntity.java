package study.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
public class UserEntity {
	
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
	
	@Column(name = "created_at", updatable = false)
	@CreatedDate
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
	@LastModifiedDate
	private LocalDateTime updatedAt;
	
	@Version
	@Column(name = "version")
	private long version;
	
	@OneToOne(mappedBy = "userEntity")
	private IntroduceEntity introduceEntity;

//	@OneToMany(mappedBy = "userEntity")
//	private List<CommunityEntity> communityEntity;


}
