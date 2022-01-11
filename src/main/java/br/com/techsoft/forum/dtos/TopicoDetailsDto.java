package br.com.techsoft.forum.dtos;

import br.com.techsoft.forum.models.StatusTopico;
import br.com.techsoft.forum.models.Topico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TopicoDetailsDto {

    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String nomeAutor;
    private StatusTopico status;
    private List<RespostaDto> respotas;

    public TopicoDetailsDto(Topico topico) {
        this.id          = topico.getId();
        this.titulo      = topico.getTitulo();
        this.mensagem    = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
        this.nomeAutor   = topico.getAutor().getNome();
        this.status      = topico.getStatus();
        this.respotas    = new ArrayList<>();
        this.respotas.addAll(RespostaDto.list(topico.getRespostas()));
    }

    public static List<TopicoDetailsDto> list(List<Topico> list) {
        return  list.stream().map(TopicoDetailsDto::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public StatusTopico getStatus() {
        return status;
    }

    public List<RespostaDto> getRespotas() {
        return respotas;
    }
}
