package nerddinner.model;

import nerddinner.data.models.Nerd;
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
    private final String email;

    @Getter
    private final String userName;

    @Getter
    private Integer nerdPk;

    public NerdProfile(Nerd nerd) {
        firstName = getValueOrEmpty(nerd, n -> n.getFirstName());
        lastName = getValueOrEmpty(nerd, n -> n.getLastName());
        fullName = String.format("%s %s", firstName, lastName);
        avatar = getValueOrEmpty(nerd, n -> n.getAvatar());
        userName = getValueOrEmpty(nerd, n -> nerd.getUserName());
        nerdPk = getValueOrEmpty(nerd, n -> nerd.getNerdPk());
        email = nerd != null && nerd.getEmails().size() > 0 ?
                nerd.getEmails().stream().findFirst().get().getEmail() :
                "";
    }

    private <T> T getValueOrEmpty(Nerd n, Function<Nerd, T> accessor) {
        if (n == null) return null;
        return accessor.apply(n);
    }

}
