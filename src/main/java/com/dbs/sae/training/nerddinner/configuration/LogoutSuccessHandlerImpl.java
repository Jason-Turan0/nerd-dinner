package com.dbs.sae.training.nerddinner.configuration;

import com.dbs.sae.training.nerddinner.controller.Paths;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogoutSuccessHandlerImpl implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        Map<String, String[]> pm = request.getParameterMap();
        List<String> urlParameters =
                Stream.concat(
                        pm.keySet().stream().map(k -> String.format("%s=%s", k, String.join(",", pm.get(k)))),
                        Stream.of("logout"))
                        .collect(Collectors.toList());

        String redirectUrl = String.format("%s?%s",
                Paths.Login.login,
                String.join("&", urlParameters));
        response.sendRedirect(redirectUrl);
    }
}
