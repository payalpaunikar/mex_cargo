package com.mexcorgo.security;


import com.mexcorgo.datamodel.User;
import com.mexcorgo.exception.UserNotFoundException;
import com.mexcorgo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User is not found"));

        return new MyUserDetails(existingUser);
    }
}
