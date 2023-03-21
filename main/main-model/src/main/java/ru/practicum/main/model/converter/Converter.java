package ru.practicum.main.model.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.main.model.constant.AppConstants;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class Converter {
    private final ModelMapper mapper;
    org.modelmapper.Converter<LocalDateTime, String> dateToString = (timestamp) ->
            (Objects.isNull(timestamp.getSource())) ?
                    null :
                    timestamp.getSource().format(AppConstants.FORMATTER);

    public Converter() {
        mapper = new ModelMapper();
        mapper.createTypeMap(LocalDateTime.class, String.class)
                .setConverter(dateToString);
    }

    public <T> T toClass(Object object, Class<T> tClass) {
        return mapper.map(object, tClass);
    }
    
    public <T> Collection<T> toClassCollection(Collection<?> objects, Class<T> tClass) {
        return objects.stream().map((object) -> mapper.map(object, tClass)).collect(Collectors.toList());
    }
}
