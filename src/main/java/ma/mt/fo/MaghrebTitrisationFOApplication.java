package ma.mt.fo;

import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ma.mt.fo.dao.AnnuaireJpaRepository;
import ma.mt.fo.dao.TypeCredilogJpaRepository;
import ma.mt.fo.entity.Roles;
import ma.mt.fo.entity.TypeCredilog;
import ma.mt.fo.entity.User;
import ma.mt.fo.service.AnnuaireService;
import ma.mt.fo.service.interfaces.IAnnuaireService;


@SpringBootApplication
public class MaghrebTitrisationFOApplication implements CommandLineRunner{ 
	
	@Autowired
	AnnuaireJpaRepository annuaireJpaRepository ;  
//	
//	@Autowired
//	TypeCredilogJpaRepository typeCredilogJpaRepository ; 
//	
//	@Autowired
//	IAnnuaireService iAnnuaireService ; 
////		
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder ;  
	
	public static void main(String[] args) {
//		System.out.println("Waiting starting spring boot application");
		SpringApplication.run(MaghrebTitrisationFOApplication.class, args);
		System.out.println("----- Your application Front Office has started----- !!!");
	} 
	
//    GregorianCalendar laDate = new GregorianCalendar();
//    int jour, mois, annee;  

	@Override
	public void run(String... args) throws Exception {    
//		   
//		   annuaireJpaRepository.save(new User("admin","admin@lnet.ma",
//				  "admin", bCryptPasswordEncoder.encode("adminlnet2018"))) ;  
//		   
//		   annuaireJpaRepository.save(new User("test66","test66@lnet.ma",
//					  "test66", bCryptPasswordEncoder.encode("test66"))) ; 
//		   annuaireJpaRepository.save(new User("med","moumjahd@lnet.ma",
//					  "test", bCryptPasswordEncoder.encode("test"))) ; 
		   	
		
//		   annuaireJpaRepository.save(new User("test99","test99@lnet.ma",
//		  "test99", bCryptPasswordEncoder.encode("test99"))) ; 
		
		
		
//		 TypeCredilog fondSakane = typeCredilogJpaRepository.findOne(4L) ;   
//		 TypeCredilog CredilogI = typeCredilogJpaRepository.findOne(5L) ;  
//		 TypeCredilog CredilogII = typeCredilogJpaRepository.findOne(6L) ;  
//         
//		 System.out.println(fondSakane.getEntitledTypeCredilog());  
//		   iAnnuaireService.addRole(
//				   new Roles(1L, "SAKANE",fondSakane));  
//		   
//		   iAnnuaireService.addRole(
//				   new Roles(2L, "CREDILOG II",CredilogII));  
//		   
//		   iAnnuaireService.addRole(
//				   new Roles(3L, "CREDILOG I",CredilogI)) ; 
		   
//		   
	 //  iAnnuaireService.addRoleToUser("test77", "CREDILOG II");
//		   iAnnuaireService.addRoleToUser("test", "SAKANE"); 
//		   iAnnuaireService.addRoleToUser("test66", "CREDILOG I");
	} 
	
/// Instanciation de  BCrypt 

	@Bean
	public BCryptPasswordEncoder getBC(){		
		return new BCryptPasswordEncoder() ; 
	}  	
	
}
