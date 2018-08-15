package ma.mt.fo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ma.mt.fo.security.JWTAuthenticationFilter;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
     
	 @Autowired
	 private UserDetailsService  userDetailsService; 
	
	 @Autowired
	 private BCryptPasswordEncoder  bCryptPasswordEncoder ; 
	 
	 @Override
     protected void configure(AuthenticationManagerBuilder auth) throws Exception {  

//    authentification basée sur userDetailsService  
		 
		   auth.userDetailsService(userDetailsService)
	       .passwordEncoder(bCryptPasswordEncoder) ;  
		 		 
//    	auth.inMemoryAuthentication()
//    	.withUser("med").password("2222").roles("SAKANE","CREDILOG I")
//        .and() 
//        .withUser("kamal").password("1234").roles("SAKANE") ; 
 			   	 
     }  
     
     @Override 
    protected void configure(HttpSecurity http) throws Exception { 
    	 
//    	// c'est cette partie qui est concernée
    	 http.csrf().disable() ; 
    	 http.formLogin().loginPage("http://localhost:2222/login");   
    	 http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  
   	     http.authorizeRequests().antMatchers("/login/**", "/register/**","/api/annuaires/",
   	    		 "/api/fondsDataSnapshotsFo/4", "/api/scenariosFondsRa/","/api/scenariosFondsDDT/",
   	    		 "/api/fondsDataPricings/4", "/api/performanceCollaterals/4", 
   	    		 "/api/fondsDataHistoriques/4","/api/fondsDataRapport/downloadPDF/**").permitAll(); 
   	     
     	 http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/annuaires/") ;  //.hasAuthority("SAKANE") ; 
   	     http.authorizeRequests().anyRequest().authenticated(); 
    	 http.addFilter(new JWTAuthenticationFilter(authenticationManager())); 
         http.addFilterBefore(new JWTAuthorisationFilter(), UsernamePasswordAuthenticationFilter.class) ; 
    	 
// 	  	    http.csrf().disable() ; 
//    	    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  
// 	  	    http.formLogin() ;   
//    	    // avec ça on demande à ce que le login n'exige pas d'authentification  
//    	    http.authorizeRequests().antMatchers("/login/*", "/register/*").permitAll(); 
////	        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/annuaires/**") 
////    	    .hasAnyAuthority("SAKANE");       
//        	http.authorizeRequests().anyRequest().authenticated();    
//        	http.addFilter(new JWTAuthenticationFilter(authenticationManager())); 
//            http.addFilterBefore(new JWTAuthorisationFilter(), UsernamePasswordAuthenticationFilter.class) ; 
     }   

}
