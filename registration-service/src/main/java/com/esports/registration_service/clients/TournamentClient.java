package com.esports.registration_service.clients;

import com.esports.registration_service.models.dtos.TournamentResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "tournament-service", url = "http://localhost:8083")
public interface TournamentClient {

    @GetMapping("/api/v1/torneos/{id}")
    TournamentResponseDTO findById(@PathVariable Long id);
}
