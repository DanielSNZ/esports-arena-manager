package com.esports.match_service.clients;

import com.esports.match_service.models.dtos.TeamResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "team-service", url = "http://localhost:8084")
public interface TeamClient {

    @GetMapping("/api/v1/equipos/{id}")
    TeamResponseDTO findById(@PathVariable Long id);
}