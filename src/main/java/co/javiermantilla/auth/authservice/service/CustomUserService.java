package co.javiermantilla.auth.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import co.javiermantilla.auth.authservice.entity.User;
import co.javiermantilla.auth.authservice.repository.UserRepository;

@Service
public class CustomUserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not Found");
		}
		UserBuilder builder = null;
		builder = org.springframework.security.core.userdetails.User.withUsername(username);
		builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
		System.out.println("ROLE====" + user.getRoles().iterator().next().getName().toString());
		// builder.roles(user.getRoles().iterator().next().getName().toString(),"ADMIN");
		builder.roles(user.getRoles().iterator().next().getName().toString());

		 return builder.build();
	}
	
	public User saveUser(User user){
        return userRepository.save(user);
    }

}
