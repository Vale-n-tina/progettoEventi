package com.example.progettoEventi.eventi;

import com.example.progettoEventi.auth.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eventi")
@RequiredArgsConstructor
public class EventoController {
    private final EventoService eventoService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ORGANIZZATORE')")
    public EventoResponseId create(@RequestBody EventoRequest request, @AuthenticationPrincipal AppUser user) {
        return eventoService.create(request, user);
    }

}
