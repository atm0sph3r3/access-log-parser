package com.ef;

import com.ef.generated.tables.daos.ViolationsDao;
import com.ef.generated.tables.pojos.Access;
import com.ef.generated.tables.pojos.Violations;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class Parser {
    public static void main(String[] args) throws IOException {
        Injector injector = Guice.createInjector(new LogModule(args));
        CommandLineParser commandLineParser = injector.getInstance(CommandLineParser.class);
        AccessDao accessDao = injector.getInstance(AccessDao.class);
        ViolationsDao violationsDao = injector.getInstance(ViolationsDao.class);
        AccessLogParser accessLogParser = injector.getInstance(AccessLogParser.class);

        insertLogs(accessLogParser, accessDao, commandLineParser);
        findViolations(commandLineParser, accessDao, violationsDao);
    }

    private static void findViolations(CommandLineParser commandLineParser, AccessDao accessDao, ViolationsDao violationsDao) {
        Collection<Access> accesses = accessDao.selectByTime(commandLineParser.getStartTime(), commandLineParser.getEndTime());
        Map<String, Long> ipCounts = countIpOccurrences(commandLineParser, accesses);
        Collection<Violations> violations = createViolations(commandLineParser, ipCounts);

        violationsDao.insert(violations);
    }

    private static Collection<Violations> createViolations(CommandLineParser commandLineParser, Map<String, Long> ipCounts) {
        Collection<Violations> violations = new ArrayList<>();

        ipCounts.forEach((key, value) -> {
            Violations violation = new Violations();
            String violationComment = String.format("%s was observed at least %s times between %s and %s.",
                    key,
                    commandLineParser.getThreshold(),
                    commandLineParser.getStartTime(),
                    commandLineParser.getEndTime());

            System.out.println(violationComment);

            violation.setComment(violationComment);
            violation.setIp(key);

            violations.add(violation);

        });
        return violations;
    }

    private static Map<String, Long> countIpOccurrences(CommandLineParser commandLineParser, Collection<Access> accesses) {
        return accesses.stream()
                    .collect(Collectors.groupingBy(Access::getIp, Collectors.counting()))
                    .entrySet().stream()
                    .filter(entry -> entry.getValue() > commandLineParser.getThreshold())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static void insertLogs(AccessLogParser accessLogParser, AccessDao accessDao, CommandLineParser commandLineParser) throws IOException {
        if (accessDao.count() == 0) {
            Collection<Access> records = accessLogParser.parseLogFile(commandLineParser.getAccessLogFile());
            accessDao.insert(records);
        }
    }
}
