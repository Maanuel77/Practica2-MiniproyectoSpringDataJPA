package com.salesianostriana.dam.spring.data.jpa.data;

import com.salesianostriana.dam.spring.data.jpa.model.Critica;
import com.salesianostriana.dam.spring.data.jpa.model.Genero;
import com.salesianostriana.dam.spring.data.jpa.model.Pelicula;
import com.salesianostriana.dam.spring.data.jpa.repository.GeneroRepository;
import com.salesianostriana.dam.spring.data.jpa.repository.PeliculaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class DataSeed {

    private final TransactionTemplate transactionTemplate;
    private final PeliculaRepository peliculaRepository;
    private final GeneroRepository generoRepository;

    public DataSeed(PlatformTransactionManager transactionManager,
                    PeliculaRepository peliculaRepository,
                    GeneroRepository generoRepository) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.peliculaRepository = peliculaRepository;
        this.generoRepository = generoRepository;
    }

    @PostConstruct
    public void cargarDatos() {

        transactionTemplate.executeWithoutResult(status -> {
            Genero g1 = generoRepository.save(Genero.builder().nombre("Accion").build());
            Genero g2 = generoRepository.save(Genero.builder().nombre("Drama").build());

            Pelicula p1 = Pelicula.builder().titulo("Los vengadores").anioEstreno(2012).build();
            p1.addGenero(g1);
            p1.addGenero(g2);
            p1.addCritica(Critica.builder().autor("Angel Naranjo").texto("Muy buena pelicula muy recomendada").puntuacion(8).build());
            p1.addCritica(Critica.builder().autor("Luis Miguel Lopez").texto("Falta un poco de java pero igualmente bastante recomendada").puntuacion(6).build());

            Pelicula p2 = Pelicula.builder().titulo("El laberinto del fauno").anioEstreno(2021).build();
            p2.addGenero(g2);
            p2.addCritica(Critica.builder().autor("Jesus Casanova").texto("Demasiado dialogo creado con IA generativa").puntuacion(7).build());

            peliculaRepository.save(p1);
            peliculaRepository.save(p2);
        });

    }

}
