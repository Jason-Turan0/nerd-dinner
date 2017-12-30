package com.dbs.sae.training.nerddinner.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "nerddinner")
public class NerdDinnerConfigurationProperties {
    private Boolean sendEmails = false;
    private String emailSavePath = "";
}
