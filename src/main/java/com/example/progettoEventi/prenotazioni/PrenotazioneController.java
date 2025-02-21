package com.example.progettoEventi.prenotazioni;

import com.example.progettoEventi.auth.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prenotazioni")
@RequiredArgsConstructor
public class PrenotazioneController {
    public final PrenotazioneService prenotazioneService;


    @PostMapping("/{eventoId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    public PrenotazioneResponseId prenotaEvento(@PathVariable Long eventoId, @AuthenticationPrincipal AppUser user) {
       return prenotazioneService.prenotaEvento(eventoId, user);
    }

}
