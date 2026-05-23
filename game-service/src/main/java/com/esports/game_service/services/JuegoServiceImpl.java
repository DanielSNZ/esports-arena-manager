package com.esports.game_service.services;

import com.esports.game_service.exceptions.JuegoException;
import com.esports.game_service.models.Juego;
import com.esports.game_service.models.dtos.JuegoDTO;
import com.esports.game_service.repositories.JuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JuegoServiceImpl implements JuegoService {

    @Autowired
    private JuegoRepository juegoRepository;

    @Override
    public List<Juego> findAll() {
        return this.juegoRepository.findAll();
    }

    @Override
    public Juego findById(Long id) {

        return this.juegoRepository.findById(id).orElseThrow(
                () -> new JuegoException("Juego no encontrado")
        );
    }

    @Override
    public Juego save(JuegoDTO juegoDTO) {

        Juego juego = new Juego();

        juego.setNombre(juegoDTO.getNombre());
        juego.setGenero(juegoDTO.getGenero());
        juego.setModalidad(juegoDTO.getModalidad());
        juego.setJugadoresPorEquipo(juegoDTO.getJugadoresPorEquipo());
        juego.setEstado(juegoDTO.getEstado());

        return this.juegoRepository.save(juego);
    }

    @Override
    public void deleteById(Long id) {

        if (!this.juegoRepository.existsById(id)) {
            throw new JuegoException("Juego no encontrado");
        }

        this.juegoRepository.deleteById(id);
    }

    @Override
    public Juego updateById(Long id, JuegoDTO juegoDTO) {

        return this.juegoRepository.findById(id).map(element -> {

            element.setNombre(juegoDTO.getNombre());
            element.setGenero(juegoDTO.getGenero());
            element.setModalidad(juegoDTO.getModalidad());
            element.setJugadoresPorEquipo(juegoDTO.getJugadoresPorEquipo());
            element.setEstado(juegoDTO.getEstado());

            return this.juegoRepository.save(element);

        }).orElseThrow(
                () -> new JuegoException("Juego no encontrado")
        );
    }
}