package ru.practicum.statsserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.statsdto.response.ViewStatsResponseDto;
import ru.practicum.statsserver.entity.EndpointHitEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EndpointHitRepository extends JpaRepository<EndpointHitEntity, Long> {

    @Query("""
        select new ru.practicum.statsdto.response.ViewStatsResponseDto(e.app, e.uri, count(e))
        from EndpointHitEntity e
        where e.timestamp between :start and :end
          and (:uris is null or e.uri in :uris)
        group by e.app, e.uri
        order by count (e) desc""")
    List<ViewStatsResponseDto> findStats(LocalDateTime start, LocalDateTime end, @Param("uris") List<String> uris);

    @Query("""
        select new ru.practicum.statsdto.response.ViewStatsResponseDto(e.app, e.uri, count (distinct e.ip))
        from EndpointHitEntity e
        where e.timestamp between :start and :end
          and (:uris is null or e.uri in :uris)
        group by e.app, e.uri
        order by count(distinct e.ip) desc""")
    List<ViewStatsResponseDto> findStatsUnique(LocalDateTime start, LocalDateTime end, @Param("uris") List<String> uris);
}

