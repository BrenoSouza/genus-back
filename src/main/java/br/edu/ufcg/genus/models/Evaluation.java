package br.edu.ufcg.genus.models;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Evaluation {
	
	@Id
	@Column(name="id", nullable=false)
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "weight")
	private Double weight;
	
	@ManyToOne
    @JoinColumn(name="subject_id", nullable=false)
	private Subject subject;
	
	@OneToMany(mappedBy="evaluation", fetch=FetchType.EAGER, cascade = CascadeType.REMOVE)
	@Column(name="evaluationResults", nullable=false)
	private Set<EvaluationResult> evaluationResults;
	
	public Evaluation() {
		this.evaluationResults = new LinkedHashSet<>();
	}
	
	public Evaluation(String name, Double weight, Subject subject) {
		this();
		this.name = name;
		this.weight = weight;
		this.subject = subject;
	}
	
	public boolean addEvaluationResult(EvaluationResult evaluationResult) {
		return this.evaluationResults.add(evaluationResult);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Evaluation other = (Evaluation) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
