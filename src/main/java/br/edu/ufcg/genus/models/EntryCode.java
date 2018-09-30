package br.edu.ufcg.genus.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import br.edu.ufcg.genus.utils.ServerConstants;

@Entity
public class EntryCode {
	
	@Id
	private String code;
	
	@Column(nullable = false)
	private Date expirationDate;
	
	@Column(nullable = false)
	private UserRole role;
	
	@Column(nullable = false)
	private long institutionId;
	
	public EntryCode() {
		Date now = new Date();
		this.expirationDate = new Date(now.getTime() + ServerConstants.DAY_MILLISECONDS);
	}
	
	public EntryCode (String code, UserRole role, long institutionId) {
		this();
		this.code = code;
		this.role = role;
		this.institutionId = institutionId;
	}

	public String getCode() {
		return code;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public UserRole getRole() {
		return role;
	}

	public long getInstitutionId() {
		return institutionId;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public void setInstitutionId(long institutionId) {
		this.institutionId = institutionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		EntryCode other = (EntryCode) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}	
}
