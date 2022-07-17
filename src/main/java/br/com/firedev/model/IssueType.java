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
@Table(name = "issuetype")
@BatchSize(size = 50)
public class IssueType extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 883845536392610341L;

	@Id
	private Long id;

	private String pname;

	@Override
	public String getDesc() {
		return this.pname;
	}
}
