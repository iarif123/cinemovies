package com.aweshams.cinematch.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

/**
 * Created by irteza on 2018-05-20.
 */

public class DateHelper {

    //region static variables
    public static final String GMT_TIME_ZONE = "Etc/GMT";
    public static final String BT_LONG_DATE_DTF = "yyyy-MM-dd'T'HH:mm:ssZ";


    // DateTimeFormatter
    public static final String BT_SHORT_DATE_DTF = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z";

    private static final DateTimeFormatter mEnglishDateFormat = DateTimeFormat.forPattern("MMM. d").withLocale(Locale.CANADA);
    private static final DateTimeFormatter mFrenchDateFormat = DateTimeFormat.forPattern("d MMM.").withLocale(Locale.CANADA_FRENCH);

    private static final DateTimeFormatter mEnglishDateFormatLong = DateTimeFormat.forPattern("MMMM. d").withLocale(Locale.CANADA);
    private static final DateTimeFormatter mFrenchDateFormatLong = DateTimeFormat.forPattern("d MMMM.").withLocale(Locale.CANADA_FRENCH);

    //endregion

    //region static methods
    /*public static String formatDateForJson(DateTime date) {
        String result = null;

        if (date != null) {
            try {
                DateTime localDate = new DateTime(date);
                DateTimeFormatter fmt = DateTimeFormat.forPattern(BT_SHORT_DATE_DTF).withZone(DateTimeZone.forID(GMT_TIME_ZONE));
                result = localDate.toString(fmt);

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static String formatDate(DateTime date) {
        if (date == null) {
            return "";
        }
        DateTimeFormatter format;
        if (ServiceLocator.getService(ILanguageService.class).getLanguage() == Language.ENGLISH) {
            format = DateTimeFormat.forPattern("MMM. dd, yyyy").withLocale(Locale.CANADA);
        } else {
            format = DateTimeFormat.forPattern("MMM dd, yyyy").withLocale(Locale.CANADA_FRENCH);
        }
        return date.toString(format);
    }

    public static String formatDateForAccessibilityAndLocale(DateTime date) {

        String result = null;
        if (date == null) {
            return "";
        }

        DateTimeFormat format;

        if (ServiceLocator.getService(ILanguageService.class).getLanguage() == Language.ENGLISH) {
            try {
                DateTimeFormatter fmt = DateTimeFormat.forPattern("MMMM dd, yyyy").withLocale(Locale.CANADA);
                result = date.toString(fmt);

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        } else {
            try {
                DateTimeFormatter fmt = DateTimeFormat.forPattern("MMMM dd, yyyy").withLocale(Locale.CANADA_FRENCH);
                result = date.toString(fmt);

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        return result;
    }*/



    public static String formatDateForLastUpdatedDate(DateTime date) {

        String result = null;

        if (date != null) {
            try {
                DateTime localDate = new DateTime(date);
                DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM dd hh:mmaa zzz");
                result = localDate.toString(fmt);

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    /*public static String formatBillingCycle(DateTime startDate, DateTime endDate) {

        String result = "";
        Language language =  ServiceLocator.getService(ILanguageService.class).getLanguage();
        DateTimeFormatter format = language == Language.ENGLISH ? mEnglishDateFormat : mFrenchDateFormat;
        format.withZone(DateTimeZone.forID(GMT_TIME_ZONE));
        if (startDate != null) {
            result += getAbbreviatedShortDate(startDate, format);

            if (endDate != null) {
                if (startDate.getYear() != endDate.getYear()) {

                    if(language == Language.ENGLISH) {
                        result += ", ";
                    } else {
                        result += " ";
                    }
                    result += startDate.getYear();
                }

                result += " - ";
            }
        }

        if (endDate != null) {
            result += getAbbreviatedShortDate(endDate, format);
            if(language == Language.ENGLISH) {
                result += ", ";
            } else {
                result += " ";
            }
            result += endDate.getYear();
        }

        return result;
    }

    public static String formatBillingCycleWithNoAbbrev(BillCycle billCycle) {

        String result = "";
        DateTime startDate = billCycle.startDt;
        DateTime endDate = billCycle.endDt;

        Language language =  ServiceLocator.getService(ILanguageService.class).getLanguage();

        DateTimeFormatter format = language == Language.ENGLISH ? mEnglishDateFormat : mFrenchDateFormat;
        format.withZone(DateTimeZone.forID(GMT_TIME_ZONE));
        if (startDate != null) {
            result += getAbbreviatedShortDate(startDate, format);

            if (endDate != null) {
                if (startDate.getYear() != endDate.getYear()) {

                    if(language == Language.ENGLISH) {
                        result += ", ";
                    } else {
                        result += " ";
                    }
                }

                result += " - ";
            }
        }

        if (endDate != null) {
            result += getAbbreviatedShortDate(endDate, format);
        }

        return result;
    }


    public static String formatBillingCycleForAccessibility(DateTime startDate, DateTime endDate) {
        String result = "";
        DateTimeFormatter format = ServiceLocator.getService(ILanguageService.class).getLanguage() == Language.ENGLISH ? mEnglishDateFormatLong : mFrenchDateFormatLong;
        format.withZone(DateTimeZone.forID(GMT_TIME_ZONE));
        if (startDate != null) {
            result += startDate.toString(format);

            if (endDate != null) {
                if (startDate.getYear() != endDate.getYear()) {
                    result += " " + startDate.getYear();
                }

                result += " - ";
            }
        }

        if (endDate != null) {
            result += endDate.toString(format);
            result += " " + endDate.getYear();
        }

        return result;
    }

    public static String formatDateRecord(DateTime date) {
        String result = null;

        if (date != null) {
            result = getAbbreviatedShortDate(date, ServiceLocator.getService(ILanguageService.class).getLanguage() == Language.ENGLISH ? mEnglishDateFormat : mFrenchDateFormat);
        }

        return result;
    }


    public static DateTime getDateTimeFromString(String dateString, String pattern) {
        if (dateString != null) {

            try {
                DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern).withZone(DateTimeZone.UTC);
                return dtf.parseDateTime(dateString);

            } catch (Exception e) {
                Log.e("DateHelper", "Error parsing date in string: " + dateString, e);
            }

        }

        return null;
    }

    private static String getAbbreviatedShortDate(DateTime date, DateTimeFormatter format) {
        if (ServiceLocator.getService(ILanguageService.class).getLanguage() == Language.FRENCH) {
            if (date.getMonthOfYear() == 5 || date.getMonthOfYear() == 6 || date.getMonthOfYear() == 8) { //mai/juin/aout
                return date.toString(format).replace(".", "");
            } else {
                return date.toString(format).replace("..", "."); // sometimes "juil." is formatted as "juil.."
            }
        } else {
            if (date.getMonthOfYear() == 5) { //may
                return date.toString(format).replace(".", "");
            } else {
                return date.toString(format).replace("..", ".");
            }
        }
    }*/

    //endregion
}
