package ma.mt.fo.service.interfaces;
import java.util.List;

import ma.mt.fo.entity.Roles;
import ma.mt.fo.entity.User;

public interface IAnnuaireService { 
	
    public  List<User> listeUtilisateurs();   
    public boolean deleteUser(Long idUser);
    public boolean addUser(String nom, String email,String login,String password); 
    public User getUserById(Long idUser);  
 	public User findUserByName (String userLogin);  
 	public void addRoleToUser(String username, String role);  
 	public Roles addRole(Roles role); 
}
