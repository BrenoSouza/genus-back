package br.edu.ufcg.genus.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
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

	@OneToMany(mappedBy="grade", fetch=FetchType.EAGER, cascade = CascadeType.REMOVE)
	@Column(name="subjects", nullable=false)
	private Set<Subject> subjects;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@JoinTable(name="student_grade", joinColumns=@JoinColumn(name="id"))
	@MapKeyColumn (name="student")
	@Column(name="ocurrences_student")
	private Map<User, Integer> students;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@JoinTable(name="teacher_grade", joinColumns=@JoinColumn(name="id"))
	@MapKeyColumn (name="teacher")
	@Column(name="ocurrences_teacher")
	private Map<User, Integer> teachers;
	
	@ManyToOne
    @JoinColumn(name="institution_id", nullable=false)
	private Institution institution;
	
	public Grade() {
		this.subjects = new HashSet<>();
		this.students = new HashMap<>();
		this.teachers = new HashMap<>();
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
	
	public void addStudent(User student) {
		addUser(student, students);
	}
	
	public void addTeacher(User teacher) {
		addUser(teacher, teachers);
	}
	
	public void removeStudent(User student) {
		removeUser(student, students);
	}
	
	public void removeTeacher(User teacher) {
		removeUser(teacher, teachers);
	}
	
	private void addUser(User user, Map<User, Integer> map) {
		Integer qnt = map.get(user);
		if (qnt == null) {
			map.put(user, 1);
		} else {
			map.put(user, qnt + 1);
		}
	}
	
	private void removeUser(User user, Map<User, Integer> map) {
		Integer qnt = map.get(user);
		if (qnt.equals(1)) {
			map.remove(user);
		} else {
			map.put(user, qnt - 1);
		}
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

	public Map<User, Integer> getStudents() {
		return students;
	}

	public Map<User, Integer> getTeachers() {
		return teachers;
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
