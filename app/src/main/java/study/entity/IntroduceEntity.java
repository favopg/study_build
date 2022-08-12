package study.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 自己紹介エンティティクラス
 * @author イッシー
 *
 */
@Entity
@Table(name = "introduce")
public class IntroduceEntity {
	
	@Id
	@Column(name = "id")
	private int id = 0;
	@Column(name = "name")
	private String name = null;
	@Column(name = "job")
	private String job = null;
	@Column(name = "language")
	private String language = null;
	@Column(name = "one_thing")
	private String oneThing = null;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getOneThing() {
		return oneThing;
	}
	public void setOneThing(String oneThing) {
		this.oneThing = oneThing;
	}

}
