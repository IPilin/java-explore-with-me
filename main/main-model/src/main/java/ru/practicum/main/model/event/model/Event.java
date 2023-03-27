package ru.practicum.main.model.event.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import ru.practicum.main.model.category.Category;
import ru.practicum.main.model.location.Location;
import ru.practicum.main.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    @Length(min = 3, max = 120)
    String title;
    @Column(nullable = false)
    @Length(min = 20, max = 2000)
    String annotation;
    @Column(nullable = false)
    @Length(min = 20, max = 7000)
    String description;
    @Column(name = "created", nullable = false)
    LocalDateTime createdOn;
    @Column(name = "event_date", nullable = false)
    LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    Location location;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    Category category;
    @Column(nullable = false)
    Boolean paid;
    @Column(name = "participant_limit", nullable = false)
    Integer participantLimit;
    @Column(name = "request_moderation", nullable = false)
    Boolean requestModeration;
    @ManyToOne
    @JoinColumn(name = "initiator_id", referencedColumnName = "id")
    User initiator;
    @Enumerated(EnumType.STRING)
    EventState state;
    @Column(name = "published")
    LocalDateTime publishedOn;
    @Transient
    int confirmedRequests;
    @Transient
    int views;
}
