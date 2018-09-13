package br.edu.ufcg.genus.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.repositories.UserRepository;

@Service
public class MyUserDetails implements UserDetailsService {
	
	@Autowired
    private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final User user = userRepository.findByUsername(username);
		if (user == null) throw new RuntimeException("User with username " + username + " not found!");
		
		return org.springframework.security.core.userdetails.User
	            .withUsername(username)
	            .password(user.getPassword())
	            .authorities(user.getRoles())
	            .accountExpired(false)
	            .accountLocked(false)
	            .disabled(false)
	            .build();
	}

}
