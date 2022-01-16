package br.com.techsoft.forum.controllers;

import br.com.techsoft.forum.config.context.ApplicationContext;
import br.com.techsoft.forum.dtos.TopicoDetailsDto;
import br.com.techsoft.forum.dtos.TopicoDto;

import br.com.techsoft.forum.forms.AttTopicoForm;
import br.com.techsoft.forum.forms.TopicoForm;
import br.com.techsoft.forum.models.Usuario;
import br.com.techsoft.forum.services.TopicosService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicosService topicosService;

    @GetMapping
    @Cacheable(value = "topicsList")
    public ResponseEntity<Page<TopicoDto>> list(
            @RequestParam(required = false) String curso,
            @PageableDefault(
                    sort = "id", direction = Sort.Direction.DESC, page = 0, size = 5
            ) Pageable pageable
    ) throws ResponseStatusException {
       try {
           Page<TopicoDto> response = topicosService.list(curso, pageable);
           return ResponseEntity.ok(response);
       }catch (EntityNotFoundException e) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Os tópicos não foram encontrados!");
       }catch (Exception e) {
           throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
       }
    }

    @PostMapping
    @CacheEvict(value = "topicsList", allEntries = true)
    public ResponseEntity<TopicoDetailsDto> create(
            @RequestBody @Valid TopicoForm topicoForm,
            UriComponentsBuilder uriBuilder
    ) throws ResponseStatusException {
        try {
            Usuario user = ApplicationContext.authenticatedUser();
            TopicoDetailsDto topico = topicosService.create(topicoForm, user);
            URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
            return ResponseEntity.created(uri).body(topico);
        }catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O curso não foi encontrado!");
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O tópico não foi criado!");
        }
    }

    @GetMapping("/{id}")
    public TopicoDetailsDto findOne(@PathVariable Long id) {
        try {
            TopicoDetailsDto topico = topicosService.findOne(id);
            return topico;
        }catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O tópico não foi encontrado!");
        }
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "topicsList", allEntries = true)
    public ResponseEntity<TopicoDto> update(
            @PathVariable Long id,
            @RequestBody @Valid AttTopicoForm attTopicoForm
    ) throws ResponseStatusException {
        try {
            TopicoDto topicoDto = topicosService.update(id, attTopicoForm);
            return ResponseEntity.ok(topicoDto);
        }catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O tópico não foi encontrado!");
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O tópico não foi atualizado!");
        }

    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "topicsList", allEntries = true)
    public ResponseEntity delete(@PathVariable Long id) throws ResponseStatusException {
        try {
            Boolean delete = topicosService.delete(id);
            return ResponseEntity.ok().build();
        }catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O tópico não foi encontrado!");
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O tópico não foi deletado!");
        }
    }
}
