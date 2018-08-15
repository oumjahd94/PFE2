package ma.mt.fo.security;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParser.Feature;
//import org.springframework.security.core.userdetails.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ma.mt.fo.entity.User;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
   
	private AuthenticationManager authenticationManager;  
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
     super() ; 
     this.authenticationManager = authenticationManager ; 
	   
	}

     // 
	@Override
	public Authentication attemptAuthentication(
			HttpServletRequest request,
			HttpServletResponse response)
			throws AuthenticationException {
	
		//ObjectMapper objectMapper = new ObjectMapper();
	//	objectMapper.configure(Feature.AUTO_CLOSE_SOURCE, true); 

		
	   User user = null ;   
	   
	   System.out.println("Je suis la avant ---OK");
        try{ 
      	    user = new ObjectMapper().readValue(request.getInputStream(), User.class) ;
        }catch(Exception ex){
        	throw new RuntimeException(ex); 
        } 
        
	    System.out.println("*************************");   
	    System.out.println("username: "+ user.getLoginUser());  
	    System.out.println(" password :"+ user.getPwdUser()); 
	    
		return authenticationManager
				.authenticate(
				       new UsernamePasswordAuthenticationToken( 
				    		   user.getLoginUser(), user.getPwdUser()
				));
	} 
	 
	// cest dans cette méthode q'on génère le  token  
	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, 
			FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		    
		org.springframework.security.core.userdetails.User springUser =
				(org.springframework.security.core.userdetails.User) authResult.getPrincipal() ; 
		
		
		// génération de Token 
		String jwt = Jwts.builder()
				.setSubject(springUser.getUsername())
				.setExpiration(new Date(System.currentTimeMillis()+ SecurityConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET) 
				.claim("roles",springUser.getAuthorities())
			    .compact(); 
      
		// Envoyer dans reponse 
		response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX+jwt);
         		
//		super.successfulAuthentication(request, response, chain, authResult);
	}   
	
	
}
