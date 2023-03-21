package ru.practicum.main.model.event.converter;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.main.model.constant.AppConstants;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class EventConverter {
    private final ModelMapper mapper;
    Converter<LocalDateTime, String> dateToString = (timestamp) ->
            (Objects.isNull(timestamp.getSource())) ?
                    null :
                    timestamp.getSource().format(AppConstants.FORMATTER);

    public EventConverter() {
        mapper = new ModelMapper();
        mapper.createTypeMap(LocalDateTime.class, String.class)
                .setConverter(dateToString);
    }

    public <T> T toClass(Object object, Class<T> tClass) {
        return mapper.map(object, tClass);
    }
}
