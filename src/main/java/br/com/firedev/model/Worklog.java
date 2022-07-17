package br.com.firedev.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.JoinFormula;

import br.com.firedev.util.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "worklog")
@BatchSize(size = 50)
public class Worklog extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -23479671255192208L;

	@Id
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "issueid")
	private Issue issue;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinFormula(value = "replace(author, 'JIRAUSER', '')::bigint", referencedColumnName = "id")
	private User author;

	@Formula(value = "(SELECT u.user_name FROM cwd_user u WHERE u.id = (replace(author, 'JIRAUSER', '')::bigint))")
	private String authorUsername;

	@Column(name = "worklogbody")
	private String body;

	private LocalDateTime startdate;

	private Long timeworked;

	@Override
	public String getDesc() {
		return this.body;
	}
}
