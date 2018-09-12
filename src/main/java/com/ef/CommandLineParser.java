package com.ef;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CommandLineParser {
    private static final String START_DATE = "--startDate";
    private static final String DURATION = "--duration";
    private static final String THRESHOLD = "--threshold";
    private static final String ACCESS_LOG_FILE = "--accesslog";
    private static final String HOURLY = "hourly";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");

    private Timestamp startTime;
    private Timestamp endTime;
    private int threshold;
    private String accessLogFile;

    public CommandLineParser(final String[] args) {
        setAttributes(args);
    }

    private void setAttributes(final String[] args) {
        Map<String, String> argsMap = createMapFromArgs(args);
        long timeRangeInMillisec;

        try {
            Date startDate = DATE_FORMAT.parse(argsMap.get(START_DATE));
            long startTimeMillisecond = startDate.toInstant().toEpochMilli();
            startTime = new Timestamp(startTimeMillisecond);

            if (argsMap.get(DURATION).equals(HOURLY)) {
                timeRangeInMillisec = 60 * 60 * 1000;
            } else {
                timeRangeInMillisec = 24 * 60 * 60 * 1000;
            }

            endTime = new Timestamp(startTimeMillisecond + timeRangeInMillisec);
            threshold = Integer.valueOf(argsMap.get(THRESHOLD));
            accessLogFile = argsMap.get(ACCESS_LOG_FILE);
        } catch (ParseException e) {
            throw new RuntimeException("Unable to parse input start date.Exiting.");
        }
    }

    private Map<String, String> createMapFromArgs(String[] args) {
        Map<String, String> argsMap = new HashMap<>();

        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--")) {
                String[] keyValueSplit = args[i].split("=");
                argsMap.put(keyValueSplit[0], keyValueSplit[1]);
            }
        }
        return argsMap;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public int getThreshold() { return threshold; }

    public String getAccessLogFile() {
        return accessLogFile;
    }
}
