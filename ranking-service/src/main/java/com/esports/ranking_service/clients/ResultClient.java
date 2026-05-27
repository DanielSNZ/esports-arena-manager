package com.esports.ranking_service.clients;

import com.esports.ranking_service.models.dtos.ResultResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "result-service", url = "http://localhost:8086")
public interface ResultClient {

    @GetMapping("/api/v1/resultados/{id}")
    ResultResponseDTO findById(@PathVariable Long id);
}