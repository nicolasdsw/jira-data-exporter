package br.com.firedev.web.epics;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import br.com.firedev.model.Issue;
import br.com.firedev.model.IssueType_;
import br.com.firedev.model.Issue_;
import br.com.firedev.model.Project_;
import br.com.firedev.util.SpecUtil;
import br.com.firedev.util.SystemUtil;
import br.com.firedev.util.Xlsx;
import br.com.firedev.util.i18n.MessageFactory;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EpicMapper {

	public Specification<Issue> toSpec(EpicFilter filter) {
		return filter == null ? null : (root, query, builder) -> {
			var predicates = new ArrayList<Predicate>();
			predicates.add(SpecUtil.like(builder, root.get(Issue_.ISSUETYPE).get(IssueType_.PNAME), "Epic"));
			var issueKey = SpecUtil.concatAll(builder, root.get(Issue_.PROJECT).get(Project_.PKEY),
					builder.literal("-"), root.get(Issue_.ISSUENUM));
			predicates.add(SpecUtil.like(builder, issueKey, filter.getEpic()));
			predicates.add(SpecUtil.isBetween(builder, root.get(Issue_.CREATED), filter.getCreated()));
			predicates.add(SpecUtil.isBetween(builder, root.get(Issue_.DUEDATE), filter.getDuedate()));
			return SpecUtil.toAndArray(builder, predicates);
		};
	}

	public EpicResDTO toDto(Issue entity) {
		var dto = new EpicResDTO();
		dto.setId(entity.getId());
		dto.setEpic(entity.getDesc());
		dto.setCreated(SystemUtil.toLocalDate(entity.getCreated()));
		dto.setDuedate(SystemUtil.toLocalDate(entity.getDuedate()));
		dto.setTimeEstimated(SystemUtil.toPrimitiveLong(entity.getTimeoriginalestimate()));
		dto.setTimeSpent(entity.sumTimespent());
		dto.setTimeAvailable(entity.calcTimeAvailable());
		dto.setProductivityPercentage(entity.calcProductivityPercentage());
		dto.setDeadlinePercentage(entity.calcDeadlinePercentage());
		return dto;
	}

	public Xlsx toExcel(List<Issue> list) {
		var xlsx = new Xlsx("epics");
		var wb = xlsx.getWorkbook();
		var cellStyle = wb.createCellStyle();
		cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
		var dtTimeStyle = wb.createCellStyle();
		dtTimeStyle.setVerticalAlignment(VerticalAlignment.TOP);
		dtTimeStyle.setDataFormat(wb.getCreationHelper().createDataFormat().getFormat("dd/MM/yyyy hh:mm"));
		var sheet = wb.createSheet("Epics");
		sheet.createFreezePane(0, 1, 0, 1);
		var line = sheet.getLastRowNum() + 1;
		var row = sheet.createRow(line++);
		var column = 0;
		var labelPrefix = "epic.";
		xlsx.createCell(row, column++, cellStyle).setCellValue(MessageFactory.getLabel(labelPrefix + "desc"));
		xlsx.createCell(row, column++, cellStyle).setCellValue(MessageFactory.getLabel(labelPrefix + "created"));
		xlsx.createCell(row, column++, cellStyle).setCellValue(MessageFactory.getLabel(labelPrefix + "duedate"));
		xlsx.createCell(row, column++, cellStyle).setCellValue(MessageFactory.getLabel(labelPrefix + "timeestimated"));
		xlsx.createCell(row, column++, cellStyle).setCellValue(MessageFactory.getLabel(labelPrefix + "timespent"));
		xlsx.createCell(row, column, cellStyle).setCellValue(MessageFactory.getLabel(labelPrefix + "productivity"));
		xlsx.createCell(row, column, cellStyle).setCellValue(MessageFactory.getLabel(labelPrefix + "deadline"));
		for (var entity : list) {
			column = 0;
			row = sheet.createRow(line++);
			var dto = this.toDto(entity);
			xlsx.createCell(row, column++, cellStyle).setCellValue(dto.getEpic());
			xlsx.createCell(row, column++, dtTimeStyle).setCellValue(dto.getCreated());
			xlsx.createCell(row, column++, dtTimeStyle).setCellValue(dto.getDuedate());
			xlsx.createCell(row, column++, cellStyle)
					.setCellValue(SystemUtil.secondsToDuration(dto.getTimeEstimated()));
			xlsx.createCell(row, column++, cellStyle).setCellValue(SystemUtil.secondsToDuration(dto.getTimeSpent()));
			xlsx.createCell(row, column++, cellStyle)
					.setCellValue(SystemUtil.secondsToDuration(dto.getTimeAvailable()));
			xlsx.createCell(row, column++, cellStyle).setCellValue(dto.getProductivityPercentage());
			xlsx.createCell(row, column, cellStyle).setCellValue(dto.getDeadlinePercentage());
		}
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		return xlsx;
	}
}
