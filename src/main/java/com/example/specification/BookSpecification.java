package com.example.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.example.dto.rest.BookFilterDto;
import com.example.entity.Author_;
import com.example.entity.Book;
import com.example.entity.Book_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

@Component
public class BookSpecification {

    public Specification<Book> filter(BookFilterDto filter) {
        return (root, query, builder) -> {
            final var predicates = List.of(
                likePredicate(builder, root.get(Book_.NAME), filter.getName()),
                likePredicate(builder, root.get(Book_.BRAND), filter.getBrand()),
                equalPredicate(builder, root.get(Book_.COVER), filter.getCover()),
                likePredicate(builder, root.get(Book_.AUTHOR).get(Author_.FIRST_NAME), filter.getAuthorFirstName()),
                likePredicate(builder, root.get(Book_.AUTHOR).get(Author_.LAST_NAME), filter.getAuthorLastName()),
                equalPredicate(builder, root.get(Book_.COUNT), filter.getCount())
            );

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Predicate likePredicate(CriteriaBuilder builder, Expression<String> expression, String value) {
        return value == null ? builder.conjunction() : builder.like(expression, value);
    }

    private Predicate equalPredicate(CriteriaBuilder builder, Expression<?> expression, Object value) {
        return value == null ? builder.conjunction() : builder.equal(expression, value);
    }

}
