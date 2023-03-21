package ru.practicum.main.model.location;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationDto {
    Float lat;
    Float lon;
}
