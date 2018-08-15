package ma.mt.fo.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import ma.mt.fo.service.interfaces.IAnnuaireService;  

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    
	@Autowired
	private IAnnuaireService  iAnnuaireService ;  
	
	//constuctor 
	public UserDetailsService() {
	}
    
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		ma.mt.fo.entity.User u = iAnnuaireService.findUserByName(username) ; 
		
		if (u == null) throw new UsernameNotFoundException(username) ; 
		
		Collection<GrantedAuthority> auhorities = new ArrayList<>(); 
		
		u.getRoles()
		.forEach
		(r->{
			auhorities.add(new SimpleGrantedAuthority(r.getIntituleRole())) ;
			});
		
		return new User(u.getLoginUser(), u.getPwdUser(), auhorities); 
				
	}

}
