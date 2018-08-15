package ma.mt.fo.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ma.mt.fo.dao.AnnuaireJpaRepository;
import ma.mt.fo.dao.RoleJpaRepository;
import ma.mt.fo.entity.Roles;
import ma.mt.fo.entity.User;
import ma.mt.fo.service.interfaces.IAnnuaireService; 

@Service
public class AnnuaireService implements IAnnuaireService {
       	
	private static final Logger LOG = Logger.getLogger(FondsDataSnapshotService.class);
   
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder ; 
	
	@Autowired
	private AnnuaireJpaRepository  annuaireJpaRepository ;   
	
	@Autowired
    private RoleJpaRepository roleJpaRepository ;  
    

	@Override
	public List<User> listeUtilisateurs() { 
		
		try {
			return annuaireJpaRepository.findAll();
		} catch (Exception e) {  
			LOG.info("Error listeUtilisateurs items !");	 
		} 
		return null ; 
	}

	@Override
	public boolean deleteUser(Long idUser) { 
		
		try {
			annuaireJpaRepository.delete(idUser); 
			return true;
		} catch (Exception e) { 
			LOG.info("Element hasn't deleted successfully !" + e.getMessage());

		} 
		return false;
	}

	@Override
	public boolean addUser(String nom, String email, String login, String password) { 
		String HashPwd = bCryptPasswordEncoder.encode(password); 
		try { 
			 annuaireJpaRepository.save(new User(nom,email,login, HashPwd)) ; 
			 // Je dois automatiquement affecterle role au user après sont insertion 
//			 addRoleToUser(login, role);
			return true ; 
		} catch (Exception e) {  
			LOG.info("Element hasn't added successfully !" + e.getMessage());

 		}
		return false ; 
	}

	@Override
	public User getUserById(Long idUser) {
		return annuaireJpaRepository.findOne(idUser);
	}

	@Override
	public User findUserByName(String userLogin) {
		return annuaireJpaRepository.findByloginUser(userLogin);
	}

	@Override
	public void addRoleToUser(String username, String role) {
        
		User  u = findUserByName(username);  
		Roles r = roleJpaRepository.findByintituleRole(role) ;   

		u.getRoles().add(r) ; 	 
		// afficher la liste des roles 
		for(Roles roles : u.getRoles()) 
			System.out.println(roles.getIntituleRole());
	}

	@Override
	public Roles addRole(Roles role) {
		
		return roleJpaRepository.save(role);
	}

}
