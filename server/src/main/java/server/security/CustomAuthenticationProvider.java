package server.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	
	SmsCode smsCode = new SmsCode();
	
    @SuppressWarnings("unchecked")
	@Override
    public Authentication authenticate(Authentication authentication) 
      throws AuthenticationException {
  
        String number = authentication.getName();
        
        String code = authentication.getCredentials().toString();
         
        
        if (smsCode.validate(number, code)) {
        	//System.out.println("number:" + number + " code:" + code);
            // use the credentials
            // and authenticate against the third-party system
            return new UsernamePasswordAuthenticationToken(
              number, code, (Collection<? extends GrantedAuthority>) new ArrayList<>());
        } else {
            return null;
        }
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    

    
}