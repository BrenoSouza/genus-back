package br.edu.ufcg.genus.models;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import br.edu.ufcg.genus.utils.ServerConstants;

@Entity
@Table(name="User_Sys")
public class User {
	
	@Id
	@Column(name="id", nullable=false)
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Size(min = ServerConstants.MIN_LOGIN_FIELD, max = ServerConstants.MAX_LOGIN_FIELD,  message="O tamanho do username deve ter entre 6 e 50 dígitos.")
	@Column(unique = true, nullable = false)
	private String username;
	
	@Size(min = ServerConstants.MIN_LOGIN_FIELD, max = ServerConstants.MAX_LOGIN_FIELD,  message="O tamanho do email deve ter entre 6 e 50 dígitos.")
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@ElementCollection(fetch=FetchType.EAGER)
	List<Role> roles;
	
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER, orphanRemoval = true)
	private Set<UserInstitution> institutions;
	
	@ManyToMany(fetch=FetchType.EAGER,
	cascade = { 
        CascadeType.PERSIST
    })
    @JoinTable(name = "teacher_subject",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName="id")
    )
	private Set<Subject> subjects = new HashSet<>();;

	@ManyToMany(fetch=FetchType.EAGER,
	cascade = { 
        CascadeType.PERSIST
    })
    @JoinTable(name = "student_subject",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName="id")
    )
	private Set<Subject> subjectsStudent = new HashSet<>();;

	public User() {
		this.institutions = new HashSet<>();
		this.subjects = new HashSet<>();
	}
	
	public User(String username, String email, String password) {
		this();
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles = new ArrayList<Role>();
	}
	
	public List<Institution> findInstitutions() {
		List<Institution> result = new ArrayList<>();
		for (UserInstitution userInstitution : institutions) {
			if (userInstitution.getUser().equals(this)) {
				result.add(userInstitution.getInstitution());
			}
		}
		return result;
	}

	public List<Subject> findSubjects() {
		List<Subject> result = new ArrayList<>();
		for (Subject subject : subjects) {
			result.add(subject);
		}
		return result;
	}

	public List<Subject> findSubjectsStudent() {
		List<Subject> result = new ArrayList<>();
		for (Subject subject : subjectsStudent) {
			result.add(subject);
		}
		return result;
	}

	public UserRole getRole(Long institutionID) {
		UserRole result = null;
		for (UserInstitution userInstitution : institutions) {
			if (userInstitution.getInstitution().getId() == institutionID && userInstitution.getUser().equals(this)) {
				result = userInstitution.getRole();
				break;
			}
		}
		return result;
	}

	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Set<UserInstitution> getInstitutions() {
		return institutions;
	}

	public void setInstitutions(Set<UserInstitution> institutions) {
		this.institutions = institutions;
	}

	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}

	public List<Subject> getSubjects() {
		List<Subject> result = new ArrayList<>();
		for (Subject subject : subjects) {
			result.add(subject);
		}
		return result;
	}

	public void setSubjectsStudent(Set<Subject> subjectsStudent) {
		this.subjectsStudent = subjectsStudent;
	}

	public List<Subject> getSubjectsStudent() {
		List<Subject> result = new ArrayList<>();
		for (Subject subject : subjectsStudent) {
			result.add(subject);
		}
		return result;
	}

	public void addSubject(Subject subject) {
        subjects.add(subject);
	}

	public void addSubjectStudent(Subject subject) {
        subjectsStudent.add(subject);
	}
	
	public boolean removeSubject(Subject subject) {
		return subjects.remove(subject);
	}

	public boolean removeSubjectStudent(Subject subject) {
		return subjectsStudent.remove(subject);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
