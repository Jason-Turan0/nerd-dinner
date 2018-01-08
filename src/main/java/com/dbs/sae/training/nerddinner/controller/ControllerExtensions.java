package com.dbs.sae.training.nerddinner.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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
}
