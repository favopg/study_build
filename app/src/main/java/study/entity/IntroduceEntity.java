package study.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

/**
 * 自己紹介エンティティクラス
 * @author イッシー
 *
 */
@Entity
@Table(name = "introduce")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class IntroduceEntity extends CommonEntity{
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
			
	@Column(name = "job", length = 50)
	private String job;
	
	@Column(name = "my_field")	
	private String myField;
	
	@Column(name = "one_message")
	private String oneMessage;
	
	@Column(name = "language")
	private String language;
	
	@Column(name= "icon")
	private String icon;
		
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = true, updatable = false)
	private UserEntity userEntity;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "community_id", referencedColumnName = "id", insertable = true, updatable = false)	
	private CommunityEntity communityEntity;
		
}
