package ru.practicum.main.model.event.filter;

import org.springframework.data.jpa.domain.Specification;
import ru.practicum.main.model.event.model.EventState;
import ru.practicum.main.model.event.model.Event;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;


public class EventSpecification implements Specification<Event> {
    private final EventFilter filter;

    public EventSpecification(EventFilter filter) {
        super();
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        var p = cb.conjunction();

        p.getExpressions()
                .add(cb.equal(root.get("state"), EventState.PUBLISHED));

        if (filter.getText() != null) {
            p.getExpressions()
                    .add(cb.or(cb.like(cb.lower(root.get("annotation")), "%" + filter.getText().toLowerCase() + "%"),
                            cb.like(cb.lower(root.get("description")), "%" + filter.getText().toLowerCase() + "%")));
        }

        if (filter.getUsers() != null) {
            p.getExpressions()
                    .add(root.join("initiator").get("id").in(filter.getUsers()));
        }

        if (filter.getStates() != null) {
            p.getExpressions()
                    .add(root.get("state").in(filter.getStates()));
        }

        if (filter.getCategories() != null) {
            p.getExpressions()
                    .add(root.join("category").get("id").in(filter.getCategories()));
        }

        if (filter.getPaid() != null) {
            p.getExpressions()
                    .add(cb.equal(root.get("paid"), filter.getPaid()));
        }

        if (filter.getRangeStart() == null) {
            p.getExpressions()
                    .add(cb.greaterThan(root.get("eventDate"), LocalDateTime.now()));
        } else {
            p.getExpressions()
                    .add(cb.between(root.get("eventDate"), filter.getRangeStart(), filter.getRangeEnd()));
        }

        if (filter.getSort() != null) {
            switch (filter.getSort()) {
                case EVENT_DATE:
                    cq.orderBy(cb.desc(root.get("eventDate")));
                    break;
            }
        }

        return p;
    }
}
