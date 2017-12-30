package com.dbs.sae.training.nerddinner.domain;

import com.dbs.sae.training.nerddinner.data.models.Nerd;
import com.dbs.sae.training.nerddinner.data.repositories.NerdRepository;
import com.dbs.sae.training.nerddinner.model.ResetPassword;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ResetPasswordService {

    private final NerdRepository repository;
    private final PasswordEncoder passwordEncoder;

    public ResetPasswordService(NerdRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void setupResetPassword(String resetKey, ResetPassword resetPassword) {
        resetPassword.setResetKey(resetKey);
        Nerd forgetfulNerdRequestingReset = resetKey == null ? null : repository.findOneByPropertyValue(Nerd.class, resetKey, (n, k) -> n.setPasswordReset(resetKey));
        resetPassword.setResetKeyIsValid(forgetfulNerdRequestingReset != null);
        resetPassword.setUsername(forgetfulNerdRequestingReset != null ? forgetfulNerdRequestingReset.getUserName() : null);
    }

    public void onResetPassword(ResetPassword resetPassword) {
        Nerd forgetfulNerdRequestingReset = repository.findOneByPropertyValue(Nerd.class, resetPassword.getResetKey(), (n, k) -> n.setPasswordReset(resetPassword.getResetKey()));
        String encodedPassword = passwordEncoder.encode(resetPassword.getPassword());
        forgetfulNerdRequestingReset.setPassword(encodedPassword);
        forgetfulNerdRequestingReset.setPasswordReset(null);
        repository.save(forgetfulNerdRequestingReset);
    }
}
