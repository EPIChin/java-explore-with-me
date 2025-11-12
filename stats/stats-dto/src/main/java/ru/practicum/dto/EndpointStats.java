package ru.practicum.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EndpointStats {
    private String app;
    private String uri;
    private long hits;
}