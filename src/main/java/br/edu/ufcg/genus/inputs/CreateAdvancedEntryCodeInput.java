package br.edu.ufcg.genus.inputs;

import br.edu.ufcg.genus.models.UserRole;

public class CreateAdvancedEntryCodeInput {
	
	private long institutionId;
	private UserRole role;
	private int uses;
	private int days;
	
	public CreateAdvancedEntryCodeInput () {
		
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

	public int getUses() {
		return uses;
	}

	public void setUses(int uses) {
		this.uses = uses;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + days;
		result = prime * result + (int) (institutionId ^ (institutionId >>> 32));
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + uses;
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
		CreateAdvancedEntryCodeInput other = (CreateAdvancedEntryCodeInput) obj;
		if (days != other.days)
			return false;
		if (institutionId != other.institutionId)
			return false;
		if (role != other.role)
			return false;
		if (uses != other.uses)
			return false;
		return true;
	}
	
	

}
