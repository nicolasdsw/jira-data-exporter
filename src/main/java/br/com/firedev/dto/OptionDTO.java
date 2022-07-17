package br.com.firedev.dto;

import java.io.Serializable;

import br.com.firedev.util.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionDTO<I extends Serializable> implements Serializable {

	private static final long serialVersionUID = -914698292833381449L;
	private I key;
	private String value;

	public static <I extends Serializable> OptionDTO<I> of(BaseEntity<I> entity) {
		if (entity == null || entity.getId() == null) {
			return null;
		}
		return new OptionDTO<>(entity.getId(), entity.getDesc());
	}
}
