package demo.persistence.jpa.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class User {

	@Id
	@GeneratedValue
	private long id;
	
	private String uid;
	
	private String name;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public User() {
		super();
	}

	public User(String uid, String name) {
		super();
		this.uid = uid;
		this.name = name;
	}
	
	
}
