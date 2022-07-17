package br.com.firedev.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SystemUtil {

	private SystemUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static LocalDate toLocalDate(LocalDateTime ldt) {
		return ldt == null ? null : ldt.toLocalDate();
	}

	public static long toPrimitiveLong(Long l) {
		return l == null ? 0l : l.longValue();
	}

	public static String secondsToDuration(long seconds) {
		return Duration.ofSeconds(seconds).toString().replace("PT", "");
	}

	public static double formatTwoDecimals(double value) {
		var df = new DecimalFormat("0.00");
		try {
			return df.parse(df.format(value)).doubleValue();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0.0;
		}
	}
}
