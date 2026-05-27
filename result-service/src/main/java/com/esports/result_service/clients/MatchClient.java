package com.esports.result_service.clients;

import com.esports.result_service.models.dtos.MatchResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "match-service", url = "http://localhost:8085")
public interface MatchClient {

    @GetMapping("/api/v1/partidas/{id}")
    MatchResponseDTO findById(@PathVariable Long id);
}
