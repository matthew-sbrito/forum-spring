package br.com.techsoft.forum.config.context;

import br.com.techsoft.forum.models.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ApplicationContext {
    public static Usuario authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!authentication.isAuthenticated()) return null;
        return (Usuario) authentication.getPrincipal();
    }
}
