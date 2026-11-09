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
public class Genero {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
}
