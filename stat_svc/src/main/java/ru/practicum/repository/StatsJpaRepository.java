package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.model.EndpointHit;
import ru.practicum.dto.EndpointStats;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsJpaRepository extends JpaRepository<EndpointHit, Integer> {
    @Query("select new ru.practicum.dto.EndpointStats(hit.app, hit.uri, count(hit.ip)) " +
            "from EndpointHit as hit " +
            "where hit.timestamp between :start and :end " +
            "group by hit.uri, hit.app " +
            "order by count(hit.id) desc")
    List<EndpointStats> getStatsNotUnique(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    @Query("select new ru.practicum.dto.EndpointStats(hit.app, hit.uri, count(DISTINCT hit.ip)) " +
            "from EndpointHit as hit " +
            "where hit.timestamp between :start and :end " +
            "group by hit.uri, hit.app " +
            "order by count(hit.id) desc")
    List<EndpointStats> getStatsUnique(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    @Query("select new ru.practicum.dto.EndpointStats(hit.app, hit.uri, count(hit.ip)) " +
            "from EndpointHit as hit " +
            "where hit.uri in :uris " +
            "and hit.timestamp between :start and :end " +
            "group by hit.uri, hit.app " +
            "order by count(hit.id) desc")
    List<EndpointStats> getStatsNotUniqueWithUris(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("uris") List<String> uris);


    @Query("select new ru.practicum.dto.EndpointStats(hit.app, hit.uri, count(DISTINCT hit.ip)) " +
            "from EndpointHit as hit " +
            "where hit.uri in :uris " +
            "and hit.timestamp between :start and :end " +
            "group by hit.uri, hit.app " +
            "order by count(hit.id) desc")
    List<EndpointStats> getStatsUniqueWithUris(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("uris") List<String> uris);
}