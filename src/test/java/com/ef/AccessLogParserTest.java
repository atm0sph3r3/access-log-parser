package com.ef;

import com.ef.generated.tables.pojos.Access;
import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class AccessLogParserTest {
    private static final String ACCESS_LOG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private AccessLogParser accessLogParser;

    @Before
    public void setUp() throws Exception {
        accessLogParser = new AccessLogParser();
    }

    @Test
    public void testParseLogFile() throws Exception {
        List<Access> accesses = accessLogParser.parseLogFile(Resources.getResource("access.log").getFile());
        String expectedDate = "2017-01-01 00:00:11.763";
        Timestamp expectedTimestamp = new Timestamp(new SimpleDateFormat(ACCESS_LOG_DATE_FORMAT).parse(expectedDate).toInstant().toEpochMilli());

        assertThat(accesses, notNullValue());
        assertThat(accesses, hasSize(3));
        assertThat(accesses.get(0).getDate(), equalTo(expectedTimestamp));
    }
}