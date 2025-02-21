package com.example.progettoEventi.eventi;

import com.example.progettoEventi.prenotazioni.Prenotazione;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "eventi")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String titolo;
    private String descrizione;
    private Date data;
    private String luogo;
    private int postiDisponibili;
    private Long idOrganizzatore;
    @OneToMany(mappedBy = "evento", fetch = FetchType.LAZY)
    private List<Prenotazione> prenotazioni;

    public int getNumeroPrenotazioni() {
        return prenotazioni != null ? prenotazioni.size() : 0;
    }

    public boolean hasPostiDisponibili() {
        return getNumeroPrenotazioni()<postiDisponibili;
    }

}
