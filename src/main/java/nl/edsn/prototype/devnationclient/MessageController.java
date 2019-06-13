package nl.edsn.prototype.devnationclient;

import com.fasterxml.jackson.databind.node.TextNode;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RequiredArgsConstructor
@RestController
public class MessageController {

    private final WebClient webClient;

    @GetMapping("/message")
    Mono<String> message(@RegisteredOAuth2AuthorizedClient("devnation") OAuth2AuthorizedClient authorizedClient) {
        return this.webClient.get()
                .uri("http://localhost:7000/message")
                .attributes(oauth2AuthorizedClient(authorizedClient))
                .retrieve()
                .bodyToMono(String.class);
    }

    @GetMapping("/message/access-token")
    public TextNode getAccessToken(@RegisteredOAuth2AuthorizedClient("devnation")OAuth2AuthorizedClient authorizedClient) {
        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        if (accessToken != null) {
            return new TextNode(accessToken.getTokenValue());
        }
        throw new IllegalStateException("no token value");
    }

    @GetMapping("/message/access-token-scopes")
    public Set<String> getAccessTokenScopes(@RegisteredOAuth2AuthorizedClient("devnation")OAuth2AuthorizedClient authorizedClient) {
        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        if (accessToken != null) {
            return accessToken.getScopes();
        }
        throw new IllegalStateException("no token");
    }

    @GetMapping("/message/token-principal-details")
    public Map<String, Object> getDetails(OAuth2AuthenticationToken authenticationToken) {
        return authenticationToken.getPrincipal().getAttributes();
    }

    @GetMapping("/message/principal-name")
    public TextNode getUser(@AuthenticationPrincipal Principal principal) {
        return new TextNode(principal.getName());     // gives bcb0d419-1908-41c4-9883-0d3e48544645
    }

    @GetMapping("/message/granttype")
    public TextNode getClientDetails(@RegisteredOAuth2AuthorizedClient("devnation")OAuth2AuthorizedClient authorizedClient) {
        return new TextNode(authorizedClient.getClientRegistration().getAuthorizationGrantType().getValue());
    }


}
