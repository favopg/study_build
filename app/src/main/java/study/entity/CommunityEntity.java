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

@Entity
@Table(name = "community")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class CommunityEntity extends CommonEntity{
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
			
	@Column(name = "name", length = 50, unique = true)
	private String name;
	
	@Column(name = "secret")
	private String secret;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserEntity userEntity;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "introduce_id", referencedColumnName = "id")
	private IntroduceEntity introduceEntity;

}
