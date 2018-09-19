package br.edu.ufcg.genus.models;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import br.edu.ufcg.genus.utils.ServerConstants;

/*
 * TODO:
 * Add Student, Teacher, ADM and Notification to this class
 */
@Entity
@Table(name="User_Sys")
public class User {
	
	@Id
	@Column(name="id", nullable=false)
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Size(min = ServerConstants.MIN_LOGIN_FIELD, max = ServerConstants.MAX_LOGIN_FIELD,  message="The size of the username has to be between 6 and 50")
	@Column(unique = true, nullable = false)
	private String username;
	
	@Size(min = ServerConstants.MIN_LOGIN_FIELD, max = ServerConstants.MAX_LOGIN_FIELD,  message="The size of the email has to be between 6 and 70")
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@ElementCollection(fetch=FetchType.EAGER)
	List<Role> roles;
	
	@ElementCollection(fetch=FetchType.EAGER)
	private Map<Long, UserRole> institutionRoleMap; 
	
	public User() {
		this.institutionRoleMap = new HashMap<>();
	}
	
	public User(String username, String email, String password) {
		this();
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles = new ArrayList<Role>();
	}
	
	public void addRole(Long institutionID, UserRole role) {
		this.institutionRoleMap.put(institutionID, role);
	}
	
	public UserRole getRole(Long institutionID) {
		return this.institutionRoleMap.get(institutionID);
	}

	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
