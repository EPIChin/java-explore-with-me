package ru.practicum.main.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.main.common.State;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class EventEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "annotation", nullable = false, columnDefinition = "text")
    private String annotation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @Column(name = "category_id", nullable = false, insertable = false, updatable = false)
    private Long categoryId;

    @Column(name = "confirmed_requests")
    @Builder.Default
    private Long confirmedRequests = 0L;

    @Column(name = "created_on")
    @Builder.Default
    private LocalDateTime createdOn = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "event_date", nullable = false, columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime eventDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id", nullable = false)
    private UserEntity initiator;

    @Column(name = "initiator_id", nullable = false, insertable = false, updatable = false)
    private Long initiatorId;

    @Embedded
    private Location location;

    @Column(name = "paid", nullable = false)
    private Boolean paid;

    @Column(name = "participant_limit")
    @Builder.Default
    private Long participantLimit = 0L;

    @Column(name = "published_on", columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime publishedOn;

    @Column(name = "request_moderation")
    @Builder.Default
    private Boolean requestModeration = true;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private State state = State.PENDING;

    @Column(name = "title", nullable = false)
    private String title;
}
