package com.aweshams.cinematch.services.data;

/**
 * Created by irteza on 2018-01-23.
 */

import com.google.gson.JsonParseException;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Deserializes {@link DateTime} instances.
 */
public class DateTimeDeserializer extends StringValueDeserializer<DateTime> {

    // region static variables

    private static final DateTimeFormatter[] _dateFormatters;

    // endregion


    // region constructors

    // class constructor
    static {
        _dateFormatters = new DateTimeFormatter[] {
                DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ").withZone(DateTimeZone.UTC),
                DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ").withZone(DateTimeZone.UTC),
                DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.S'Z'").withZone(DateTimeZone.UTC),
                DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.Sz").withZone(DateTimeZone.UTC),
                DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(DateTimeZone.UTC),
                DateTimeFormat.forPattern("EEE MMM dd HH:mm:ss z yyyy").withZone(DateTimeZone.UTC),
                DateTimeFormat.forPattern("yyyy-MM-dd-HH:mm").withZone(DateTimeZone.UTC),
                DateTimeFormat.forPattern("yyyy-MM-ddZ").withZone(DateTimeZone.UTC),
                DateTimeFormat.forPattern("yyyy-MM-dd").withZone(DateTimeZone.UTC)
        };
    }

    // endregion


    // region overridden methods

    @Override
    protected DateTime deserialize(String value) throws JsonParseException {

        // try to parse the date using the registered formats
        for (DateTimeFormatter formatter : _dateFormatters) {

            // parse date (might throw)
            try {
                return DateTime.parse(value, formatter);
            }

            // suppress parse exceptions (handled below)
            catch (Exception unused) {
            }
        }

        // throw if we couldn't parse a date from the value
        throw new JsonParseException("Unable to parse DateTime from value: " + value);
    }

    // endregion
}
