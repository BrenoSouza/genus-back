package br.edu.ufcg.genus.models;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity(name = "UserInstitution")
@Table(name = "user_institution")
public class UserInstitution {
	
	@EmbeddedId
	private UserInstitutionId id;
	
	@Column(name = "role")
	private UserRole role;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("institutionId")
	private Institution institution;
	
	public UserInstitution() {
		
	}
	
	public UserInstitution(User user, Institution institution, UserRole role) {
		this.user = user;
		this.institution = institution;
		this.id = new UserInstitutionId(user.getId(), institution.getId());
		this.role = role;
	}

	public UserInstitutionId getId() {
		return id;
	}

	public void setId(UserInstitutionId id) {
		this.id = id;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		UserInstitution other = (UserInstitution) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (institution == null) {
			if (other.institution != null)
				return false;
		} else if (!institution.equals(other.institution))
			return false;
		if (role != other.role)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	

}
