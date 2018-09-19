package nerddinner.domain;

import nerddinner.data.models.Nerd;
import nerddinner.data.repositories.NerdRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        Nerd nerd = this.nerdRepo.findOneByPropertyValue(Nerd.class, username, (n, u) -> n.setUserName(u));
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
