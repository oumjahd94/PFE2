package ma.mt.fo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import net.minidev.json.annotate.JsonIgnore;

@Entity
public class User implements Serializable {
	
	  @Id	
	  @GeneratedValue(strategy=GenerationType.AUTO)
	  private Long idUser ;  
	  
	  @Column(nullable = false)
	  private String nomUser ;  
	  
	  private String emailUser ;

	 
      @Column(unique= true)
	  private String loginUser ;   
	  
	  private String pwdUser ; 
	  
	  @ManyToMany(fetch = FetchType.EAGER)
	  private Collection<Roles> roles = new ArrayList<Roles>() ;   
	         
	  public Collection<Roles> getRoles() {
		  return roles;
	  }

	  public void setRoles(Collection<Roles> roles) {
	 	 this.roles = roles;
	  }

	public User(String nomUser, String emailUser, String loginUser, String pwdUser) {
		super();
		this.nomUser = nomUser;
		this.emailUser = emailUser;
		this.loginUser = loginUser; 
		this.pwdUser = pwdUser;
	}
     
	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}


	
	public String getNomUser() {
		return nomUser;
	}
	
	
	public void setNomUser(String nomUser) {
		this.nomUser = nomUser;
	}
	
	
	public String getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}

	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	@com.fasterxml.jackson.annotation.JsonIgnore
	public String getPwdUser() {
		return pwdUser;
	}



	@JsonSetter
	public void setPwdUser(String pwdUser) {
		this.pwdUser = pwdUser;
	}


	 	
	public User() {
	}

}
