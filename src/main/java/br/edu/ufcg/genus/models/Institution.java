package br.edu.ufcg.genus.models;

import java.util.List;
import java.util.ArrayList;

import javax.validation.constraints.Size;
import javax.persistence.*;

import br.edu.ufcg.genus.utils.ServerConstants;

@Entity
public class Institution {
	
	@Id
	@Column(name="id", nullable=false)
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Size(min = ServerConstants.MIN_LOGIN_FIELD, max = ServerConstants.MAX_LOGIN_FIELD,  message="The size of the name has to be between 6 and 50")
	@Column(unique = true, nullable = false)
	private String name;

	@Size(min = ServerConstants.MIN_LOGIN_FIELD, max = ServerConstants.MAX_LOGIN_FIELD,  message="The size of the email has to be between 6 and 70")
	@Column(unique = true, nullable = false)
	private String email;

	@Size(min = ServerConstants.MIN_LOGIN_FIELD, max = ServerConstants.MAX_LOGIN_FIELD,  message="The size of the address has to be between 6 and 70")
	@Column(nullable = false)
	private String address;
	
	@Column(nullable = false)
	private String phone;
	
	@ManyToOne
    @JoinColumn(name="owner_id", nullable=false)
    private User owner;

	public Institution() {
		
	}
	
	public Institution(String name, String address, String phone, String email) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}

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

	public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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
