package com.example.progettoEventi.eventi;

import com.example.progettoEventi.auth.AppUser;
import jakarta.persistence.EntityExistsException;
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



}
