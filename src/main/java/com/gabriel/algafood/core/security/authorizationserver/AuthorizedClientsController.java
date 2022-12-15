package com.gabriel.algafood.core.security.authorizationserver;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorizedClientsController {

    private final OAuth2AuthorizationQueryService oAuth2AuthorizationQueryService;
    private final RegisteredClientRepository clientRepository;
    private final OAuth2AuthorizationConsentService consentService;
    private final OAuth2AuthorizationService authorizationService;


    @GetMapping("/oauth2/authorized-clients")
    public String clientsList(Principal principal, Model model) {
        List<RegisteredClient> clients = oAuth2AuthorizationQueryService.listClientsWithConsent(principal.getName());
        model.addAttribute("clients", clients);
        return "pages/authorized-clients";
    }

    @PostMapping("/oauth2/authorized-clients/revoke")
    public String revokeClient(Principal principal, Model model, @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId) {
        RegisteredClient registeredClient = this.clientRepository.findByClientId(clientId);
        if (registeredClient == null) {
            throw new AccessDeniedException(String.format("Cliente %s não encontrado", clientId));
        }

        OAuth2AuthorizationConsent consent = this.consentService.findById(registeredClient.getId(), principal.getName());

        List<OAuth2Authorization> authorizations = this.oAuth2AuthorizationQueryService.listAuthorizations(principal.getName(), registeredClient.getId());
        if (consent != null) {
            this.consentService.remove(consent);
        }

        authorizations.forEach(authorization -> this.authorizationService.remove(authorization));
        return "redirect:/oauth2/authorized-clients";
    }
}
