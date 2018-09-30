package br.edu.ufcg.genus.inputs;

import br.edu.ufcg.genus.models.UserRole;

public class CreateEntryCodeInput {
	
	private long institutionId;
	private UserRole role;
	
	public CreateEntryCodeInput() {
		
	}

	public long getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(long institutionId) {
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
		result = prime * result + (int) (institutionId ^ (institutionId >>> 32));
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
		CreateEntryCodeInput other = (CreateEntryCodeInput) obj;
		if (institutionId != other.institutionId)
			return false;
		if (role != other.role)
			return false;
		return true;
	}
	
	

}
