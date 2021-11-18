package com.baufest.tennis.springtennis.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.leo.tennis.model.Jugador;
import com.leo.tennis.repository.JugadorRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class JugadorRepositoryTest {

    private static final Jugador JUGADOR1 = new Jugador(null, "A", 2, null, 0);
    private static final Jugador JUGADOR2 = new Jugador(null, "A", 1, null, 0);
    private static final Jugador JUGADOR3 = new Jugador(null, "B", 3, null, 0);//ese null ultimo ,es de entrenador falta declarar Y GANANCIA

    @Autowired
    private JugadorRepository repository;

    @Test
    public void findAllByOrderByNombreAsc() {
        List<Jugador> jugadoresExpected = new ArrayList<>();
        jugadoresExpected.add(JUGADOR2);
        jugadoresExpected.add(JUGADOR3);
        jugadoresExpected.add(JUGADOR1);

        repository.save(JUGADOR1);
        repository.save(JUGADOR2);
        repository.save(JUGADOR3);

        List<Jugador> judadores = repository.findAllByOrderByNombreAsc();
        assertNotNull(judadores);
        assertEquals(jugadoresExpected,judadores);
    }

}
