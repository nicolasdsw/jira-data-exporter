package br.com.firedev.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import lombok.Getter;

public class Xlsx {

	@Getter
	private XSSFWorkbook workbook;
	private String filename;

	public Xlsx(String filename) {
		super();
		this.workbook = new XSSFWorkbook();
		this.filename = filename;
	}

	public ResponseEntity<byte[]> export() {
		byte[] file;
		try (var bos = new ByteArrayOutputStream()) {
			this.workbook.write(bos);
			file = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuleException("Erro ao gerar XLSX");
		} finally {
			try {
				this.workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Xlsx.toResponseEntity(this.filename, file);
	}

	public XSSFCell createCell(XSSFRow row, Integer column, XSSFCellStyle style) {
		var cell = row.createCell(column);
		cell.setCellStyle(style);
		return cell;
	}

	public static ResponseEntity<byte[]> toResponseEntity(String fileName, byte[] bytes) {
		if (bytes != null) {
			var headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
			return ResponseEntity.ok().headers(headers).contentLength(bytes.length)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(bytes);
		}
		return ResponseEntity.noContent().build();
	}
}
