package kumarreddyn.github.fda.order.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kumarreddyn.github.fda.order.constants.ApplicationConstants;

public class DateUtil {

	private static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);
	
	public static Date toDefaultDateFormat(String dateStrDefaultFormat) {
		DateFormat dateFormat = new SimpleDateFormat(ApplicationConstants.DEFAULT_DATE_FORMAT);
		try {
			return dateFormat.parse(dateStrDefaultFormat);	
		}catch (Exception e) {
			logger.error("Not able to parse date. {} - {}", dateStrDefaultFormat, e.getMessage());
			return null;
		}
	}
	
	public static Date toDefaultDateTimeFormat(String dateStrDefaultFormat) {
		Logger logger = LoggerFactory.getLogger(DateUtil.class);
		DateFormat dateFormat = new SimpleDateFormat(ApplicationConstants.DEFAULT_DATE_TIME_FORMAT);
		try {
			return dateFormat.parse(dateStrDefaultFormat);	
		}catch (Exception e) {
			logger.error("Not able to parse date. {} - {}", dateStrDefaultFormat, e.getMessage());
			return null;
		}
	}
	
	public static String toDefaultDateToStringFormat(Date unformattedDate) {
		Logger logger = LoggerFactory.getLogger(DateUtil.class);
		DateFormat dateFormat = new SimpleDateFormat(ApplicationConstants.DEFAULT_DATE_FORMAT);
		try {
			return dateFormat.format(unformattedDate);	
		}catch (Exception e) {
			logger.error("Not able to format date. {} - {}", unformattedDate, e.getMessage());
			return null;
		}
	}
	
	public static String toDefaultDateTimeToStringFormat(Date unformattedDate) {
		Logger logger = LoggerFactory.getLogger(DateUtil.class);
		DateFormat dateFormat = new SimpleDateFormat(ApplicationConstants.DEFAULT_DATE_TIME_FORMAT);
		try {
			return dateFormat.format(unformattedDate);	
		}catch (Exception e) {
			logger.error("Not able to format date. {} - {}", unformattedDate, e.getMessage());
			return null;
		}
	}
}