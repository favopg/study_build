package study.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
public class CommonEntity {

	@Column(name = "create_user", updatable = false)
	@CreatedBy
	private String createUser;
	
	@Column(name = "created_at", updatable = false)
	@CreatedDate
	private LocalDateTime createdAt;
	
	@Column(name = "update_user")
	@LastModifiedBy
	private String updateUser;
	
	@Column(name = "updated_at")
	@LastModifiedDate
	private LocalDateTime updatedAt;
	
	@Version
	@Column(name = "version")
	private long version;

}
