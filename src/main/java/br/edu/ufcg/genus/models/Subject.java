package br.edu.ufcg.genus.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="subjects")
public class Subject {
	
	@Id
	@Column(name="id", nullable=false)
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@ManyToOne
    @JoinColumn(name="grade_id", nullable=false)
	private Grade grade;

	@ManyToMany(fetch = FetchType.EAGER,
	cascade = {
		CascadeType.PERSIST,
		CascadeType.MERGE
	},
	mappedBy = "subjects")
	private Set<User> teachers;
	
	
	@OneToMany(mappedBy="subject", fetch=FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.REMOVE)
	private Set<StudentSubject> students;


	@OneToMany(mappedBy="subject", fetch=FetchType.EAGER, cascade = CascadeType.REMOVE)
	@Column(name="forum", nullable=false)
	private Set<Discussion> forum;
	
	
	public Subject() {
		this.students = new HashSet<>();
		this.teachers = new HashSet<>();
		this.forum = new LinkedHashSet<>();
	}
	
	public Subject(Grade owner, String name) {
		this();
		this.grade = owner;
		this.name = name;
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

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public void setTeachers(Set<User> teachers) {
		this.teachers = teachers;
	}

	public void setStudents(Set<StudentSubject> students) {
		this.students = students;
	}

	public boolean addTeacher(User teacher) {
		return this.teachers.add(teacher);
	}
	
	public boolean addStudent(StudentSubject student) {
		return this.students.add(student);
	}
	
	public boolean addDiscussion(Discussion discussion) {
		return this.forum.add(discussion);
	}

	public Set<Discussion> getForum() {
		return forum;
	}

	public void setForum(Set<Discussion> forum) {
		this.forum = forum;
	}

	public List<User> getTeachers() {
		List<User> result = new ArrayList<>();
		for (User teacher : teachers) {
			result.add(teacher);
		}
		return result;
	}

	public List<StudentSubject> getStudents() {
		List<StudentSubject> result = new ArrayList<>();
		for (StudentSubject studentSubject : students) {
			result.add(studentSubject);
		}
		return result;
	}
	
	public List<User> findStudents() {
		List<User> result = new ArrayList<>();
		for (StudentSubject studentSubject : students) {
			if (studentSubject.getSubject().equals(this)) {
				result.add(studentSubject.getUser());
			}
		}
		return result;
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
		Subject other = (Subject) obj;
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

	public boolean removeTeacher(User teacher) {
		return teachers.remove(teacher);
		
	}
}
