package ru.practicum.main.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.practicum.main.common.Status;

import java.time.LocalDateTime;

@Data
@Builder
public class EventRequestResponseDto {
    private Long id;
    private Long requester;
    private Long event;
    private Status status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
}
