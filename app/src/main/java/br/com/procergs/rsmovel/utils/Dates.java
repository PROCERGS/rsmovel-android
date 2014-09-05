package br.com.procergs.rsmovel.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;

public abstract class Dates {

	public static String dateToString(Date date, String format) {
		SimpleDateFormat formater = new SimpleDateFormat(format);
		String str = "";

		try {
			if (date != null) {
				str = formater.format(date);
			}
		} catch (Exception ex) {
			Log.e("Dates", "Erro ao converter data em string");
		}
		return str;
	}

	public static String hoje() {
		return Dates.dateToString(new Date(), "yyyy-MM-dd");
	}

	public static String format(Context ctx, String strDate, String strFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
		Date date = null;
		try {
			date = sdf.parse(strDate);
		} catch (Exception e) {

		}
		java.text.DateFormat dateFormat = DateFormat.getDateFormat(ctx);
		String s = dateFormat.format(date);
		return s;
	}

	public static Date stringToDate(Context ctx, String strDate, String strFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
		Date date = null;
		try {
			date = sdf.parse(strDate);
		} catch (Exception e) {

		}

		return date;
	}
	
	public static long diff(Date start, Date end) {
		Calendar d1 = Calendar.getInstance();
        d1.setTime(start);
 
        Calendar d2 = Calendar.getInstance();
        long diferenca = d2.getTimeInMillis() - d1.getTimeInMillis();
        
        int tempoDia = 1000 * 60 * 60 * 24;
        long diasDiferenca = diferenca / tempoDia;
        return diasDiferenca;
	}

}
