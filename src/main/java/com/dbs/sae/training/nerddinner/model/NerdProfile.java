package com.dbs.sae.training.nerddinner.model;

import com.dbs.sae.training.nerddinner.data.models.Nerd;
import lombok.Getter;

import java.util.function.Function;

public class NerdProfile {

    @Getter
    private final String firstName;

    @Getter
    private final String lastName;

    @Getter
    private final String fullName;

    @Getter
    private final String avatar;

    @Getter
    private String email = "";

    public NerdProfile(Nerd nerd) {
        firstName = getValueOrEmpty(nerd, n -> n.getFirstName());
        lastName = getValueOrEmpty(nerd, n -> n.getLastName());
        fullName = String.format("%s %s", firstName, lastName);
        avatar = getValueOrEmpty(nerd, n -> n.getAvatar());

        if (nerd.getEmails().size() > 0) {
            this.email = nerd.getEmails().stream().findFirst().get().getEmail();
        }

    }

    private String getValueOrEmpty(Nerd n, Function<Nerd, String> accessor) {
        if (n == null) return "";
        return accessor.apply(n);
    }


}
