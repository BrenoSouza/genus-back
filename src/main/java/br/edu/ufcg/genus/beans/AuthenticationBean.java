package br.edu.ufcg.genus.beans;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AuthenticationBean {
	
	//@Pattern( regexp = "^[a-zA-Z0-9._]", message = "Invalid username")
	@Size(min = 6, max = 50,  message = "The size of the username has to be between 6 and 50")
	private String username;
	
	@Size(min = 6, max = 50,  message="The size of the password has to be between 6 and 50")
	private String password;
	
	public AuthenticationBean() {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		AuthenticationBean other = (AuthenticationBean) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	

}
