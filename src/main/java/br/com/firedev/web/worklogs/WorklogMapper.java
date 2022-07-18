package br.com.firedev.web.worklogs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import br.com.firedev.model.Issue_;
import br.com.firedev.model.Project_;
import br.com.firedev.model.Worklog;
import br.com.firedev.model.Worklog_;
import br.com.firedev.util.SpecUtil;
import br.com.firedev.util.SystemUtil;
import br.com.firedev.util.Xlsx;
import br.com.firedev.util.i18n.MessageFactory;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class WorklogMapper {

	public Specification<Worklog> toSpec(WorklogFilter filter) {
		return filter == null ? null : (root, query, builder) -> {
			var predicates = new ArrayList<Predicate>();
			predicates.add(SpecUtil.isBetween(builder, root.get(Worklog_.STARTDATE), filter.getStartdate()));
			predicates.add(SpecUtil.isIn(root.get(Worklog_.AUTHOR).get(Issue_.ID), filter.getAuthorIds()));
			predicates.add(SpecUtil.isEqual(builder, root.get(Worklog_.ISSUE).get(Issue_.ID), filter.getIssueId()));
			var issueKey = SpecUtil.concatAll(builder, root.get(Worklog_.ISSUE).get(Issue_.PROJECT).get(Project_.PKEY),
					builder.literal("-"), root.get(Worklog_.ISSUE).get(Issue_.ISSUENUM));
			predicates.add(SpecUtil.like(builder, issueKey, filter.getIssue()));
			predicates.add(SpecUtil.isEqual(builder, root.get(Worklog_.ISSUE).get(Issue_.PROJECT).get(Project_.ID),
					filter.getProjectId()));
			return SpecUtil.toAndArray(builder, predicates);
		};
	}

	public WorklogResDTO toDto(Worklog entity) {
		var dto = new WorklogResDTO();
		dto.setAuthor(entity.getAuthorUsername());
		dto.setProject(entity.getIssue().getProject().getPname());
		dto.setIssue(entity.getIssue().getDesc());
		dto.setStartdate(entity.getStartdate());
		dto.setTimeworked(entity.getTimeworked());
		dto.setBody(entity.getBody());
		return dto;
	}

	public Xlsx toExcel(List<Worklog> list) {
		var xlsx = new Xlsx("worklogs");
		var wb = xlsx.getWorkbook();
		var cellStyle = wb.createCellStyle();
		cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
		var dtTimeStyle = wb.createCellStyle();
		dtTimeStyle.setVerticalAlignment(VerticalAlignment.TOP);
		dtTimeStyle.setDataFormat(wb.getCreationHelper().createDataFormat().getFormat("dd/MM/yyyy hh:mm"));
		var sheet = wb.createSheet("Worklogs");
		sheet.createFreezePane(0, 1, 0, 1);
		var line = sheet.getLastRowNum() + 1;
		var row = sheet.createRow(line++);
		var column = 0;
		var labelPrefix = "worklog.";
		xlsx.createCell(row, column++, cellStyle).setCellValue(MessageFactory.getLabel(labelPrefix + Worklog_.AUTHOR));
		xlsx.createCell(row, column++, cellStyle).setCellValue(MessageFactory.getLabel(labelPrefix + Issue_.PROJECT));
		xlsx.createCell(row, column++, cellStyle).setCellValue(MessageFactory.getLabel(labelPrefix + Worklog_.ISSUE));
		xlsx.createCell(row, column++, cellStyle)
				.setCellValue(MessageFactory.getLabel(labelPrefix + Worklog_.STARTDATE));
		xlsx.createCell(row, column++, cellStyle)
				.setCellValue(MessageFactory.getLabel(labelPrefix + Worklog_.TIMEWORKED));
		xlsx.createCell(row, column, cellStyle).setCellValue(MessageFactory.getLabel(labelPrefix + Worklog_.BODY));
		for (var entity : list) {
			column = 0;
			row = sheet.createRow(line++);
			var dto = this.toDto(entity);
			xlsx.createCell(row, column++, cellStyle).setCellValue(dto.getAuthor());
			xlsx.createCell(row, column++, cellStyle).setCellValue(dto.getProject());
			xlsx.createCell(row, column++, cellStyle).setCellValue(dto.getIssue());
			xlsx.createCell(row, column++, dtTimeStyle).setCellValue(dto.getStartdate());
			xlsx.createCell(row, column++, cellStyle).setCellValue(SystemUtil.secondsToDuration(dto.getTimeworked()));
			xlsx.createCell(row, column, cellStyle).setCellValue(dto.getBody());
		}
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.setColumnWidth(5, 50 * 256);
		return xlsx;
	}
}
