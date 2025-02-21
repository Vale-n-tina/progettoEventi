package com.example.progettoEventi.prenotazioni;

import com.example.progettoEventi.auth.AppUser;
import com.example.progettoEventi.eventi.Evento;
import com.example.progettoEventi.eventi.EventoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Validated
@ControllerAdvice
@Service
@RequiredArgsConstructor
public class PrenotazioneService {
    private final PrenotazioneRepository prenotazioneRepository;
    private final EventoRepository eventoRepository;

    public PrenotazioneResponseId prenotaEvento(Long eventoId, AppUser appUser) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));
        if (!evento.hasPostiDisponibili()) {
            throw new IllegalStateException("Posti esauriti per questo evento");
        }
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setEvento(evento);
        prenotazione.setUser(appUser);
        evento.getPrenotazioni().add(prenotazione);
        prenotazioneRepository.save(prenotazione);
        eventoRepository.save(evento);
        PrenotazioneResponseId prenotazioneResponseId = new PrenotazioneResponseId();
        prenotazioneResponseId.setId(prenotazione.getId());
        return prenotazioneResponseId;

    }
}
