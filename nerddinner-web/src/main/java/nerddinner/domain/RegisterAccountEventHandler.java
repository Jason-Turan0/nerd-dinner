package nerddinner.domain;

import nerddinner.data.models.Nerd;
import nerddinner.data.models.NerdContactType;
import nerddinner.data.models.NerdEmail;
import nerddinner.data.repositories.NerdContactTypeRepository;
import nerddinner.data.repositories.NerdRepository;
import nerddinner.model.RegisterAccount;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
public class RegisterAccountEventHandler {

    private final NerdRepository nerdRepository;
    private final NerdContactTypeRepository contactTypeRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterAccountEventHandler(
            NerdRepository nerdRepository,
            NerdContactTypeRepository contactTypeRepository,
            PasswordEncoder passwordEncoder) {
        this.nerdRepository = nerdRepository;
        this.contactTypeRepository = contactTypeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void onRegisterAccount(RegisterAccount account) {
        NerdContactType contactType = contactTypeRepository.findOne(account.getSelectedContactType());
        Nerd n = new Nerd();
        n.setUserName(account.getUsername());
        n.setFirstName(account.getFirstName());
        n.setLastName(account.getLastName());

        String encodedPassword = passwordEncoder.encode(account.getPassword());
        n.setPassword(encodedPassword);
        n.setAvatar(account.getAvatar());
        NerdEmail ne = new NerdEmail();
        ne.setLastUpdateDate(Timestamp.from(Instant.now()));
        ne.setEmail(account.getEmail());
        ne.setNerd(n);

        ne.setNerdContactType(contactType);
        n.getEmails().add(ne);
        nerdRepository.save(n);
    }


}
