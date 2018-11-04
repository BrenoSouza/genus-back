package br.edu.ufcg.genus.models;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "StudentSubject")
@Table(name = "student_subject")
public class StudentSubject {
	
	@EmbeddedId
	private StudentSubjectId id;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @MapsId("subjectId")
	private Subject subject;
	
	@Column(name = "avarage")
	private Double avarage;
	
	@OneToMany(mappedBy="studentSubject", fetch=FetchType.EAGER)
	@Column(name="evaluations", nullable=false)
	private Set<Evaluation> evaluations;
	
	public StudentSubject() {
		this.avarage = new Double(0.0);
		this.evaluations = new LinkedHashSet<>();
	}
	
	public StudentSubject(User user, Subject subject) {
		this();
		this.user = user;
		this.subject = subject;
		this.id = new StudentSubjectId(user.getId(), subject.getId());
	}
	
	public boolean addEvaluation(Evaluation evaluation) {
		boolean added = this.evaluations.add(evaluation);
		if (added) {
			this.avarage += evaluation.getResult() * evaluation.getWeight(); 
		}
		return added;
	}

	public StudentSubjectId getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Double getAvarage() {
		return avarage;
	}

	public void setAvarage(Double avarage) {
		this.avarage = avarage;
	}

	public Set<Evaluation> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(Set<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		StudentSubject other = (StudentSubject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
