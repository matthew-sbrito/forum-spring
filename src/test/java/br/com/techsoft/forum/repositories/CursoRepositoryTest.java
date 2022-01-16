package br.com.techsoft.forum.repositories;

import br.com.techsoft.forum.models.Curso;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class CursoRepositoryTest {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void findByNomeIgnoreCaseTestNotNull() {
        String curseName = "HTML 5";

        Curso html5 = new Curso();
        html5.setNome(curseName);
        html5.setCategoria("Programação");
        testEntityManager.persist(html5);

        Curso curse = cursoRepository.findByNomeIgnoreCase(curseName);

        Assertions.assertNotNull(curse);
    }

    @Test
    public void findByNomeIgnoreCaseTestName() {
        String curseName = "HTML 5";

        Curso html5 = new Curso();
        html5.setNome(curseName);
        html5.setCategoria("Programação");
        testEntityManager.persist(html5);

        Curso curse = cursoRepository.findByNomeIgnoreCase(curseName);

        Assertions.assertEquals(curseName, curse.getNome());
    }

    @Test
    public void findByNomeIgnoreCaseTestNull() {
        String curseName = "JPA";
        Curso curse = cursoRepository.findByNomeIgnoreCase(curseName);

        Assertions.assertNull(curse);
    }
}
