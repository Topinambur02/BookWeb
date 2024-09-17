package com.example.wrapper;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class ArrayListWrapper {

    public static class Builder {

        private List<Predicate> predicates;

        public Builder predicates(List<Predicate> predicates) {
            this.predicates = predicates;
            return this;
        }

        public List<Predicate> build() {
            return new ArrayList<Predicate>();
        }

    }

    public static Builder builder() {
        return new Builder();
    }
}
