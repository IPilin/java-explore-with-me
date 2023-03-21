package ru.practicum.main.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main.model.location.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
