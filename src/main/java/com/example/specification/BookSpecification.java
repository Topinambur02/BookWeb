package com.example.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.example.entity.Author_;
import com.example.entity.Book;
import com.example.entity.Book_;
import com.example.filter.BookFilter;
import com.example.wrapper.ArrayListWrapper;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

@Component
public class BookSpecification {

    public Specification<Book> filter(BookFilter filter) {
        return (root, query, builder) -> {
            final var list = List.of(
                like(builder, root.get(Book_.NAME), filter.getName()),
                like(builder, root.get(Book_.BRAND), filter.getBrand()),
                equal(builder, root.get(Book_.COVER), filter.getCover()),
                like(builder, root.get(Book_.AUTHOR).get(Author_.FIRST_NAME), filter.getAuthorFirstName()),
                like(builder, root.get(Book_.AUTHOR).get(Author_.LAST_NAME), filter.getAuthorLastName()),
                equal(builder, root.get(Book_.COUNT), filter.getCount())
            );

            final var predicates = ArrayListWrapper.builder().predicates(list).build();

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Predicate like(CriteriaBuilder builder, Expression<String> expression, String value) {
        return value == null ? builder.conjunction() : builder.like(expression, value);
    }

    private Predicate equal(CriteriaBuilder builder, Expression<?> expression, Object value) {
        return value == null ? builder.conjunction() : builder.equal(expression, value);
    }

}
