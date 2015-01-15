package it.fge.dtimes.util;

import java.util.*;

public class Util {

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
	while (list.remove(null));
	while (list.remove(""));
	while (list.remove("[]"));
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
	    //expireDate.add(Calendar.MONTH, month);
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
}
