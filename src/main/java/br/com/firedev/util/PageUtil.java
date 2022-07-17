package br.com.firedev.util;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtil {

	private PageUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static Pageable validateAndReplaceSorts(Map<String, String> fields, Pageable pageable) {
		var sort = pageable.getSort();
		if (sort.isSorted()) {
			var orders = new ArrayList<Sort.Order>();
			sort.stream().iterator().forEachRemaining(order -> {
				if (fields.containsKey(order.getProperty())) {
					var prop = fields.get(order.getProperty());
					if (prop != null) {
						orders.add(new Sort.Order(order.getDirection(), prop));
					}
				} else {
					orders.add(order);
				}
			});
			sort = Sort.by(orders);
		}
		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
	}
}
