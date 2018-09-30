package nerddinner.configuration;

import nerddinner.data.models.Nerd;
import nerddinner.data.repositories.NerdRepository;
import nerddinner.model.NerdProfile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserProfileInterceptor extends HandlerInterceptorAdapter {
    private final NerdRepository nerdRepository;

    public UserProfileInterceptor(NerdRepository nerdRepository) {
        this.nerdRepository = nerdRepository;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView) throws Exception {
        if (modelAndView == null) return;

        Nerd nerd = null;
        if (request != null) {
            String userName = request.getRemoteUser();
            nerd = nerdRepository.findOneByPropertyValue(Nerd.class, userName, (n, un) -> n.setUserName(un));
        }
        modelAndView.addObject("nerdProfile", new NerdProfile(nerd));
    }
}
