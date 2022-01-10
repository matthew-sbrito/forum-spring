package br.com.techsoft.forum.repositories;

import br.com.techsoft.forum.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    Curso findByNomeIgnoreCase(String curso);
}
