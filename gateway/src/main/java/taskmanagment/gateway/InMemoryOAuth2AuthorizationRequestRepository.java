package taskmanagment.gateway;

import org.springframework.security.oauth2.client.web.server.ServerAuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryOAuth2AuthorizationRequestRepository
        implements ServerAuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private final Map<String, OAuth2AuthorizationRequest> store = new ConcurrentHashMap<>();

    @Override
    public Mono<OAuth2AuthorizationRequest> loadAuthorizationRequest(ServerWebExchange exchange) {
        String state = exchange.getRequest().getQueryParams().getFirst("state");
        if (state == null) return Mono.empty();
        return Mono.justOrEmpty(store.get(state));
    }

    @Override
    public Mono<Void> saveAuthorizationRequest(OAuth2AuthorizationRequest request, ServerWebExchange exchange) {
        if (request == null) return Mono.empty();
        System.out.println("=== SAVING state=" + request.getState());
        store.put(request.getState(), request);
        System.out.println("=== STORE: " + store.keySet());
        return Mono.empty();
    }

    @Override
    public Mono<OAuth2AuthorizationRequest> removeAuthorizationRequest(ServerWebExchange exchange) {
        String state = exchange.getRequest().getQueryParams().getFirst("state");
        System.out.println("=== REMOVING state=" + state);
        System.out.println("=== STORE: " + store.keySet());
        if (state == null) return Mono.empty();
        return Mono.justOrEmpty(store.remove(state));
    }


}