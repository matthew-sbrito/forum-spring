package br.com.techsoft.forum.forms;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LoginForm {

    @NotNull @NotEmpty
    private String email;

    @NotNull @NotEmpty
    private String senha;

    public String getEmail() {
        return email;
    }

    public UsernamePasswordAuthenticationToken authData() {
        return new UsernamePasswordAuthenticationToken(email, senha);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
