package com.example.progettoEventi.eventi;


import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    boolean existsByTitoloIgnoreCase(String titolo);
}
