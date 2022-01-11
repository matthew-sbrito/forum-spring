package br.com.techsoft.forum.services;

import br.com.techsoft.forum.dtos.TopicoDetailsDto;
import br.com.techsoft.forum.dtos.TopicoDto;
import br.com.techsoft.forum.forms.AttTopicoForm;
import br.com.techsoft.forum.forms.TopicoForm;
import br.com.techsoft.forum.models.Topico;
import br.com.techsoft.forum.repositories.CursoRepository;
import br.com.techsoft.forum.repositories.TopicoRepository;
import br.com.techsoft.forum.utils.PaginationResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class TopicosService {

    @Autowired
    private TopicoRepository _topicoRepository;

    @Autowired
    private CursoRepository _cursoRepository;

    public PaginationResponse<TopicoDto> list(String curse, Integer page, Integer size) throws EntityNotFoundException {
        Integer currentPage = page - 1;
        Pageable paging = PageRequest.of(currentPage, size);
        Page<Topico> pagingTopic;

        if(curse == null)
            pagingTopic = _topicoRepository.findAll(paging);
        else
            pagingTopic = _topicoRepository.findByCursoNomeContainingIgnoreCase(curse, paging);

        List<TopicoDto> topicsDto = TopicoDto.list(pagingTopic.getContent());

        if(!topicsDto.isEmpty()){
            return new PaginationResponse<TopicoDto>(topicsDto, pagingTopic);
        }
        throw new EntityNotFoundException();
    }

    public TopicoDto create(TopicoForm topicoForm) throws EntityNotFoundException {
        Topico topico = topicoForm.get(_cursoRepository);
        Topico topicoSave = _topicoRepository.save(topico);
        return new TopicoDto(topicoSave);
    }

    public TopicoDetailsDto findOne(Long id) throws EntityNotFoundException {
        Optional<Topico> topico = _topicoRepository.findById(id);

        if(!topico.isPresent()){
            throw new EntityNotFoundException();
        }

        TopicoDetailsDto topicoDto = new TopicoDetailsDto(topico.get());

        return topicoDto;
    }

    public TopicoDto update(Long id, AttTopicoForm attTopicoForm) throws EntityNotFoundException, Exception {
        Optional<Topico> findTopico = _topicoRepository.findById(id);

        if(!findTopico.isPresent()){
            throw new EntityNotFoundException();
        }

        Topico topico = findTopico.get();

        topico.setTitulo(attTopicoForm.getTitulo());
        topico.setMensagem(attTopicoForm.getMensagem());

        _topicoRepository.save(topico);

        return new TopicoDto(topico);
    }

    public boolean delete(Long id) throws EntityNotFoundException {
        Optional<Topico> findTopico = _topicoRepository.findById(id);

        if(!findTopico.isPresent()){
            throw new EntityNotFoundException();
        }

        _topicoRepository.delete(findTopico.get());

        return true;
    }
}
