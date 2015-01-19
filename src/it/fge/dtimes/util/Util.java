package it.fge.dtimes.util;

import it.fge.dtimes.model.subscription.SubscriptionDTO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

public class Util {
	private static SimpleDateFormat sdfPeriodCod = new SimpleDateFormat("yyyy_MM", Locale.ITALIAN);
	private static SimpleDateFormat sdfPeriodLabel = new SimpleDateFormat("MMMM yyyy", Locale.ITALIAN);

	public static String[] removeDuplicatesStringInArray(String[] array) {
		LinkedHashSet<String> set = new LinkedHashSet<String>();
		for (String x : array) {
			set.add(x);
		}
		array = new String[set.size()];
		array = set.toArray(array);
		return array;
	}

	public static <E> List<E> removeEmptyObjectInList(List<E> list) {
		while (list.remove(null))
			;
		while (list.remove(""))
			;
		while (list.remove("[]"))
			;
		return list;
	}

	public static List<String> removeDuplcatesStringInList(List<String> list) {
		HashSet<String> hs = new HashSet<String>();
		hs.addAll(list);
		list.clear();
		list.addAll(hs);
		return list;
	}

	public static Calendar calculateExpirationDate(Date startDate, int month) {

		Calendar expireDate = Calendar.getInstance();
		expireDate.setTime(startDate);
		if (month < 12) {
			// expireDate.add(Calendar.MONTH, month);
			expireDate.set(Calendar.DAY_OF_MONTH, expireDate.getActualMaximum(Calendar.DAY_OF_MONTH));
			expireDate.set(Calendar.HOUR_OF_DAY, 23);
			expireDate.set(Calendar.MINUTE, 59);
			expireDate.set(Calendar.SECOND, 59);
		} else {
			expireDate.add(Calendar.YEAR, 1);
			expireDate.set(Calendar.MONTH, 7);
			expireDate.set(Calendar.DAY_OF_MONTH, expireDate.getActualMaximum(Calendar.DAY_OF_MONTH));
			expireDate.set(Calendar.HOUR_OF_DAY, 23);
			expireDate.set(Calendar.MINUTE, 59);
			expireDate.set(Calendar.SECOND, 59);
		}
		return expireDate;
	}

	public static Map<String, String> getPeriodToFilterList(Calendar now, SubscriptionDTO firstSubscription) {

		Map<String, String> period = new HashMap<String, String>();
		Calendar startDate = Calendar.getInstance();
		if (firstSubscription == null) {
			startDate.set(Calendar.DAY_OF_MONTH, 1);
			startDate.set(Calendar.MONTH, 8);
			startDate.add(Calendar.YEAR, -1);
			startDate.set(Calendar.HOUR_OF_DAY, 0);
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
			startDate.set(Calendar.MILLISECOND, 000);
		} else {
			startDate.setTime(firstSubscription.getRegistrationDate());

			do {
				period.put(sdfPeriodCod.format(startDate.getTime()), StringUtils.capitalize(sdfPeriodLabel.format(startDate.getTime())));
				startDate.add(Calendar.MONTH, 1);
			} while (startDate.before(now));
		}
		
		Map<String, String> treeMap = new TreeMap<String, String>(period);

		return treeMap;
	}
}
