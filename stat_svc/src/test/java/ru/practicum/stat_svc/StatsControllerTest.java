package ru.practicum.stat_svc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.EndpointStats;
import ru.practicum.dto.RequestParamDto;
import ru.practicum.service.StatsService;


import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatsService statsService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Утилита: конвертация объекта в JSON
    private String asJsonString(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    // Тест 1: успешный POST /hit
    @Test
    void postHit_shouldReturnCreated() throws Exception {
        EndpointHitDto hitDto = new EndpointHitDto();
        hitDto.setApp("testApp");
        hitDto.setUri("/test");
        hitDto.setIp("192.168.0.1");
        hitDto.setTimestamp("2025-11-07 15:00:00");

        // Верификация по полям (не по ссылке)
        doNothing().when(statsService).saveHit(argThat(dto ->
                dto.getApp().equals(hitDto.getApp()) &&
                        dto.getUri().equals(hitDto.getUri())
        ));

        mockMvc.perform(post("/hit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(hitDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Информация сохранена"));

        verify(statsService, times(1)).saveHit(any(EndpointHitDto.class));
    }

    // Тест 2: валидация DTO — пустой app
    @Test
    void postHit_withEmptyApp_shouldReturnBadRequest() throws Exception {
        EndpointHitDto invalidDto = new EndpointHitDto();
        invalidDto.setApp(""); // Нарушение @NotBlank
        invalidDto.setUri("/test");
        invalidDto.setIp("192.168.0.1");
        invalidDto.setTimestamp("2025-11-07 15:00:00");

        mockMvc.perform(post("/hit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    // Тест 3: GET /stats с параметрами
    @Test
    void getStats_shouldReturnStats() throws Exception {
        List<EndpointStats> stats = List.of(
                new EndpointStats("testApp", "/test", 5L)
        );

        RequestParamDto requestDto = new RequestParamDto(
                "2025-11-07 00:00:00",
                "2025-11-07 23:59:59",
                new String[]{"/test"},
                false
        );

        when(statsService.getStats(requestDto)).thenReturn(stats);


        mockMvc.perform(get("/stats")
                        .param("start", "2025-11-07 00:00:00")
                        .param("end", "2025-11-07 23:59:59")
                        .param("uris", "/test")
                        .param("unique", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].app").value("testApp"))
                .andExpect(jsonPath("$[0].uri").value("/test"))
                .andExpect(jsonPath("$[0].hits").value(5));


        verify(statsService, times(1)).getStats(requestDto);
    }

    // Тест 4: GET /stats без uris (optional)
    @Test
    void getStats_withoutUris_shouldReturnStats() throws Exception {
        List<EndpointStats> stats = List.of(
                new EndpointStats("app1", "/api/1", 3L),
                new EndpointStats("app2", "/api/2", 7L)
        );

        RequestParamDto requestDto = new RequestParamDto(
                "2025-11-07 00:00:00",
                "2025-11-07 23:59:59",
                null,
                false
        );

        when(statsService.getStats(argThat(dto ->
                dto.getStart().equals(requestDto.getStart()) &&
                        dto.getEnd().equals(requestDto.getEnd()) &&
                        Arrays.equals(dto.getUris(), requestDto.getUris()) &&
                        dto.isUnique() == requestDto.isUnique()
        ))).thenReturn(stats);

        mockMvc.perform(get("/stats")
                        .param("start", "2025-11-07 00:00:00")
                        .param("end", "2025-11-07 23:59:59")
                        .param("unique", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].app").value("app1"))
                .andExpect(jsonPath("$[1].hits").value(7));


        verify(statsService, times(1)).getStats(any());
    }
}