package com.pedroprior.projetointegrador.config.security;

import com.pedroprior.projetointegrador.entities.UserModel;
import com.pedroprior.projetointegrador.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            UserModel userModel = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));


        return new User(userModel.getUsername(), userModel.getPassword(), true, true,
                true, true, userModel.getAuthorities());
    }
}
