package br.edu.ufcg.genus.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import br.edu.ufcg.genus.utils.ServerConstants;

@Entity
public class Grade {
	
	@Id
	@Column(name="id", nullable=false)
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Size(min = ServerConstants.MIN_SUBJECT_NAME_FIELD, max = ServerConstants.MAX_LOGIN_FIELD,  message="O nome da série deve ter entre 4 and 50 dígitos.")
	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy="grade", fetch=FetchType.EAGER)
	@Column(name="subjects", nullable=false)
	private Set<Subject> subjects;
	
	@ManyToOne
    @JoinColumn(name="institution_id", nullable=false)
	private Institution institution;
	
	public Grade() {
		this.subjects = new HashSet<>();
	}
	
	public Grade(String name, Institution institution) {
		this();
		this.name = name;
		this.institution = institution;
		this.subjects = new HashSet<>();
	}
	
	public boolean addSubject(Subject subject) {
		return this.subjects.add(subject);
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

	public List<Subject> getSubjects() {
		List<Subject> result = new ArrayList<>();
		for (Subject subject : subjects) {
			result.add(subject);
		}
		return result;
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
		Grade other = (Grade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
