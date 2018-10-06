package br.edu.ufcg.genus.inputs;


public class RemoveUserFromInstitutionInput {
	
	private Long institutionId;
	
	private Long toBeRemovedId;
	
	private String institutionName;
	
	private String password;
	
	public RemoveUserFromInstitutionInput () {
		
	}
	
	public RemoveUserFromInstitutionInput (Long institutionId, Long toBeRemovedId, String institutionName, String password) {
		this.institutionId = institutionId;
		this.toBeRemovedId = toBeRemovedId;
		this.institutionName = institutionName;
		this.password = password;
	}

	public Long getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}

	public Long getToBeRemovedId() {
		return toBeRemovedId;
	}

	public void setToBeRemovedId(Long toBeRemovedId) {
		this.toBeRemovedId = toBeRemovedId;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
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
		result = prime * result + ((institutionId == null) ? 0 : institutionId.hashCode());
		result = prime * result + ((institutionName == null) ? 0 : institutionName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((toBeRemovedId == null) ? 0 : toBeRemovedId.hashCode());
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
		RemoveUserFromInstitutionInput other = (RemoveUserFromInstitutionInput) obj;
		if (institutionId == null) {
			if (other.institutionId != null)
				return false;
		} else if (!institutionId.equals(other.institutionId))
			return false;
		if (institutionName == null) {
			if (other.institutionName != null)
				return false;
		} else if (!institutionName.equals(other.institutionName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (toBeRemovedId == null) {
			if (other.toBeRemovedId != null)
				return false;
		} else if (!toBeRemovedId.equals(other.toBeRemovedId))
			return false;
		return true;
	}
	
	

}
