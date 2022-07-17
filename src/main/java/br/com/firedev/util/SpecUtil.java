package br.com.firedev.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import br.com.firedev.dto.RangeDTO;

public class SpecUtil {

	private SpecUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static Predicate toAndArray(CriteriaBuilder builder, Collection<Predicate> predicates) {
		predicates.removeIf(Objects::isNull);
		if (predicates.isEmpty()) {
			return null;
		}
		return builder.and(predicates.toArray(new Predicate[predicates.size()]));
	}

	public static Predicate toOrArray(CriteriaBuilder builder, Collection<Predicate> predicates) {
		predicates.removeIf(Objects::isNull);
		if (predicates.isEmpty()) {
			return null;
		}
		return builder.or(predicates.toArray(new Predicate[predicates.size()]));
	}

	public static Expression<String> concatAll(CriteriaBuilder builder, Expression<?>... x) {
		var result = x[0].as(String.class);
		for (var i = 1; i < x.length; i++) {
			result = builder.concat(result, x[i].as(String.class));
		}
		return result;
	}

	public static <F> Predicate isEqual(CriteriaBuilder builder, Expression<?> path, F filter) {
		return filter == null ? null : builder.equal(path, filter);
	}

	public static Predicate like(CriteriaBuilder builder, Expression<?> path, String filter) {
		if (filter == null || filter.isBlank()) {
			return null;
		}
		var filterValue = filter.replace("*", "%").trim().toUpperCase();
		return builder.like(builder.upper(builder.trim(path.as(String.class))), filterValue);
	}

	public static Predicate contains(CriteriaBuilder builder, Expression<?> path, String filter) {
		return filter == null ? null : like(builder, path, "%" + filter.trim() + "%");
	}

	public static Predicate isIn(Path<Object> path, Collection<?> values) {
		return values == null || values.isEmpty() ? null : path.in(values);
	}

	public static Predicate isBetween(CriteriaBuilder builder, Expression<LocalDateTime> path, RangeDTO range) {
		if (range == null) {
			return null;
		}
		var predicates = new ArrayList<Predicate>();
		if (range.getStart() != null) {
			predicates.add(builder.greaterThanOrEqualTo(path, range.getStart().atStartOfDay()));
		}
		if (range.getEnd() != null) {
			predicates.add(builder.lessThanOrEqualTo(path, range.getEnd().atTime(LocalTime.MAX)));
		}
		return builder.and(predicates.toArray(new Predicate[predicates.size()]));
	}
}
