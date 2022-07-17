package br.com.firedev.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import br.com.firedev.util.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "issuelinktype")
@BatchSize(size = 50)
public class LinkType extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6277927713563413558L;

	@Id
	private Long id;

	private String linkname;

	@Override
	public String getDesc() {
		return this.linkname;
	}
}
