package com.esports.tournament_service.clients;

import com.esports.tournament_service.models.dtos.GameResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "game-service", url = "http://localhost:8081")
public interface GameClient {

    @GetMapping("/api/v1/juegos/{id}")
    GameResponseDTO findById(@PathVariable Long id);
}