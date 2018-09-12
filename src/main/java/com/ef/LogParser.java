package com.ef;

import com.ef.generated.tables.pojos.Access;

import java.io.IOException;
import java.util.List;

public interface LogParser<T> {
    List<Access> parseLogFile(final String logFile) throws IOException;
}
