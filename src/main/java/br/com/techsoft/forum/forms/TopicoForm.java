package br.com.techsoft.forum.forms;

import br.com.techsoft.forum.models.Curso;
import br.com.techsoft.forum.models.Topico;
import br.com.techsoft.forum.models.Usuario;
import br.com.techsoft.forum.repositories.CursoRepository;

import javax.persistence.EntityNotFoundException;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TopicoForm {

    @NotNull @NotEmpty @Size(min = 5)
    private String titulo;

    @NotNull @NotEmpty @Size(min = 5)
    private String mensagem;

    @NotNull @NotEmpty
    private String nomeCurso;

    public Topico get(CursoRepository cursoRepository, Usuario user) throws EntityNotFoundException {
        Curso curso = cursoRepository.findByNomeIgnoreCase(this.nomeCurso);

        if(curso == null) {
            throw new EntityNotFoundException();
        }

        return new Topico(titulo, mensagem, curso, user);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public String getNomeCurso() { return nomeCurso; }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }
}
