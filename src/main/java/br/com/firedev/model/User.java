package br.com.firedev.model;

import javax.persistence.Column;
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
@Table(name = "cwd_user")
@BatchSize(size = 50)
public class User extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1997620929750397584L;

	@Id
	private Long id;

	@Column(name = "user_name")
	private String username;

	@Column(name = "email_address")
	private String email;

	private boolean active;

	@Override
	public String getDesc() {
		return this.username;
	}
}