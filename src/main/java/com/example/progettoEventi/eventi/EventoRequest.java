package com.example.progettoEventi.eventi;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoRequest {
    @NotBlank(message = "Il titolo non può essere vuoto")
    private String titolo;
    @NotBlank(message = "La descrizione non può essere vuota")
    private String descrizione;
    @Future(message = "La data non può essere nel passato")
    private Date data;
    @NotBlank(message = "Il luogo non può essere vuoto")
    private String luogo;
    private int postiDisponibili;

}
