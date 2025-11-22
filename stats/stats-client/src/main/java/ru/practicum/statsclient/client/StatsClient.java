package ru.practicum.statsclient.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.statsdto.request.EndpointHitRequestDto;
import ru.practicum.statsdto.response.ViewStatsResponseDto;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${stats.base-url}")
    private String baseUrl;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void hit(EndpointHitRequestDto dto) {
        restTemplate.postForEntity(baseUrl + "/hit", dto, Void.class);
    }

    public List<ViewStatsResponseDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        String startEnc = URLEncoder.encode(FORMATTER.format(start), StandardCharsets.UTF_8);
        String endEnc = URLEncoder.encode(FORMATTER.format(end), StandardCharsets.UTF_8);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/stats")
            .queryParam("start", startEnc)
            .queryParam("end", endEnc)
            .queryParam("unique", unique);

        if (uris != null && !uris.isEmpty()) {
            for (String uri : uris) {
                builder.queryParam("uris", uri);
            }
        }

        URI uri = builder.build(true).toUri();
        ResponseEntity<ViewStatsResponseDto[]> response = restTemplate.getForEntity(uri, ViewStatsResponseDto[].class);
        return Arrays.asList(response.getBody() == null ? new ViewStatsResponseDto[]{} : response.getBody());
    }
}
