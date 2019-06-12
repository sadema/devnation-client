package nl.edsn.prototype.devnationclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@Slf4j
@RestController
public class UserController {

    @GetMapping("/api/user")
    public String getUser(@AuthenticationPrincipal Principal principal) {
        return principal.getName();     // gives bcb0d419-1908-41c4-9883-0d3e48544645
    }

    @GetMapping("/preferred_username")
    public String getUser(OAuth2AuthenticationToken authenticationToken) {
        return (String) authenticationToken.getPrincipal().getAttributes().get("preferred_username");
    }

    @GetMapping("/username")
    public String getUserName(@RegisteredOAuth2AuthorizedClient("devnation")OAuth2AuthorizedClient authorizedClient) {
        return authorizedClient.getPrincipalName(); // gives bcb0d419-1908-41c4-9883-0d3e48544645
    }

    @GetMapping("/granttype")
    public String getClientDetails(@RegisteredOAuth2AuthorizedClient("devnation")OAuth2AuthorizedClient authorizedClient) {
        return authorizedClient.getClientRegistration().getAuthorizationGrantType().getValue();
    }

    @GetMapping("/jwt")
    public String getJwt(@RegisteredOAuth2AuthorizedClient("devnation")OAuth2AuthorizedClient authorizedClient) {
        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        if (accessToken != null) {
            return accessToken.getTokenValue();
        }
        return "no token value";
    }

    @GetMapping("/details")
    public Map<String, Object> getDetails(OAuth2AuthenticationToken authenticationToken) {
        return authenticationToken.getPrincipal().getAttributes();
    }

}
