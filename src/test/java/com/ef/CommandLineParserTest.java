package com.ef;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertThat;

public class CommandLineParserTest {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");

    private Timestamp expectedStartTimestamp;
    private long startTimeMilliseconds;

    @Before
    public void setUp() throws Exception {
        Date start = DATE_FORMAT.parse("2017-01-01.13:00:00");
        startTimeMilliseconds = start.toInstant().toEpochMilli();
        expectedStartTimestamp = new Timestamp(startTimeMilliseconds);
    }

    @Test
    public void testHourlyParsing() {
        String[] args = {"--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=100", "--accesslog=/path/to/file"};
        Timestamp expectedEndTimestamp = new Timestamp(startTimeMilliseconds + 3600000);
        CommandLineParser commandLineParser = new CommandLineParser(args);

        assertThat(commandLineParser.getStartTime(), Matchers.is((Matchers.equalTo(expectedStartTimestamp))));
        assertThat(commandLineParser.getEndTime(), Matchers.is((Matchers.equalTo(expectedEndTimestamp))));
        assertThat(commandLineParser.getThreshold(), Matchers.is(Matchers.equalTo(100)));
        assertThat(commandLineParser.getAccessLogFile(), Matchers.is(Matchers.equalTo("/path/to/file")));
    }

    @Test
    public void testDailyParsing() {
        String[] args = {"--startDate=2017-01-01.13:00:00", "--duration=daily", "--threshold=100", "--accesslog=/path/to/file"};
        CommandLineParser commandLineParser = new CommandLineParser(args);
        Timestamp expectedEndTimestamp = new Timestamp(startTimeMilliseconds + 24 * 3600000);

        assertThat(commandLineParser.getStartTime(), Matchers.is((Matchers.equalTo(expectedStartTimestamp))));
        assertThat(commandLineParser.getEndTime(), Matchers.is((Matchers.equalTo(expectedEndTimestamp))));
        assertThat(commandLineParser.getThreshold(), Matchers.is(Matchers.equalTo(100)));
        assertThat(commandLineParser.getAccessLogFile(), Matchers.is(Matchers.equalTo("/path/to/file")));
    }
}