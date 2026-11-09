package com.salesianostriana.dam.spring.data.jpa.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Critica {

    @Id
    @GeneratedValue
    private Long id;

    private String autor;

    private String texto;

    private Integer puntuacion;

    @ManyToOne
    @JoinColumn(name = "pelicula_id")
    @ToString.Exclude
    private Pelicula pelicula;
}
