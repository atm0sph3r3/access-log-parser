package com.ef;

import com.ef.generated.tables.daos.ViolationsDao;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DefaultConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LogModule extends AbstractModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogModule.class);

    /*
    For demonstration purposes only. These should be loaded via a secrets manager.
     */
    private static final String USERNAME = "logs";
    private static final String PASSWORD = "password";
    private static final String URL = "jdbc:mysql://localhost:3306/logs?useSSL=false&verifyServerCertificate=false";

    private final Configuration configuration;
    private final String[] commandLineArgs;

    public LogModule(final String[] commandLineArgs) {
        this.commandLineArgs = commandLineArgs;

        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Settings settings = new Settings();
            settings.setReturnRecordToPojo(false);
            configuration = new DefaultConfiguration()
                    .set(conn)
                    .set(settings)
                    .set(SQLDialect.MYSQL);
        } catch (SQLException e) {
            LOGGER.error("Unable to create JDBC connection.", e);
            throw new RuntimeException("Unable to continue. Exiting.");
        }

    }

    @Provides
    AccessDao providesAccessDao() {
        return new AccessDao(configuration);
    }

    @Provides
    ViolationsDao providesViolationsDao() {
        return new ViolationsDao(configuration);
    }

    @Provides
    CommandLineParser providesCommandLineParser() {
        return new CommandLineParser(commandLineArgs);
    }
}
