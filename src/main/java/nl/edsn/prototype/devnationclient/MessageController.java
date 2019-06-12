package nl.edsn.prototype.devnationclient;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
}
