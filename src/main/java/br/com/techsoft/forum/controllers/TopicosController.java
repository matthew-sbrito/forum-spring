package br.com.techsoft.forum.controllers;

import br.com.techsoft.forum.dtos.TopicoDto;
import br.com.techsoft.forum.models.Topico;

import br.com.techsoft.forum.repositories.TopicoRepository;
import br.com.techsoft.forum.utils.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @GetMapping()
    public ResponseEntity<PaginationResponse> list(
            @RequestParam(required = false) String curso,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ){
       try {
           Integer currentPage = page - 1;
           Pageable paging = PageRequest.of(currentPage, size);
           Page<Topico> pagingTopico;

           if(curso == null)
               pagingTopico = topicoRepository.findAll(paging);
           else
               pagingTopico = topicoRepository.findByCursoNomeContainingIgnoreCase(curso, paging);

           List<TopicoDto> topicosDto = TopicoDto.list(pagingTopico.getContent());

           PaginationResponse<TopicoDto> response = new PaginationResponse(topicosDto, pagingTopico);

           return new ResponseEntity<>(response, HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
}
