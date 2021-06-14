package com.app.tamagotchi.utils;


import lombok.extern.slf4j.Slf4j;
import org.joda.time.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class GenericUtility {

  public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
  public static final SimpleDateFormat sdfFull = new SimpleDateFormat("dd MMMM yyyy (HH:mm:ss)");
  public static final SimpleDateFormat sdfTimestamp = new SimpleDateFormat("yyyyMMddhhmmssSSSS");
  private static final String decimalPattern = "-?([0-9]*)\\.([0-9]*)";

  public static String getToken(RequestAttributes requestAttributes) {
   return ((ServletRequestAttributes) requestAttributes).getRequest().getHeader("Authorization");
  }






  public static String removeListChar(String s) {
    s = s.replace(']', ' ');
    s = s.replace('[', ' ');
    return s.trim();
  }

  public static List<String> convertToList(String s) {
    if (s == null || s.length() == 0) {
      return new ArrayList<String>();
    }
    List<String> l = null;
    try {
      s = removeListChar(s);
      String[] pieces = s.split(",");
      for (int i = 0; i < pieces.length; i++) {
        pieces[i] = pieces[i].trim();
      }
      l = new ArrayList<String>(Arrays.asList(pieces));
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return l;
  }

  public static Map<String, String> convertToMap(String s) {
    Map<String, String> m = new HashMap<String, String>();
    try {
      s = removeListChar(s);
      String[] pieces = s.split(",");
      for (int i = 0; i < pieces.length; i++) {
        pieces[i] = pieces[i].trim();
        m.put(pieces[i], pieces[i]);
      }

    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return m;
  }

  public static boolean checkRsaId(String idVal) {
    if (idVal == null || (idVal != null && idVal.trim().length() == 0)) {
      return true;
    }
    idVal = idVal.trim();
    if (idVal.length() < 13) {
      return false;
    }
    int checkDigit = ((Integer.valueOf("" + (idVal.charAt(idVal.length() - 1)))).intValue());
    String odd = "0";
    String even = "";
    int evenResult = 0;
    int result;
    for (int c = 1; c <= idVal.length(); c++) {
      if (c % 2 == 0) {
        even += idVal.charAt(c - 1);
      } else {
        if (c == idVal.length()) {
          continue;
        } else {
          odd = "" + (Integer.valueOf("" + odd).intValue() + Integer
              .valueOf("" + (idVal.charAt(c - 1))));
        }
      }
    }
    String evenS = "" + (Integer.valueOf(even) * 2);
    for (int r = 1; r <= evenS.length(); r++) {
      evenResult += Integer.valueOf("" + evenS.charAt(r - 1));
    }
    result = (Integer.valueOf(odd) + Integer.valueOf(evenResult));
    String resultS = "" + result;
    resultS = "" + (10 - (Integer.valueOf("" + (resultS.charAt(resultS.length() - 1)))).intValue());
    if (resultS.length() > 1) {
      resultS = "" + resultS.charAt(resultS.length() - 1);
    }
    if (Integer.valueOf(resultS) != checkDigit) {
      return false;
    } else {
      return true;
    }
  }

  public static String readFile(String filename) throws Exception {

    FileInputStream fileStream = null;
    BufferedReader reader = null;
    StringBuffer strbuf = null;

    try {
      fileStream = new FileInputStream(filename);
      reader = new BufferedReader(new InputStreamReader(fileStream));
      boolean endFlag = true;
      String line = null;
      strbuf = new StringBuffer(300);
      while (endFlag) {
        line = reader.readLine();
        if (line != null) {
          strbuf.append(line).append("\n");
        }
        if (line == null) {
          endFlag = false;
        }
      }

    } finally {
      if (fileStream != null) {
        fileStream.close();
      }
      if (reader != null) {
        reader.close();
      }
    }
    return strbuf.toString();
  }

  public static void deleteFile(String fn) throws IOException {
    File f = new File(fn);
    if (f.exists()) {
      f.delete();
    }
  }

  public static String convertFileName(String fullPath) {
    String ret = null;
    int pos = -1;
    if (fullPath.lastIndexOf('/') > -1) {
      pos = fullPath.lastIndexOf('/');
    } else if (fullPath.lastIndexOf('\\') > -1) {
      pos = fullPath.lastIndexOf('\\');
    }

    if (pos > -1) {
      ret = fullPath.substring(pos + 1);
    }

    return ret;
  }

  public static String removeLastComma(String string) {
    string = string.substring(0, string.lastIndexOf(','));
    return string;
  }

  public static String removeSpecialChars(String string) {
    return string.replaceAll(",", " ");
  }


  public static byte[] convertBufferedImageToByteArray(BufferedImage bImageFromConvert, String ext)
      throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(bImageFromConvert, ext, baos);
    baos.flush();
    byte[] imageInByte = baos.toByteArray();
    baos.close();
    return imageInByte;
  }

  public static void zipFile(File fileToZip) throws Exception {
    FileOutputStream fos = new FileOutputStream(fileToZip.getAbsolutePath() + ".zip");
    ZipOutputStream zipOut = new ZipOutputStream(fos);

    FileInputStream fis = new FileInputStream(fileToZip);
    ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
    zipOut.putNextEntry(zipEntry);
    final byte[] bytes = new byte[1024];
    int length;
    while ((length = fis.read(bytes)) >= 0) {
      zipOut.write(bytes, 0, length);
    }
    zipOut.close();
    fis.close();
    fos.close();
  }

  public static String camelCaseWord(String word) throws Exception {
    return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
  }

  public static Double convertStringtoDouble(String amt) {
    Double result = null;
    if (amt != null && amt.trim().length() > 0) {
      amt = amt.replaceAll("[(), ]", "").trim();
      result = Double.valueOf(amt);
    }
    return result;
  }

  public static boolean isNumeric(String str) {
    return str.matches("-?\\d+(\\.\\d+)?");
  }

  public static boolean isDouble(String str) {
    return Pattern.matches(decimalPattern, str);
  }


  public static Date addDaysToDate(Date start, int days) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(start);
    cal.add(Calendar.DATE, days);
    return cal.getTime();
  }

  public static Date addDaysToDateExcludeWeekends(Date start, int days) {
    if (days < 1) {
      return start;
    }
    LocalDate date = LocalDate.parse(sdf.format(start));
    LocalDate result = date;
    int addedDays = 0;
    while (addedDays < days) {
      result = result.plusDays(1);
      if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY.getValue()
          || result.getDayOfWeek() == DayOfWeek.SUNDAY.getValue())) {
        ++addedDays;
      }
    }

    return result.toDate();
  }

  public static Date deductDaysFromDate(Date start, int days) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(start);
    cal.add(Calendar.DATE, -days);
    return cal.getTime();
  }

  public static int getDaysBetweenDates(Date startDate, Date endDate) {
    return Days
        .daysBetween(new LocalDate(startDate.getTime()).toDateTimeAtStartOfDay().toLocalDateTime(),
            new LocalDate(endDate.getTime()).toDateTimeAtStartOfDay().toLocalDateTime()).getDays();
  }

  public static int getHoursBetweenDates(Date startDate, Date endDate) {
    return Hours.hoursBetween(new LocalDate(startDate.getTime()), new LocalDate(endDate.getTime()))
        .getHours();
  }

  public static int getMonthsBetweenDates(Date startDate, Date endDate) {
    return Months.monthsBetween(new LocalDate(getStartOfDay(startDate)),
        new LocalDate(getStartOfDay(endDate))).getMonths();
  }

  public static List<Date> getMonthsDates(Date startDate, Date endDate) {
    int size = getMonthsBetweenDates(getFirstDayOfMonth(startDate), getLasttDayOfMonth(endDate));
    List<Date> dates = new ArrayList<Date>();
    if (size != 0) {
      dates.add(startDate);
    }
    for (int i = 0; i < size; i++) {
      if (i > 0) {
        dates.add(getFirstDayOfMonth(addMonthsToDate(startDate, i)));
      }

    }
    dates.add(endDate);
    return dates;
  }

  public static int getYearsBetweenDates(Date startDate, Date endDate) {
    return Years.yearsBetween(new LocalDate(startDate.getTime()), new LocalDate(endDate.getTime()))
        .getYears();
  }

  public static int getDaysBetweenDatesExcludeWeekends(Date start, Date end) {
    DateTime startDateTime = new DateTime(GenericUtility.getEndOfDay(start));
    DateTime endDateTime = new DateTime(GenericUtility.getEndOfDay(end));

    int dayOfWeek;
    int days = 0;

    while (startDateTime.isBefore(endDateTime)) {
      dayOfWeek = startDateTime.getDayOfWeek();
      if ((dayOfWeek == DateTimeConstants.SUNDAY || dayOfWeek == DateTimeConstants.SATURDAY)
          == false) {
        days++;
      }
      startDateTime = startDateTime.plusDays(1);
    }
    days++;
    return days;
  }

  public static Date getStartOfDay(Date date) {
    return (new LocalDateTime(date.getTime()).withHourOfDay(0).withMinuteOfHour(0)
        .withSecondOfMinute(0).withMillisOfSecond(0)).toDate();
  }

  public static Date getEndOfDay(Date date) {
    return (new LocalDateTime(date.getTime()).withHourOfDay(23).withMinuteOfHour(59)
        .withSecondOfMinute(59).withMillisOfSecond(999)).toDate();
  }

  public static Date startDateOfCurrentYear() {
    DateTime dt = new DateTime();
    try {
      return (sdf.parse("" + dt.getYear() + "0101"));

    } catch (ParseException e) {
      log.error(e.getMessage());
      return null;
    }
  }

  public static Date lastDateOfCurrentYear() {
    DateTime dt = new DateTime();
    try {
      return (sdf.parse("" + dt.getYear() + "1231"));

    } catch (ParseException e) {
      log.error(e.getMessage());
      return null;
    }
  }

  public static int getAge(Date dob) {
    return Years.yearsBetween(new LocalDate(dob.getTime()), new LocalDate()).getYears();
  }


  public static Date getFirstDayOfWeek(Date date) {
    LocalDate now = new LocalDate(date);
    return now.withDayOfWeek(1).toDate();
  }

  public static Date getFirstDayOfYear(Date date) {
    return new DateTime(date).dayOfYear().withMinimumValue().withTimeAtStartOfDay().toDate();
  }

  public static Date getLastDayOfYear(Date date) {
    return deductDaysFromDate(new DateTime(getFirstDayOfYear(date)).plusYears(1).toDate(), 1);
  }


  public static Date getLastDayOfWeek(Date date) {
    LocalDate now = new LocalDate(date);
    return now.withDayOfWeek(7).toDate();
  }


  public static Double roundToPrecision(Double value, int precision) {
    BigDecimal val = BigDecimal.valueOf(value);
    val = val.setScale(precision, RoundingMode.HALF_EVEN);
    return val.doubleValue();
  }

  public static Date addMiniutesToDate(Date date, int minutes) {
    if (date == null) {
      return null;
    }
    return (new DateTime(date.getTime()).plusMinutes(minutes)).toDate();
  }

  public static Date addSecondsToDate(Date date, int seconds) {
    if (date == null) {
      return null;
    }
    return (new DateTime(date.getTime()).plusSeconds(seconds)).toDate();
  }

  public static Date addMilliSecondsToDate(Date date, int milli) {
    if (date == null) {
      return null;
    }
    return (new DateTime(date.getTime()).plusMillis(milli)).toDate();
  }


  public static Date addHoursToDate(Date date, int hours) {
    if (date == null) {
      return null;
    }
    return (new DateTime(date.getTime()).plusHours(hours)).toDate();
  }

  public static Date deductHoursFromDate(Date date, int hours) {
    if (date == null) {
      return null;
    }
    return (new DateTime(date.getTime()).minusHours(hours)).toDate();
  }


  public static Date deductMinutesFromDate(Date date, int minutes) {
    if (date == null) {
      return null;
    }
    return (new DateTime(date.getTime()).minusMinutes(minutes)).toDate();
  }

  public static Date addWeeksToDate(Date date, int weeks) {
    if (date == null) {
      return null;
    }
    return (new DateTime(date.getTime()).plusWeeks(weeks)).toDate();
  }

  public static Date addMonthsToDate(Date date, int months) {
    if (date == null) {
      return null;
    }
    return (new DateTime(date.getTime()).plusMonths(months)).toDate();
  }

  public static Date addMonthsToDateGetEndOfMonth(Date date, int months) {
    if (date == null) {
      return null;
    }
    return (new DateTime(date.getTime()).plusMonths(months)).dayOfMonth().withMaximumValue()
        .toDate();
  }

  public static Date deductMonthsFromDate(Date date, int months) {
    if (date == null) {
      return null;
    }
    return (new DateTime(date.getTime()).minusMonths(months)).toDate();
  }

  public static Date addYearsToDate(Date date, int years) {
    if (date == null) {
      return null;
    }
    return (new DateTime(date.getTime()).plusYears(years)).toDate();
  }


  public static Long getHoursBetweenDate(Date startDate, Date endDate) {
    if (startDate == null || endDate == null) {
      return null;
    }
    return (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60);
  }

  public static String getMonthForInt(int num) {
    String month = "wrong";
    DateFormatSymbols dfs = new DateFormatSymbols();
    String[] months = dfs.getMonths();
    if (num >= 0 && num <= 11) {
      month = months[num];
    }
    return month;
  }

  public static int getYearOfDate(Date date) {
    java.time.LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    return localDate.getYear();
  }

  public static int getMonthOfDate(Date date) {
    java.time.LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    return localDate.getMonthValue();
  }

  public static Date getFirstDayOfMonth(Date date) {
    return (new LocalDate(date.getTime()).withDayOfMonth(1)).toDate();
  }

  public static Date getLasttDayOfMonth(Date date) {
    return (new LocalDate(date.getTime()).dayOfMonth().withMaximumValue()).toDate();
  }


}
