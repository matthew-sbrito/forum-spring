package br.com.techsoft.forum.controllers;

import br.com.techsoft.forum.dtos.TopicoDto;

import br.com.techsoft.forum.forms.TopicoForm;
import br.com.techsoft.forum.services.TopicosService;
import br.com.techsoft.forum.utils.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicosService _topicosService;

    @GetMapping
    public ResponseEntity<PaginationResponse<TopicoDto>> list(
            @RequestParam(required = false) String curso,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ) throws ResponseStatusException {
       try {
           PaginationResponse<TopicoDto> response = _topicosService.list(curso, page, size);
           return new ResponseEntity<>(response, HttpStatus.OK);
       }catch (EntityNotFoundException e) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Os tópicos não foram encontrados!");
       }catch (Exception e) {
           throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
       }
    }

    @PostMapping
    public ResponseEntity<TopicoDto> create(@RequestBody TopicoForm topicoForm, UriComponentsBuilder uriBuilder) throws ResponseStatusException {
        try {
            TopicoDto topico = _topicosService.create(topicoForm);
            URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
            return ResponseEntity.created(uri).body(topico);
        }catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O curso não foi encontrado!");
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O tópico não foi criado!");
        }
    }
}
