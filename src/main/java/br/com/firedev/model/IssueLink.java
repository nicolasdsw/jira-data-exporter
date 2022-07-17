package br.com.firedev.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import br.com.firedev.util.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "issuelink")
@BatchSize(size = 50)
public class IssueLink extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2309353356450529252L;

	@Id
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "source", referencedColumnName = "id")
	private Issue source;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "destination", referencedColumnName = "id")
	private Issue destination;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "linktype", referencedColumnName = "id")
	private LinkType linktype;

	@Override
	public String getDesc() {
		return String.format("%s - %s", this.source == null ? "" : this.source.getDesc(),
				this.destination == null ? "" : this.destination.getDesc());
	}
}
