package ru.practicum.main.model.request.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.main.model.event.model.Event;
import ru.practicum.main.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    LocalDateTime created;
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    Event event;
    @ManyToOne
    @JoinColumn(name = "requester_id", referencedColumnName = "id")
    User requester;
    @Enumerated(EnumType.STRING)
    RequestState status;
}
