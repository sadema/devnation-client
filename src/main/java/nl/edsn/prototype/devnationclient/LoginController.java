package nl.edsn.prototype.devnationclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
public class LoginController {

    @GetMapping("/login")
    public RedirectView redirect(@RequestParam MultiValueMap<String, String> parameters, HttpServletRequest request) {
        List<String> uris = parameters.get("uri");
        if (request != null && uris != null) {
            log.debug("SessionId: " + request.getSession().getId() + " " + "uri: " + uris.get(0));
            request.getSession().setAttribute("uri", uris.get(0));
            return new RedirectView("/oauth2/authorization/devnation");
        }
        throw new IllegalStateException("Invalid request or no uri request parameter");
    }

    @GetMapping("/")
    public RedirectView callback(HttpServletRequest request) {
        String uri = (String) request.getSession().getAttribute("uri");
        if (uri != null) {
            log.debug("URI: " + uri);
        }
        return new RedirectView("http://localhost:7005");
    }
}
