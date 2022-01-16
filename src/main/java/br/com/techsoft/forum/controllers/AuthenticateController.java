package br.com.techsoft.forum.controllers;

import br.com.techsoft.forum.dtos.TokenDto;
import br.com.techsoft.forum.forms.LoginForm;
import br.com.techsoft.forum.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Profile(value = {"prod", "test"})
public class AuthenticateController
{

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDto> authenticate(@RequestBody @Valid LoginForm form) {
        UsernamePasswordAuthenticationToken authData = form.authData();

        try {
             Authentication authentication = authenticationManager.authenticate(authData);
             String token = tokenService.encode(authentication);
             return ResponseEntity.ok(new TokenDto(token, "Bearer"));
        }catch (AuthenticationException e) {
            return  ResponseEntity.badRequest().build();
        }
    }
}
