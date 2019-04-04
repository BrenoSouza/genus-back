package br.edu.ufcg.genus.models;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

import javax.validation.constraints.Size;

import javax.persistence.*;

import br.edu.ufcg.genus.utils.ServerConstants;

@Entity
public class Institution {
	
	@Id
	@Column(name="id", nullable=false)
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Size(min = ServerConstants.MIN_LOGIN_FIELD, max = ServerConstants.MAX_LOGIN_FIELD,  message="O tamanho do nome deve ter entre 6 e 50 dígitos.")
	@Column(unique = true, nullable = false)
	private String name;

	@Size(min = ServerConstants.MIN_LOGIN_FIELD, max = ServerConstants.MAX_LOGIN_FIELD,  message="O tamanho do email deve ter entre 6 e 50 dígitos.")
	@Column(unique = true, nullable = false)
	private String email;

	@Size(min = ServerConstants.MIN_LOGIN_FIELD, max = ServerConstants.MAX_LOGIN_FIELD,  message="O tamanho do endereço da instituição deve ter entre 6 e 50 dígitos.")
	@Column(nullable = false)
	private String address;
	
	@Column(nullable = false)
	private String phone;
	
	@OneToMany(mappedBy="institution", fetch=FetchType.EAGER)
	@Column(name="grades", nullable=false)
	private Set<Grade> grades;
	
	@OneToMany(mappedBy="institution", fetch=FetchType.EAGER, orphanRemoval = true)
	private Set<UserInstitution> users;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name="photo", nullable=true)
	private byte[] photo;
	
	@Column(name="mime_type", nullable=true)
	private String mimeType;

	public Institution() {
		this.grades = new HashSet<>();
		this.users = new HashSet<>();
		this.photo = null;
		this.mimeType = null;
	}
	
	public Institution(String name, String address, String phone, String email) {
		this();
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.grades = new HashSet<>();
	}
	
	public boolean addGrade(Grade grade) {
		return this.grades.add(grade);
	}
	
	public UserInstitution addUser(User user, UserRole role) {
		UserInstitution userInstitution = new UserInstitution(user, this, role);
		this.users.add(userInstitution);
		user.getInstitutions().add(userInstitution);
		return userInstitution;
	}
	
	//Getters and Setters

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Grade> getGrades() {
		List<Grade> result = new ArrayList<>();
		for (Grade grade : grades) {
			result.add(grade);
		}
		return result;
	}

	public void setGrades(Set<Grade> grades) {
		this.grades = grades;
	}

	public Set<UserInstitution> getUsers() {
		return users;
	}

	public void setUsers(Set<UserInstitution> users) {
		this.users = users;
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
		Institution other = (Institution) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
