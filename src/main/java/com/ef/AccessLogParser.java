package com.ef;

import com.ef.generated.tables.pojos.Access;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class AccessLogParser implements LogParser<Collection<Access>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogParser.class);
    private static final String ACCESS_LOG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String ACCESS_LOG_DELIMITER = "\\|";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(ACCESS_LOG_DATE_FORMAT);

    @Override
    public List<Access> parseLogFile(final String logFile) throws IOException {
        List<Access> accesses = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String inputLine = reader.readLine();

            while (inputLine != null) {
                String[] inputLineDelimited = inputLine.split(ACCESS_LOG_DELIMITER);
                try {
                    accesses.add(createAccessRecordFromInputLine(inputLineDelimited));
                } catch (ParseException e) {
                    LOGGER.error("Unable to parse access log: {}", inputLine, e);
                }

                inputLine = reader.readLine();
            }
        }

        return accesses;
    }

    private Access createAccessRecordFromInputLine(String[] inputLineDelimited) throws ParseException {
        Access accessRecord = new Access();
        Date dateParsed = DATE_FORMAT.parse(inputLineDelimited[0]);
        accessRecord.setDate(new Timestamp(dateParsed.toInstant().toEpochMilli()));
        accessRecord.setIp(inputLineDelimited[1]);
        accessRecord.setRequest(inputLineDelimited[2]);
        accessRecord.setStatus(Integer.valueOf(inputLineDelimited[3]));
        accessRecord.setUseragent(inputLineDelimited[4]);

        return accessRecord;
    }
}
