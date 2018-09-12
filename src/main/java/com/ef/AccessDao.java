package com.ef;

import com.ef.generated.tables.Access;
import org.jooq.Configuration;
import org.jooq.impl.DSL;

import java.sql.Timestamp;
import java.util.List;

public class AccessDao extends com.ef.generated.tables.daos.AccessDao {
    public AccessDao(Configuration configuration) {
        super(configuration);
    }

    public List<com.ef.generated.tables.pojos.Access> selectByTime(Timestamp startTime, Timestamp endTime) {
        return DSL.using(configuration())
                .selectFrom(getTable())
                .where(Access.ACCESS.DATE.between(startTime, endTime))
                .fetch()
                .map(mapper());
    }
}
