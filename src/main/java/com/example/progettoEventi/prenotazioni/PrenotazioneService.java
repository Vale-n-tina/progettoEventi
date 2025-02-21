package com.example.progettoEventi.prenotazioni;

import com.example.progettoEventi.auth.AppUser;
import com.example.progettoEventi.auth.JwtTokenMissingException;
import com.example.progettoEventi.eventi.Evento;
import com.example.progettoEventi.eventi.EventoRepository;
import com.example.progettoEventi.exception.NoPostiDisponibiliException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

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
            throw new NoPostiDisponibiliException("Posti esauriti per questo evento");
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

    public void delete(Long prenotazioneId, AppUser appUser) {
        if(!prenotazioneRepository.existsById(prenotazioneId)){
            throw new EntityNotFoundException("Prenotazione non trovata");
        }
        if(!prenotazioneRepository.findById(prenotazioneId).get().getUser().equals(appUser)){
            throw new JwtTokenMissingException("Accesso non consentito, non puoi eliminare una prenotazione che non appartiene a te");
        }
        prenotazioneRepository.deleteById(prenotazioneId);
    }

    public List<PrenotazioneResponse>findByUserId(AppUser user) {
       List<Prenotazione> prenotazioni = prenotazioneRepository.findByUser(user);
       return prenotazioni.stream().map(prenotazione->{
           PrenotazioneResponse resp = new PrenotazioneResponse();
           resp.setId(prenotazione.getId());
           resp.setTitolo(prenotazione.getEvento().getTitolo());
           resp.setData(prenotazione.getEvento().getData());
           resp.setLuogo(prenotazione.getEvento().getLuogo());
           return resp;
       }).collect(Collectors.toList());
    }
}
