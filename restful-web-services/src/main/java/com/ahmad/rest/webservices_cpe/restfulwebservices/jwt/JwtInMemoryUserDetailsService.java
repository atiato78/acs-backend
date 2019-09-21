package com.ahmad.rest.webservices_cpe.restfulwebservices.jwt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtInMemoryUserDetailsService implements UserDetailsService {

  static List<JwtUserDetails> inMemoryUserList = new ArrayList<>();

  static {
	  inMemoryUserList.add(new JwtUserDetails(1L, "admin",
		        "$2a$10$8ej21aEkhHv5CwNCDbw5TeI56UY0JkvRrfNhnbVxeUvRF/EJgep4K", "admin"));
			  
	  inMemoryUserList.add(new JwtUserDetails(1L, "ahmad",
				        "$2a$10$9UJoFieF8mQv8Mtf79bQZuLX1UavTOFuukJ8WUS51Lb75ppuV/kBu", "user"));
    
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<JwtUserDetails> findFirst = inMemoryUserList.stream()
        .filter(user -> user.getUsername().equals(username)).findFirst();
    
    
    //ahm - encode any new password
    //*********************************//
//    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//    String encodedPassword = encoder.encode("ahmad");
//    System.out.println(("encodedPassword: "+encodedPassword));
    //**********************************//
    
    

    if (!findFirst.isPresent()) {
      throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
    }

    return findFirst.get();
  }

}


