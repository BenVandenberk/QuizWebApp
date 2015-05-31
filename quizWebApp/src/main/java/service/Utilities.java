package service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Utilities {

	public static String dateString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return sdf.format(date);
	}

	public static java.sql.Date toSQLDate(String dateString) throws IllegalArgumentException {
		// dd/mm/yyyy
		String[] dateParts = dateString.split("-|/");
		if (dateParts.length != 3) {
			throw new IllegalArgumentException("Ongeldig formaat");
		}
		try {
			int jaar = Integer.parseInt(dateParts[2]);
			int maand = Integer.parseInt(dateParts[1]) - 1;
			int dag = Integer.parseInt(dateParts[0]);
			GregorianCalendar gc = new GregorianCalendar(jaar, maand, dag);
			return new java.sql.Date(gc.getTimeInMillis());
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

}
