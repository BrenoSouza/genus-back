package br.edu.ufcg.genus.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StudentSubjectId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 404762994235775356L;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "subject_id")
	private Long subjectId;
	
	public StudentSubjectId() {
		
	}
	
	public StudentSubjectId(Long uid, Long sid) {
		this.userId = uid;
		this.subjectId = sid;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getSubjectId() {
		return subjectId;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass()) 
            return false;
 
        StudentSubjectId that = (StudentSubjectId) o;
        return Objects.equals(userId, that.getUserId()) && 
               Objects.equals(subjectId, that.getSubjectId());
    }
	
	@Override
    public int hashCode() {
        return Objects.hash(userId, subjectId);
    }

}
