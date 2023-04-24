/*
 * This file is generated by jOOQ.
 */
package scrapper.domains.jooq.tables.records;


import jakarta.validation.constraints.Size;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;

import scrapper.domains.jooq.tables.LinkSubscription;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LinkSubscriptionRecord extends UpdatableRecordImpl<LinkSubscriptionRecord> implements Record4<Long, Long, String, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>LINK_SUBSCRIPTION.ID</code>.
     */
    public void setId(@Nullable Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>LINK_SUBSCRIPTION.ID</code>.
     */
    @Nullable
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>LINK_SUBSCRIPTION.TGUSERID</code>.
     */
    public void setTguserid(@NotNull Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>LINK_SUBSCRIPTION.TGUSERID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getTguserid() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>LINK_SUBSCRIPTION.LINK</code>.
     */
    public void setLink(@NotNull String value) {
        set(2, value);
    }

    /**
     * Getter for <code>LINK_SUBSCRIPTION.LINK</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 256)
    @NotNull
    public String getLink() {
        return (String) get(2);
    }

    /**
     * Setter for <code>LINK_SUBSCRIPTION.LASTUPDATE</code>.
     */
    public void setLastupdate(@Nullable LocalDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>LINK_SUBSCRIPTION.LASTUPDATE</code>.
     */
    @Nullable
    public LocalDateTime getLastupdate() {
        return (LocalDateTime) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row4<Long, Long, String, LocalDateTime> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row4<Long, Long, String, LocalDateTime> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Long> field1() {
        return LinkSubscription.LINK_SUBSCRIPTION.ID;
    }

    @Override
    @NotNull
    public Field<Long> field2() {
        return LinkSubscription.LINK_SUBSCRIPTION.TGUSERID;
    }

    @Override
    @NotNull
    public Field<String> field3() {
        return LinkSubscription.LINK_SUBSCRIPTION.LINK;
    }

    @Override
    @NotNull
    public Field<LocalDateTime> field4() {
        return LinkSubscription.LINK_SUBSCRIPTION.LASTUPDATE;
    }

    @Override
    @Nullable
    public Long component1() {
        return getId();
    }

    @Override
    @NotNull
    public Long component2() {
        return getTguserid();
    }

    @Override
    @NotNull
    public String component3() {
        return getLink();
    }

    @Override
    @Nullable
    public LocalDateTime component4() {
        return getLastupdate();
    }

    @Override
    @Nullable
    public Long value1() {
        return getId();
    }

    @Override
    @NotNull
    public Long value2() {
        return getTguserid();
    }

    @Override
    @NotNull
    public String value3() {
        return getLink();
    }

    @Override
    @Nullable
    public LocalDateTime value4() {
        return getLastupdate();
    }

    @Override
    @NotNull
    public LinkSubscriptionRecord value1(@Nullable Long value) {
        setId(value);
        return this;
    }

    @Override
    @NotNull
    public LinkSubscriptionRecord value2(@NotNull Long value) {
        setTguserid(value);
        return this;
    }

    @Override
    @NotNull
    public LinkSubscriptionRecord value3(@NotNull String value) {
        setLink(value);
        return this;
    }

    @Override
    @NotNull
    public LinkSubscriptionRecord value4(@Nullable LocalDateTime value) {
        setLastupdate(value);
        return this;
    }

    @Override
    @NotNull
    public LinkSubscriptionRecord values(@Nullable Long value1, @NotNull Long value2, @NotNull String value3, @Nullable LocalDateTime value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached LinkSubscriptionRecord
     */
    public LinkSubscriptionRecord() {
        super(LinkSubscription.LINK_SUBSCRIPTION);
    }

    /**
     * Create a detached, initialised LinkSubscriptionRecord
     */
    @ConstructorProperties({ "id", "tguserid", "link", "lastupdate" })
    public LinkSubscriptionRecord(@Nullable Long id, @NotNull Long tguserid, @NotNull String link, @Nullable LocalDateTime lastupdate) {
        super(LinkSubscription.LINK_SUBSCRIPTION);

        setId(id);
        setTguserid(tguserid);
        setLink(link);
        setLastupdate(lastupdate);
    }

    /**
     * Create a detached, initialised LinkSubscriptionRecord
     */
    public LinkSubscriptionRecord(scrapper.domains.jooq.tables.pojos.LinkSubscription value) {
        super(LinkSubscription.LINK_SUBSCRIPTION);

        if (value != null) {
            setId(value.getId());
            setTguserid(value.getTguserid());
            setLink(value.getLink());
            setLastupdate(value.getLastupdate());
        }
    }
}
