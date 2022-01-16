package br.com.techsoft.forum.config.security;

import br.com.techsoft.forum.models.Usuario;
import br.com.techsoft.forum.repositories.UsuarioRepository;
import br.com.techsoft.forum.services.TokenService;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthenticateToken extends OncePerRequestFilter {

    private UsuarioRepository usuarioRepository;

    private TokenService tokenService;

    public AuthenticateToken(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService      = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = retrieveToken(request);

        Claims claims = tokenService.decoded(token);

        if(claims != null) {
            Long id = Long.parseLong(claims.getSubject());
            authenticateClient(id);
        }
        filterChain.doFilter(request, response);
    }

    private void authenticateClient(Long id) {
        Optional<Usuario> userFind = usuarioRepository.findById(id);

        if(!userFind.isPresent()) return;

        Usuario user = userFind.get();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String retrieveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7, token.length());
    }


}
