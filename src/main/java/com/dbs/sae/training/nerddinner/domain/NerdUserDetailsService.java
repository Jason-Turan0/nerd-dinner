package com.dbs.sae.training.nerddinner.domain;

import com.dbs.sae.training.nerddinner.data.models.Nerd;
import com.dbs.sae.training.nerddinner.data.repositories.NerdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class NerdUserDetailsService implements UserDetailsService {

    private final NerdRepository nerdRepo;

    @Autowired
    public NerdUserDetailsService(NerdRepository nerdRepo) {
        this.nerdRepo = nerdRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Nerd example = new Nerd();
        example.setUserName(username);
        Nerd nerd = this.nerdRepo.findOne(Example.of(example));
        if (nerd == null) {
            throw new UsernameNotFoundException("Failed to find NERD with user name: " + username);
        }
        return toUserDetails(nerd);
    }

    private UserDetails toUserDetails(Nerd nerd) {
        return User.withUsername(nerd.getUserName())
                .password(nerd.getPassword())
                .roles("NERD!!!!").build();
    }
}
