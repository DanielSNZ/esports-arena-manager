package com.esports.ranking_service.services;

import com.esports.ranking_service.exceptions.RankingException;
import com.esports.ranking_service.models.Ranking;
import com.esports.ranking_service.models.dtos.RankingDTO;
import com.esports.ranking_service.repositories.RankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.esports.ranking_service.clients.ResultClient;
import com.esports.ranking_service.models.dtos.ResultResponseDTO;

import java.util.List;

@Service
public class RankingServiceImpl implements RankingService {

    @Autowired
    private RankingRepository rankingRepository;

    @Autowired
    private ResultClient resultClient;

    @Override
    public List<Ranking> findAll() {
        return this.rankingRepository.findAll();
    }

    @Override
    public Ranking findById(Long id) {
        return this.rankingRepository.findById(id).orElseThrow(
                () -> new RankingException("Ranking no encontrado")
        );
    }

    @Override
    public List<Ranking> findByEquipoId(Long equipoId) {
        return this.rankingRepository.findByEquipoId(equipoId);
    }

    @Override
    public List<Ranking> findByTorneoId(Long torneoId) {
        return this.rankingRepository.findByTorneoId(torneoId);
    }

    @Override
    public List<Ranking> findByPosicion(Integer posicion) {
        return this.rankingRepository.findByPosicion(posicion);
    }

    @Override
    public Ranking save(RankingDTO rankingDTO) {

        ResultResponseDTO resultado = resultClient.findById(2L);

        if (!resultado.getEstadoValidacion().equals("REGISTRADO")) {
            throw new RankingException("El resultado no está validado");
        }

        Ranking ranking = new Ranking();

        ranking.setEquipoId(rankingDTO.getEquipoId());
        ranking.setTorneoId(rankingDTO.getTorneoId());
        ranking.setPuntos(rankingDTO.getPuntos());
        ranking.setVictorias(rankingDTO.getVictorias());
        ranking.setDerrotas(rankingDTO.getDerrotas());
        ranking.setPosicion(rankingDTO.getPosicion());

        return this.rankingRepository.save(ranking);
    }

    @Override
    public Ranking updateById(Long id, RankingDTO rankingDTO) {

        return this.rankingRepository.findById(id).map(ranking -> {

            ranking.setEquipoId(rankingDTO.getEquipoId());
            ranking.setTorneoId(rankingDTO.getTorneoId());
            ranking.setPuntos(rankingDTO.getPuntos());
            ranking.setVictorias(rankingDTO.getVictorias());
            ranking.setDerrotas(rankingDTO.getDerrotas());
            ranking.setPosicion(rankingDTO.getPosicion());

            return this.rankingRepository.save(ranking);

        }).orElseThrow(
                () -> new RankingException("Ranking no encontrado")
        );
    }

    @Override
    public void deleteById(Long id) {

        if (!this.rankingRepository.existsById(id)) {
            throw new RankingException("Ranking no encontrado");
        }

        this.rankingRepository.deleteById(id);
    }
}