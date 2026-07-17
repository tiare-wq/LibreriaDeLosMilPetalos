package com.libreriadelosmilpetalos.lecturas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.libreriadelosmilpetalos.lecturas.entity.Etiqueta;
import com.libreriadelosmilpetalos.lecturas.entity.Libro;

public interface EtiquetaRepository extends JpaRepository<Etiqueta, Long> {

    List<Etiqueta> findByLibro(Libro libro);

}
