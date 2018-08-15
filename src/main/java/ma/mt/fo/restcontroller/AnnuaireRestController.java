package ma.mt.fo.restcontroller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ma.mt.fo.dao.AnnuaireJpaRepository;
import ma.mt.fo.entity.User;
import ma.mt.fo.service.interfaces.IAnnuaireService;

@Transactional
@RestController
@RequestMapping(value = "/api/annuaires/")
public class AnnuaireRestController {	
	@Autowired
	private IAnnuaireService annuaireService ;  
	
    @Autowired
    private AnnuaireJpaRepository annuaireJpaRepository ; 
	
	public AnnuaireRestController() { 
		
	}   
	
	//web service pour récupérer la liste des annuaires  
	@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<User> listUsers() {
		return annuaireService.listeUtilisateurs() ; 
	}
  
	@CrossOrigin
	@RequestMapping(value = "/deleteUser/{idUser}", method = RequestMethod.DELETE)
	public boolean deleteUser(@PathVariable(name = "idUser") Long idUser) {
		return annuaireService.deleteUser(idUser);
	}  
	
	
	@CrossOrigin
	@RequestMapping(value = "/addUser", method = { RequestMethod.POST, RequestMethod.OPTIONS })
	public boolean addUser(
			@RequestParam("nomUser") String nomUser,
			@RequestParam("emailUser") String emailUser,
			@RequestParam("loginUser") String loginUser, 
			@RequestParam("pwdUser") String pwdUser) {
		return annuaireService.addUser(nomUser, emailUser, loginUser, pwdUser); 
	}   
	
	@PostMapping("/register")
	public User registerUser(@RequestBody RegistreForm userForm){ 
		
		if(!userForm.getPassword().equals(userForm.getRepassword())) 
			throw new RuntimeException("les mots de passe ne sont pas identiques"); 
	    
		User u = annuaireService.findUserByName(userForm.getUsername()) ;  
		if(u!=null) 
			throw new RuntimeException("cet utilisateur existe déjà !"); 
		
		User user =new User(); 
		user.setLoginUser(userForm.getUsername());  
		user.setPwdUser(userForm.getPassword()); 
		user.setNomUser("null"); 
		user.setEmailUser(null);     
		annuaireService.addRoleToUser(userForm.getUsername(), "SAKANE");
		 annuaireJpaRepository.save(user);    
		 return user ; 
	}
}
