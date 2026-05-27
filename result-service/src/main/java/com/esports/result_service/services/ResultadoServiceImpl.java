package com.esports.result_service.services;

import com.esports.result_service.exceptions.ResultadoException;
import com.esports.result_service.models.Resultado;
import com.esports.result_service.models.dtos.ResultadoDTO;
import com.esports.result_service.repositories.ResultadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.esports.result_service.clients.MatchClient;
import com.esports.result_service.models.dtos.MatchResponseDTO;

import java.util.List;

@Service
public class ResultadoServiceImpl implements ResultadoService {

    @Autowired
    private ResultadoRepository resultadoRepository;

    @Autowired
    private MatchClient matchClient;

    @Override
    public List<Resultado> findAll() {
        return this.resultadoRepository.findAll();
    }

    @Override
    public Resultado findById(Long id) {
        return this.resultadoRepository.findById(id).orElseThrow(
                () -> new ResultadoException("Resultado no encontrado")
        );
    }

    @Override
    public List<Resultado> findByPartidaId(Long partidaId) {
        return this.resultadoRepository.findByPartidaId(partidaId);
    }

    @Override
    public List<Resultado> findByGanadorId(Long ganadorId) {
        return this.resultadoRepository.findByGanadorId(ganadorId);
    }

    @Override
    public List<Resultado> findByEstadoValidacion(String estadoValidacion) {
        return this.resultadoRepository.findByEstadoValidacion(estadoValidacion);
    }

    @Override
    public Resultado save(ResultadoDTO resultadoDTO) {

        MatchResponseDTO partida = matchClient.findById(resultadoDTO.getPartidaId());

        if (!partida.getEstado().equals("FINALIZADA")) {
            throw new ResultadoException("La partida aún no está finalizada");
        }

        Resultado resultado = new Resultado();

        resultado.setPartidaId(resultadoDTO.getPartidaId());
        resultado.setGanadorId(resultadoDTO.getGanadorId());
        resultado.setPuntajeA(resultadoDTO.getPuntajeA());
        resultado.setPuntajeB(resultadoDTO.getPuntajeB());
        resultado.setEstadoValidacion(resultadoDTO.getEstadoValidacion());
        resultado.setFechaRegistro(resultadoDTO.getFechaRegistro());

        return this.resultadoRepository.save(resultado);
    }

    @Override
    public Resultado updateById(Long id, ResultadoDTO resultadoDTO) {

        return this.resultadoRepository.findById(id).map(resultado -> {

            resultado.setPartidaId(resultadoDTO.getPartidaId());
            resultado.setGanadorId(resultadoDTO.getGanadorId());
            resultado.setPuntajeA(resultadoDTO.getPuntajeA());
            resultado.setPuntajeB(resultadoDTO.getPuntajeB());
            resultado.setEstadoValidacion(resultadoDTO.getEstadoValidacion());
            resultado.setFechaRegistro(resultadoDTO.getFechaRegistro());

            return this.resultadoRepository.save(resultado);

        }).orElseThrow(
                () -> new ResultadoException("Resultado no encontrado")
        );
    }

    @Override
    public void deleteById(Long id) {

        if (!this.resultadoRepository.existsById(id)) {
            throw new ResultadoException("Resultado no encontrado");
        }

        this.resultadoRepository.deleteById(id);
    }
}