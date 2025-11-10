package ru.practicum.dto;

import ru.practicum.model.EndpointHit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EndpointHitMapper {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public static EndpointHitDto toDto(EndpointHit hit) {
        EndpointHitDto hitDto = new EndpointHitDto();
        hitDto.setIp(hit.getIp());
        hitDto.setApp(hit.getApp());
        hitDto.setUri(hit.getUri());
        LocalDateTime hitTimestamp = hit.getTimestamp();
        hitDto.setTimestamp(hitTimestamp.format(TIME_FORMAT));
        return hitDto;
    }

    public static EndpointHit toHit(EndpointHitDto hitDto) {
        EndpointHit hit = new EndpointHit();
        hit.setIp(hitDto.getIp());
        hit.setApp(hitDto.getApp());
        hit.setUri(hitDto.getUri());

        String dtoTimestamp = hitDto.getTimestamp();
        hit.setTimestamp(LocalDateTime.parse(dtoTimestamp, TIME_FORMAT));
        return hit;
    }
}