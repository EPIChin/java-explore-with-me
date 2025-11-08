package ru.practicum.stat_svc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.dto.EndpointStats;
import ru.practicum.model.EndpointHit;
import ru.practicum.repository.StatsJpaRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
@ActiveProfiles("test")
public class StatsJpaRepositoryTest {

    @Autowired
    private StatsJpaRepository repository;

    private final LocalDateTime baseTime = LocalDateTime.of(2025, 11, 7, 13, 0, 0);

    // Тестовые данные
    private EndpointHit hit1, hit2, hit3, hit4, hit5;

    @BeforeEach
    void setUp() {
        hit1 = new EndpointHit();
        hit1.setApp("app1");
        hit1.setUri("/events/1");
        hit1.setIp("192.168.0.1");
        hit1.setTimestamp(baseTime.minusMinutes(30));

        hit2 = new EndpointHit();
        hit2.setApp("app1");
        hit2.setUri("/events/1");
        hit2.setIp("192.168.0.2");
        hit2.setTimestamp(baseTime.minusMinutes(20));

        hit3 = new EndpointHit();
        hit3.setApp("app2");
        hit3.setUri("/events/2");
        hit3.setIp("192.168.0.1");
        hit3.setTimestamp(baseTime.minusMinutes(10));


        hit4 = new EndpointHit();
        hit4.setApp("app1");
        hit4.setUri("/events/3");
        hit4.setIp("192.168.0.3");
        hit4.setTimestamp(baseTime);

        hit5 = new EndpointHit();
        hit5.setApp("app3");
        hit5.setUri("/events/1");
        hit5.setIp("192.168.0.4");
        hit5.setTimestamp(baseTime.plusMinutes(10));


        repository.saveAll(List.of(hit1, hit2, hit3, hit4, hit5));
        repository.flush();
    }

    // --- Тесты для getStatsNotUnique ---
    @Test
    void getStatsNotUnique_shouldReturnAllHitsInRange() {
        LocalDateTime start = baseTime.minusHours(1);
        LocalDateTime end = baseTime.plusHours(1);

        List<EndpointStats> result = repository.getStatsNotUnique(start, end);


        assertEquals(4, result.size());


        // Ищем запись с app="app1", uri="/events/1"
        EndpointStats matched = result.stream()
                .filter(s -> s.getApp().equals("app1") && s.getUri().equals("/events/1"))
                .findFirst()
                .orElse(null);


        assertNotNull(matched);
        assertEquals(2L, matched.getHits());
    }

    @Test
    void getStatsNotUnique_emptyRange_shouldReturnEmpty() {
        LocalDateTime start = baseTime.plusDays(1);
        LocalDateTime end = baseTime.plusDays(2);

        List<EndpointStats> result = repository.getStatsNotUnique(start, end);


        assertTrue(result.isEmpty());
    }

    // --- Тесты для getStatsUnique ---
    @Test
    void getStatsUnique_shouldCountDistinctIps() {
        LocalDateTime start = baseTime.minusHours(1);
        LocalDateTime end = baseTime.plusHours(1);

        List<EndpointStats> result = repository.getStatsUnique(start, end);


        assertEquals(4, result.size());


        EndpointStats matched = result.stream()
                .filter(s -> s.getApp().equals("app1") && s.getUri().equals("/events/1"))
                .findFirst()
                .orElse(null);

        assertNotNull(matched);
        assertEquals(2L, matched.getHits());  // 2 уникальных IP
    }

    @Test
    void getStatsUnique_sameIpDifferentUris_shouldCountSeparately() {
        LocalDateTime start = baseTime.minusHours(1);
        LocalDateTime end = baseTime.plusHours(1);
        List<EndpointStats> result = repository.getStatsUnique(start, end);

        assertThat(
                result.stream()
                        .filter(s -> s.getUri().equals("/events/1"))
                        .findFirst().orElseThrow().getHits(),
                equalTo(2L)
        );
        assertThat(
                result.stream()
                        .filter(s -> s.getUri().equals("/events/2"))
                        .findFirst().orElseThrow().getHits(),
                equalTo(1L)
        );
    }

    // --- Тесты для getStatsNotUniqueWithUris ---
    @Test
    void getStatsNotUniqueWithUris_filteredByUris_shouldReturnOnlyMatched() {
        LocalDateTime start = baseTime.minusHours(1);
        LocalDateTime end = baseTime.plusHours(1);
        List<String> uris = List.of("/events/1", "/events/3");

        List<EndpointStats> result = repository.getStatsNotUniqueWithUris(start, end, uris);

        assertEquals(3, result.size());
        assertTrue(result.stream().allMatch(s -> uris.contains(s.getUri())));

        // Дополнительно проверяем hits для /events/1
        EndpointStats event1Stats = result.stream()
                .filter(s -> s.getUri().equals("/events/1"))
                .findFirst()
                .orElse(null);
        assertNotNull(event1Stats);
        assertEquals(2L, event1Stats.getHits());  // hit1, hit2, hit5
    }

    @Test
    void getStatsNotUniqueWithUris_noMatchingUris_shouldReturnEmpty() {
        LocalDateTime start = baseTime.minusHours(1);
        LocalDateTime end = baseTime.plusHours(1);
        List<String> uris = List.of("/nonexistent");

        List<EndpointStats> result = repository.getStatsNotUniqueWithUris(start, end, uris);
        assertTrue(result.isEmpty());
    }

    // --- Тесты для getStatsUniqueWithUris ---
    @Test
    void getStatsUniqueWithUris_distinctIpsPerUri_shouldReturnCorrectCounts() {
        LocalDateTime start = baseTime.minusHours(1);
        LocalDateTime end = baseTime.plusHours(1);
        List<String> uris = List.of("/events/1", "/events/2");

        List<EndpointStats> result = repository.getStatsUniqueWithUris(start, end, uris);

        assertEquals(3, result.size());

        assertThat(
                result.stream()
                        .filter(s -> s.getUri().equals("/events/1"))
                        .findFirst().orElseThrow().getHits(),
                equalTo(2L)  // IP: 192.168.0.1 (hit1), 192.168.0.2 (hit2), 192.168.0.4 (hit5)
        );
        assertThat(
                result.stream()
                        .filter(s -> s.getUri().equals("/events/2"))
                        .findFirst().orElseThrow().getHits(),
                equalTo(1L)  // IP: 192.168.0.1 (hit3)
        );
    }

    @Test
    void getStatsUniqueWithUris_emptyResult_whenNoHitsInRange() {
        LocalDateTime start = baseTime.plusDays(1);
        LocalDateTime end = baseTime.plusDays(2);
        List<String> uris = List.of("/events/1");

        List<EndpointStats> result = repository.getStatsUniqueWithUris(start, end, uris);
        assertTrue(result.isEmpty());
    }
}