package nerddinner.controller;

import nerddinner.data.models.NerdContactType;
import nerddinner.data.models.NerdContactTypeDescription;
import nerddinner.data.repositories.NerdContactTypeRepository;
import nerddinner.model.SelectOption;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ControllerExtensions {
    public static List<String> getAvatarUrls(ApplicationContext applicationContext) {
        try {
            Resource[] avatarImages = applicationContext.getResources("classpath:static/images/avatars/*.svg");
            List<String> avatarUrls = Arrays.stream(avatarImages).map(r -> "/images/avatars/" + r.getFilename()).collect(Collectors.toList());
            return avatarUrls;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static List<SelectOption> getContactTypesAsSelectOptions(NerdContactTypeRepository contactTypeRepository, Locale l) {
        List<NerdContactType> contactTypeList = contactTypeRepository.findAll();
        List<SelectOption> contactTypes = contactTypeList.stream()
                .map(ct -> {
                    SelectOption o = new SelectOption();
                    String requestedLanguage = l.getLanguage();
                    Set<NerdContactTypeDescription> v = ct.getDescriptions().stream().collect(Collectors.toSet());
                    Optional<NerdContactTypeDescription> description = v
                            .stream()
                            .filter(d -> d.getLanguage().getLanguageCode().equalsIgnoreCase(requestedLanguage))
                            .findFirst();
                    o.setValue(ct.getNerdContactTypePk().toString());
                    o.setText(description.isPresent() ? description.get().getDescription() : "");
                    return o;
                }).collect(Collectors.toList());
        return contactTypes;
    }
}
