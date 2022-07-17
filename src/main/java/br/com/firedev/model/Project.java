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
@Table(name = "project")
@BatchSize(size = 50)
public class Project extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8601330097008234377L;

	@Id
	private Long id;

	private String pname;

	private String pkey;

	@Override
	public String getDesc() {
		return this.pkey;
	}
}