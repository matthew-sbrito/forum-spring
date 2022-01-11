package br.com.techsoft.forum.repositories;

import br.com.techsoft.forum.models.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Page<Topico> findByCursoNomeContainingIgnoreCase(String curso, Pageable pageable);

    @Query("SELECT t FROM Topico t WHERE t.curso.nome = :curso")
    List<Topico> findByCurse(@Param("curso") String curso);
}
