package br.edu.ufcg.genus.models;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

import javax.persistence.Basic;
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
import javax.persistence.Lob;
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
	
	@Column(nullable = false)
	private long lastInstitutionId;
	
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

	@OneToMany(mappedBy="user", fetch=FetchType.EAGER, orphanRemoval = true)
	private Set<StudentSubject> subjectsStudent = new HashSet<>();

	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	@Column(name="notifications")
	private Set<Notification> notifications;
	
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER, cascade = CascadeType.REMOVE)
	@Column(name="evaluationResults", nullable=false)
	private Set<EvaluationResult> evaluationResults;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name="photo", nullable=true)
	private byte[] photo;
	
	@Column(name="mime_type", nullable=true)
	private String mimeType;

	public User() {
		this.institutions = new HashSet<>();
		this.subjects = new HashSet<>();
		this.notifications = new LinkedHashSet<>();
		this.evaluationResults = new LinkedHashSet<>();
		this.lastInstitutionId = -1;
		this.photo = null;
		this.mimeType = null;
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
		for(StudentSubject studentSubject : this.subjectsStudent) {
			if(studentSubject.getUser().equals(this)) {
				result.add(studentSubject.getSubject());
			}
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
	
	public boolean addEvaluationResult(EvaluationResult evaluationResult) {
		return this.evaluationResults.add(evaluationResult);
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

	public long getLastInstitutionId() {
		return lastInstitutionId;
	}

	public void setLastInstitutionId(long lastInstitutionId) {
		this.lastInstitutionId = lastInstitutionId;
	}

	public Set<EvaluationResult> getEvaluationResults() {
		return evaluationResults;
	}

	public void setEvaluationResults(Set<EvaluationResult> evaluationResults) {
		this.evaluationResults = evaluationResults;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public List<Subject> getSubjects() {
		List<Subject> result = new ArrayList<>();
		for (Subject subject : subjects) {
			result.add(subject);
		}
		return result;
	}

	public void setSubjectsStudent(Set<StudentSubject> subjectsStudent) {
		this.subjectsStudent = subjectsStudent;
	}

	public List<StudentSubject> getSubjectsStudent() {
		List<StudentSubject> result = new ArrayList<>();
		for (StudentSubject subject : subjectsStudent) {
			result.add(subject);
		}
		return result;
	}

	public void addSubject(Subject subject) {
        subjects.add(subject);
	}

	public boolean addSubjectStudent(StudentSubject studentSubject) {
        return subjectsStudent.add(studentSubject);
	}
	
	public boolean removeSubject(Subject subject) {
		subject.getGrade().removeTeacher(this);
		return subjects.remove(subject);
	}

	public boolean checkStudent(Subject subject) {
		boolean result = false;

		for (StudentSubject subjectStudent : subjectsStudent) {
			if (subject.equals(subjectStudent.getSubject())) {
				result = true;
			}
		}
		return result;
	}

	public boolean checkTeacher(Subject subject) {
		boolean result = false;

		for (Subject subjectTeacher : subjects) {
			if (subject.equals(subjectTeacher)) {
				result = true;
			}
		}
		return result;
	}

	public Set<Notification> getNotifications() {
		return this.notifications;
	}

	public void setNotifications(Set<Notification> notifications) {
		this.notifications = notifications;
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
