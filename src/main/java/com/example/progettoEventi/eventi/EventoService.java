package com.example.progettoEventi.eventi;

import com.example.progettoEventi.auth.AppUser;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Validated
@ControllerAdvice
@Service
@RequiredArgsConstructor
public class EventoService {
    private final EventoRepository eventoRepository;

    public EventoResponseId create(@Valid EventoRequest request, AppUser appUser) {

        if (eventoRepository.existsByTitoloIgnoreCase(request.getTitolo())) {
            throw new EntityExistsException("Esiste già un evento con lo stesso titolo");
        }
        Evento evento = new Evento();
        BeanUtils.copyProperties(request, evento);
        evento.setIdOrganizzatore(appUser.getId());
        eventoRepository.save(evento);
        EventoResponseId responseId = new EventoResponseId();
        BeanUtils.copyProperties(evento, responseId);
        return responseId;

    }

  public EventoResponseComplete update(@Valid EventoRequest request, Long id, AppUser appUser) {
        if(!eventoRepository.existsById(id)) {
            throw new EntityNotFoundException("L'evento non esiste");
        }
        Evento evento = eventoRepository.findById(id).get();
        if (!evento.getIdOrganizzatore().equals(appUser.getId())) {
            throw new IllegalArgumentException("Non hai i permessi per modificare questo evento");
        }
        BeanUtils.copyProperties(request, evento);
        eventoRepository.save(evento);
        EventoResponseComplete response = new EventoResponseComplete();
        BeanUtils.copyProperties(evento, response);
        return response;
  }
  public void delete(Long id, AppUser appUser) {
        if(!eventoRepository.existsById(id)) {
            throw new EntityNotFoundException("L'evento non esiste");
        }
        Evento evento = eventoRepository.findById(id).get();
        if (!evento.getIdOrganizzatore().equals(appUser.getId())) {
            throw new IllegalArgumentException("Non hai i permessi per eliminare questo evento");
        }
        eventoRepository.deleteById(id);
  }

}
