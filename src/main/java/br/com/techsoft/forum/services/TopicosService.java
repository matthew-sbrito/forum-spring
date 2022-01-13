package br.com.techsoft.forum.services;

import br.com.techsoft.forum.dtos.TopicoDetailsDto;
import br.com.techsoft.forum.dtos.TopicoDto;
import br.com.techsoft.forum.forms.AttTopicoForm;
import br.com.techsoft.forum.forms.TopicoForm;
import br.com.techsoft.forum.models.Topico;
import br.com.techsoft.forum.models.Usuario;
import br.com.techsoft.forum.repositories.CursoRepository;
import br.com.techsoft.forum.repositories.TopicoRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class TopicosService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public Page<TopicoDto> list(String curse, Pageable pageable) throws EntityNotFoundException {
        Page<Topico> pagingTopic;
        if(curse == null)
            pagingTopic = topicoRepository.findAll(pageable);
        else
            pagingTopic = topicoRepository.findByCursoNomeContainingIgnoreCase(curse, pageable);

        return pagingTopic.map(TopicoDto::new);
    }

    public TopicoDetailsDto create(TopicoForm topicoForm, Usuario user) throws EntityNotFoundException {
        Topico topico = topicoForm.get(cursoRepository, user);
        Topico topicoSave = topicoRepository.save(topico);
        return new TopicoDetailsDto(topicoSave);
    }

    public TopicoDetailsDto findOne(Long id) throws EntityNotFoundException {
        Optional<Topico> topico = topicoRepository.findById(id);

        if(!topico.isPresent()){
            throw new EntityNotFoundException();
        }

        TopicoDetailsDto topicoDto = new TopicoDetailsDto(topico.get());

        return topicoDto;
    }

    public TopicoDto update(Long id, AttTopicoForm attTopicoForm) throws EntityNotFoundException, Exception {
        Optional<Topico> findTopico = topicoRepository.findById(id);

        if(!findTopico.isPresent()){
            throw new EntityNotFoundException();
        }

        Topico topico = findTopico.get();

        topico.setTitulo(attTopicoForm.getTitulo());
        topico.setMensagem(attTopicoForm.getMensagem());

        topicoRepository.save(topico);

        return new TopicoDto(topico);
    }

    public boolean delete(Long id, Usuario user) throws EntityNotFoundException {
        Optional<Topico> findTopico = topicoRepository.findById(id);

        if(!findTopico.isPresent()){
            throw new EntityNotFoundException();
        }

        Topico topico = findTopico.get();

        if(topico.getAutor().equals(user)){
            topicoRepository.delete(topico);
            return true;
        }


        return false;
    }
}
