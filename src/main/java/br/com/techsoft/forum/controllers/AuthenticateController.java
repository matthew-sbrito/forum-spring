package br.com.techsoft.forum.controllers;

import br.com.techsoft.forum.forms.LoginForm;
import br.com.techsoft.forum.models.Usuario;
import br.com.techsoft.forum.services.AuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticateController {

    @Autowired
    AuthenticateService authenticateService;

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody @Valid LoginForm form) {
        System.out.println(form.getEmail());
        System.out.println(form.getSenha());
        UserDetails usuario = authenticateService.loadUserByUsername(form.getEmail());
        System.out.println(usuario.getUsername());
        return ResponseEntity.ok().build();
    }
}
