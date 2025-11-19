package ru.practicum.main.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EventRequestStatusUpdateResponseDto {
    private List<EventRequestResponseDto> confirmedRequests;
    private List<EventRequestResponseDto> rejectedRequests;
}
