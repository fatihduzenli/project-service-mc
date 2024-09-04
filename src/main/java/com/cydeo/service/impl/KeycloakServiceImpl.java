package com.cydeo.service.impl;

import com.cydeo.config.KeycloakProperties;
import com.cydeo.exception.UserNotFoundException;
import com.cydeo.service.KeycloakService;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeycloakServiceImpl implements KeycloakService {

    private final KeycloakProperties keycloakProperties;

    public KeycloakServiceImpl(KeycloakProperties keycloakProperties) {
        this.keycloakProperties = keycloakProperties;
    }

    @Override
    public String getAccessToken() {
        KeycloakAuthenticationToken keycloakAuthenticationToken = getAuthentication();
        return "Bearer " + keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getTokenString();
    }

    @Override
    public String getUsername() {
        SimpleKeycloakAccount account = (SimpleKeycloakAccount) getAuthentication().getAccount();
        return account.getKeycloakSecurityContext().getToken().getPreferredUsername();
    }

    @Override
    public List<String> getUserClientRoles(String username) {

        try (Keycloak keycloak = getKeycloakInstance()) {

            RealmResource realmResource = keycloak.realm(keycloakProperties.getRealm());
            UsersResource usersResource = realmResource.users();

            List<UserRepresentation> userRepresentations = usersResource.search(username);

            if (userRepresentations.isEmpty()) {
                throw new UserNotFoundException("User does not exist.");
            }

            ClientRepresentation appClient = realmResource.clients()
                    .findByClientId(keycloakProperties.getClientId()).get(0);

            String clientId = appClient.getId();

            UserRepresentation keycloakUser = userRepresentations.get(0);

            List<RoleRepresentation> existingRoles = realmResource.users().get(keycloakUser.getId())
                    .roles().clientLevel(clientId).listEffective();

            return existingRoles.stream()
                    .map(RoleRepresentation::getName)
                    .collect(Collectors.toList());

        }

    }

    @Override
    public boolean hasClientRole(String username, String role) {
        List<String> userClientRoles = getUserClientRoles(username);
        return userClientRoles.stream().anyMatch(eachRole -> eachRole.equals(role));
    }

    private KeycloakAuthenticationToken getAuthentication() {
        return (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    }

    private Keycloak getKeycloakInstance() {
        return Keycloak.getInstance(
                keycloakProperties.getAuthServerUrl(),
                keycloakProperties.getMasterRealm(),
                keycloakProperties.getMasterUser(),
                keycloakProperties.getMasterUserPswd(),
                keycloakProperties.getMasterClient());
    }

}
