package nerddinner.data.models;

import lombok.Data;

@Data
public class LocaleViewModel {

    private String localeId;

    private String languageCode;
    private String languageName;

    private String countryCode;
    private String countryName;
}
