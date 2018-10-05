package br.edu.ufcg.genus.inputs;

import br.edu.ufcg.genus.models.UserRole;

public class GetUsersFromInstitutionByRoleInput {
	
	private Long institutionId;
	private UserRole role;
	
	public GetUsersFromInstitutionByRoleInput () {
		
	}
	
	public GetUsersFromInstitutionByRoleInput (Long iid, UserRole role) {
		this.institutionId = iid;
		this.role = role;
	}

	public Long getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((institutionId == null) ? 0 : institutionId.hashCode());
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
		GetUsersFromInstitutionByRoleInput other = (GetUsersFromInstitutionByRoleInput) obj;
		if (institutionId == null) {
			if (other.institutionId != null)
				return false;
		} else if (!institutionId.equals(other.institutionId))
			return false;
		if (role != other.role)
			return false;
		return true;
	}
	
	

}
