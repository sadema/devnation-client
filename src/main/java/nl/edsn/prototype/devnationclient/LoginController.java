package nl.edsn.prototype.devnationclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Slf4j
@RestController
public class LoginController {

    @GetMapping("/login")
    public RedirectView redirect(@RequestParam MultiValueMap<String, String> parameters, HttpServletRequest request) {
        String uri = parameters.get("uri").get(0);
        log.debug("URI: " + uri);
        if (request != null) {
            log.debug("=================SessionId: " + request.getSession().getId());
            request.getSession().setAttribute("uri", uri);
        }
        return new RedirectView("/oauth2/authorization/devnation");
    }

//    @GetMapping("/callback")
//    public RedirectView callback(HttpServletRequest request) {
////        return "{ \"text\" : \"callback\" }";
//        return new RedirectView("http://localhost:7005/api/message");
//    }

    @GetMapping("/")
    public RedirectView callback(HttpServletRequest request) {
        String uri = (String) request.getSession().getAttribute("uri");
        if (uri != null) {
            log.debug("URI: " + uri);
        }
        return new RedirectView(uri);
    }
}
