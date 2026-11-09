package com.salesianostriana.dam.spring.data.jpa.data;

import com.salesianostriana.dam.spring.data.jpa.model.Critica;
import com.salesianostriana.dam.spring.data.jpa.model.Genero;
import com.salesianostriana.dam.spring.data.jpa.model.Pelicula;
import com.salesianostriana.dam.spring.data.jpa.repository.CriticaRepository;
import com.salesianostriana.dam.spring.data.jpa.repository.GeneroRepository;
import com.salesianostriana.dam.spring.data.jpa.repository.PeliculaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Component
@DependsOn("dataSeed")
public class DataCheck {

    private final TransactionTemplate transactionTemplate;
    private final PeliculaRepository peliculaRepository;
    private final CriticaRepository criticaRepository;
    private final GeneroRepository generoRepository;

    public DataCheck(PlatformTransactionManager transactionManager,
                     PeliculaRepository peliculaRepository,
                     CriticaRepository criticaRepository,
                     GeneroRepository generoRepository) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.peliculaRepository = peliculaRepository;
        this.criticaRepository = criticaRepository;
        this.generoRepository = generoRepository;
    }

    @PostConstruct
    public void comprobarDatos() {

        transactionTemplate.executeWithoutResult(status -> {
            System.out.println("===== Comprobación A =====");
            List<Pelicula> peliculas = peliculaRepository.findAll();
            peliculas.forEach(p -> System.out.println("Película: " + p.getTitulo()));

            Long primeraId = peliculas.get(0).getId();
            Pelicula primera = peliculaRepository.findById(primeraId).orElseThrow();
            System.out.println("Título: " + primera.getTitulo());

            System.out.println("--- Accediendo a las críticas (LAZY) ---");
            for (Critica c : primera.getCriticas()) {
                System.out.println("  Crítica: " + c.getTexto());
            }

            System.out.println("--- Accediendo a los géneros (LAZY) ---");
            for (Genero g : primera.getGeneros()) {
                System.out.println("  Género: " + g.getNombre());
            }

            System.out.println("===== Comprobación B =====");
            System.out.println("Antes -> críticas: " + criticaRepository.count()
                    + ", géneros: " + generoRepository.count());

            Pelicula conDosCriticas = peliculas.stream()
                    .filter(p -> p.getCriticas().size() == 2)
                    .findFirst().orElseThrow();
            peliculaRepository.delete(conDosCriticas);
            peliculaRepository.flush();

            System.out.println("Después -> críticas: " + criticaRepository.count()
                    + ", géneros: " + generoRepository.count());
        });

    }

}
