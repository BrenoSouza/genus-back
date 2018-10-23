package br.edu.ufcg.genus.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserInstitutionId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8215858083623652615L;

	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "institution_id")
	private Long institutionId;
	
	public UserInstitutionId() {
		
	}
	
	public UserInstitutionId (Long uid, long iid) {
		this.userId = uid;
		this.institutionId = iid;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getInstitutionId() {
		return institutionId;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass()) 
            return false;
 
        UserInstitutionId that = (UserInstitutionId) o;
        return Objects.equals(userId, that.getUserId()) && 
               Objects.equals(institutionId, that.getInstitutionId());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(userId, institutionId);
    }

}
