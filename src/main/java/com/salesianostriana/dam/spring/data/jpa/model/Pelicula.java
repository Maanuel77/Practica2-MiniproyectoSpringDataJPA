package com.salesianostriana.dam.spring.data.jpa.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Pelicula {

    @Id
    @GeneratedValue
    private Long id;

    private String titulo;

    private Integer anioEstreno;

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<Critica> criticas = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "pelicula_genero",
            joinColumns = @JoinColumn(name = "pelicula_id"),
            inverseJoinColumns = @JoinColumn(name = "genero_id"))
    @Builder.Default
    @ToString.Exclude
    private Set<Genero> generos = new HashSet<>();

    public void addCritica(Critica critica) {
        criticas.add(critica);
        critica.setPelicula(this);
    }

    public void removeCritica(Critica critica) {
        criticas.remove(critica);
        critica.setPelicula(null);
    }

    public void addGenero(Genero genero) {
        generos.add(genero);
    }
}
