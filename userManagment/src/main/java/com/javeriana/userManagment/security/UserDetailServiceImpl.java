package com.javeriana.userManagment.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.javeriana.userManagment.services.UserService;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        if(userService.getClientByEmail(email).isPresent()){
            return new UserDetailsImpl(userService.getClientByEmail(email).get().getEmail(), userService.getClientByEmail(email).get().getPassword(), userService.getClientByEmail(email).get().getRole().toString(), userService.getClientByEmail(email).get().getId());
        }

        else if(userService.getProviderByEmail(email).isPresent()){
            return new UserDetailsImpl(userService.getProviderByEmail(email).get().getEmail(), userService.getProviderByEmail(email).get().getPassword(), userService.getProviderByEmail(email).get().getRole().toString(), userService.getProviderByEmail(email).get().getId());
        }

        else{
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }
}
