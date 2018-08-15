package ma.mt.fo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.JoinTable; 
import javax.persistence.JoinColumn;
@Entity
public class Roles implements Serializable {
     
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idRole ; 
	
	@Column(nullable = false)
	private String intituleRole;  
	
	/*@ManyToMany
	@JoinTable(name = "UsersRoles", joinColumns = @JoinColumn(name = "roles", referencedColumnName="intituleRole"),
	                 inverseJoinColumns = @JoinColumn(name = "users", referencedColumnName="nomUser"))
	private Collection<User> users = new ArrayList<User>() ; */
	
	@ManyToOne
	private TypeCredilog typeCredilog;  
	
	public Roles() {
		
	}  
	

	public Roles(Long idRole, String intituleRole, TypeCredilog typeCredilog) {
		super();
		this.idRole = idRole;
		this.intituleRole = intituleRole;
//		this.users = users;
		this.typeCredilog = typeCredilog;
	}


	public Long getIdRole() {
		return idRole;
	}

	public void setIdRole(Long idRole) {
		this.idRole = idRole;
	} 
	
	public String getIntituleRole() {
		return intituleRole;
	}
	
	public void setIntituleRole(String intituleRole) {
		this.intituleRole = intituleRole;
	}

//	public Collection<User> getUsers() {
//		return users;
//	}
//
//	public void setUsers(Collection<User> users) {
//		this.users = users;
//	}

	public TypeCredilog getTypeCredilog() {
		return typeCredilog;
	}

	public void setTypeCredilog(TypeCredilog typeCredilog) {
		this.typeCredilog = typeCredilog;
	} 
	
	

}
