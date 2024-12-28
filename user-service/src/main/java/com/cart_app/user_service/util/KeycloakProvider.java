package com.cart_app.user_service.util;

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;

public class KeycloakProvider {

    private static final String SERVER_URL = System.getenv("SERVER_URL");
    private static final String REALM_NAME = System.getenv("REALM_NAME");
    private static final String REALM_MASTER = System.getenv("REALM_MASTER");
    private static final String ADMIN_CLI = System.getenv("ADMIN_CLI");
    private static final String USER_CONSOLE = System.getenv("USER_CONSOLE");
    private static final String PASSWORD_CONSOLE = System.getenv("PASSWORD_CONSOLE");
    private static final String CLIENT_SECRET = System.getenv("CLIENT_SECRET");

    //metodo para configurar el acceso a la api de keycloak
    public static RealmResource getRealmResource(){
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(SERVER_URL)
                .realm(REALM_MASTER)
                .clientId(ADMIN_CLI)
                .username(USER_CONSOLE)
                .password(PASSWORD_CONSOLE)
                .clientSecret(CLIENT_SECRET)
                .resteasyClient(new ResteasyClientBuilderImpl()
                        .connectionPoolSize(10)
                        .build())
                .build();
        return keycloak.realm(REALM_NAME);
    }

    //metodo para poder trabajar con los usuarios
    public static UsersResource getUserResource(){
        RealmResource realmResource = getRealmResource();
        return realmResource.users();
    }

}
