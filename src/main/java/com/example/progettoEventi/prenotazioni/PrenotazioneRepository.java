package com.example.progettoEventi.prenotazioni;


import com.example.progettoEventi.auth.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByUser(AppUser user);
}
