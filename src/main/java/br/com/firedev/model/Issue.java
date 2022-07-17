package br.com.firedev.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.JoinFormula;

import br.com.firedev.util.BaseEntity;
import br.com.firedev.util.SystemUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "jiraissue")
@BatchSize(size = 50)
public class Issue extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1699592020989601282L;

	@Id
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project")
	private Project project;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinFormula(value = "replace(assignee, 'JIRAUSER', '')::bigint", referencedColumnName = "id")
	private User assignee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "issuetype", referencedColumnName = "id")
	private IssueType issuetype;

	private Long issuenum;

	private String summary;

	private String description;

	private LocalDateTime created;

	private LocalDateTime duedate;

	private Long timeoriginalestimate;

	private Long timespent;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "source")
	private List<IssueLink> children;

	@Override
	public String getDesc() {
		return (this.project == null ? "" : this.project.getPkey()) + "-" + this.issuenum;
	}

	public long sumTimespent() {
		long sum = SystemUtil.toPrimitiveLong(this.timespent);
		if (this.children == null || this.children.isEmpty()) {
			return sum;
		}
		for (var child : children) {
			if (child.getDestination() != null) {
				sum += child.getDestination().sumTimespent();
			}
		}
		return sum;
	}

	public long calcTimeAvailable() {
		return Math.subtractExact(SystemUtil.toPrimitiveLong(this.timeoriginalestimate),
				SystemUtil.toPrimitiveLong(this.timespent));
	}

	public double calcDeadlinePercentage() {
		var passedDays = Duration.between(this.created, LocalDateTime.now()).toDays();
		var totalDays = this.duedate == null ? 1 : Duration.between(this.created, this.duedate).toDays();
		if (passedDays > totalDays) {
			return 100.0;
		}
		return SystemUtil.formatTwoDecimals(100.0 * passedDays / totalDays);
	}

	public double calcProductivityPercentage() {
		var spent = this.sumTimespent();
		if (spent == 0) {
			return 100.0;
		}
		return SystemUtil.formatTwoDecimals(100.0 * SystemUtil.toPrimitiveLong(this.timeoriginalestimate) / spent);
	}
}
