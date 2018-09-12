/*
 * This file is generated by jOOQ.
 */
package com.ef.generated.tables.records;


import com.ef.generated.tables.Access;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AccessRecord extends UpdatableRecordImpl<AccessRecord> implements Record6<String, String, Integer, String, Integer, Timestamp> {

    private static final long serialVersionUID = -294807798;

    /**
     * Setter for <code>logs.access.ip</code>.
     */
    public void setIp(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>logs.access.ip</code>.
     */
    public String getIp() {
        return (String) get(0);
    }

    /**
     * Setter for <code>logs.access.request</code>.
     */
    public void setRequest(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>logs.access.request</code>.
     */
    public String getRequest() {
        return (String) get(1);
    }

    /**
     * Setter for <code>logs.access.status</code>.
     */
    public void setStatus(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>logs.access.status</code>.
     */
    public Integer getStatus() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>logs.access.userAgent</code>.
     */
    public void setUseragent(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>logs.access.userAgent</code>.
     */
    public String getUseragent() {
        return (String) get(3);
    }

    /**
     * Setter for <code>logs.access.id</code>.
     */
    public void setId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>logs.access.id</code>.
     */
    public Integer getId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>logs.access.date</code>.
     */
    public void setDate(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>logs.access.date</code>.
     */
    public Timestamp getDate() {
        return (Timestamp) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<String, String, Integer, String, Integer, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<String, String, Integer, String, Integer, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return Access.ACCESS.IP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Access.ACCESS.REQUEST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return Access.ACCESS.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Access.ACCESS.USERAGENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return Access.ACCESS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return Access.ACCESS.DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getIp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getRequest();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getUseragent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component6() {
        return getDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getIp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getRequest();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getUseragent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccessRecord value1(String value) {
        setIp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccessRecord value2(String value) {
        setRequest(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccessRecord value3(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccessRecord value4(String value) {
        setUseragent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccessRecord value5(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccessRecord value6(Timestamp value) {
        setDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccessRecord values(String value1, String value2, Integer value3, String value4, Integer value5, Timestamp value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AccessRecord
     */
    public AccessRecord() {
        super(Access.ACCESS);
    }

    /**
     * Create a detached, initialised AccessRecord
     */
    public AccessRecord(String ip, String request, Integer status, String useragent, Integer id, Timestamp date) {
        super(Access.ACCESS);

        set(0, ip);
        set(1, request);
        set(2, status);
        set(3, useragent);
        set(4, id);
        set(5, date);
    }
}