package service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {

	public static String dateString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return sdf.format(date);

	}

}
