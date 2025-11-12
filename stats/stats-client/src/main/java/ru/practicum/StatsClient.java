package ru.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.practicum.dto.EndpointHitDto;


import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.exceptions.StatsClientException;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatsClient {

    private final RestTemplate restTemplate;
    private final StatsClientProperties properties;


    public void postHit(HttpServletRequest request, String app) {
        if (!properties.isEnabled()) {
            log.debug("Stats client is disabled. Skipping hit.");
            return;
        }

        EndpointHitDto hitDto = EndpointHitDto.builder()
                .app(app)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(String.valueOf(request.getDateHeader("Date"))) // System.currentTimeMillis()
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<EndpointHitDto> entity = new HttpEntity<>(hitDto, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    properties.getBaseUrl() + "/hit",
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Hit успешно отправлен в сервис статистики: {}", hitDto);
            } else {
                log.warn("Ошибка при отправке hit в сервис статистики. Статус: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Ошибка при взаимодействии с сервисом статистики: {}", e.getMessage(), e);
            throw new StatsClientException("Не удалось отправить hit в сервис статистики", e);
        }
    }
}