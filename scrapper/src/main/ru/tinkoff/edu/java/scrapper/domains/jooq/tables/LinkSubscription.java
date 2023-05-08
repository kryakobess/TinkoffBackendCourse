/*
 * This file is generated by jOOQ.
 */

package scrapper.domains.jooq.tables;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function4;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import scrapper.domains.jooq.DefaultSchema;
import scrapper.domains.jooq.Keys;
import scrapper.domains.jooq.tables.records.LinkSubscriptionRecord;


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
public class LinkSubscription extends TableImpl<LinkSubscriptionRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>LINK_SUBSCRIPTION</code>
     */
    public static final LinkSubscription LINK_SUBSCRIPTION = new LinkSubscription();

    /**
     * The class holding records for this type
     */
    @Override
    @NotNull
    public Class<LinkSubscriptionRecord> getRecordType() {
        return LinkSubscriptionRecord.class;
    }

    /**
     * The column <code>LINK_SUBSCRIPTION.ID</code>.
     */
    public final TableField<LinkSubscriptionRecord, Long> ID = createField(DSL.name("ID"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>LINK_SUBSCRIPTION.TGUSERID</code>.
     */
    public final TableField<LinkSubscriptionRecord, Long> TGUSERID = createField(DSL.name("TGUSERID"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>LINK_SUBSCRIPTION.LINK</code>.
     */
    public final TableField<LinkSubscriptionRecord, String> LINK = createField(DSL.name("LINK"), SQLDataType.VARCHAR(256).nullable(false), this, "");

    /**
     * The column <code>LINK_SUBSCRIPTION.LASTUPDATE</code>.
     */
    public final TableField<LinkSubscriptionRecord, LocalDateTime> LASTUPDATE = createField(DSL.name("LASTUPDATE"), SQLDataType.LOCALDATETIME(6), this, "");

    private LinkSubscription(Name alias, Table<LinkSubscriptionRecord> aliased) {
        this(alias, aliased, null);
    }

    private LinkSubscription(Name alias, Table<LinkSubscriptionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>LINK_SUBSCRIPTION</code> table reference
     */
    public LinkSubscription(String alias) {
        this(DSL.name(alias), LINK_SUBSCRIPTION);
    }

    /**
     * Create an aliased <code>LINK_SUBSCRIPTION</code> table reference
     */
    public LinkSubscription(Name alias) {
        this(alias, LINK_SUBSCRIPTION);
    }

    /**
     * Create a <code>LINK_SUBSCRIPTION</code> table reference
     */
    public LinkSubscription() {
        this(DSL.name("LINK_SUBSCRIPTION"), null);
    }

    public <O extends Record> LinkSubscription(Table<O> child, ForeignKey<O, LinkSubscriptionRecord> key) {
        super(child, key, LINK_SUBSCRIPTION);
    }

    @Override
    @Nullable
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    @NotNull
    public Identity<LinkSubscriptionRecord, Long> getIdentity() {
        return (Identity<LinkSubscriptionRecord, Long>) super.getIdentity();
    }

    @Override
    @NotNull
    public UniqueKey<LinkSubscriptionRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_F;
    }

    @Override
    @NotNull
    public List<ForeignKey<LinkSubscriptionRecord, ?>> getReferences() {
        return Arrays.asList(Keys.CONSTRAINT_F2);
    }

    private transient TgUser _tgUser;

    /**
     * Get the implicit join path to the <code>PUBLIC.TG_USER</code> table.
     */
    public TgUser tgUser() {
        if (_tgUser == null)
            _tgUser = new TgUser(this, Keys.CONSTRAINT_F2);

        return _tgUser;
    }

    @Override
    @NotNull
    public LinkSubscription as(String alias) {
        return new LinkSubscription(DSL.name(alias), this);
    }

    @Override
    @NotNull
    public LinkSubscription as(Name alias) {
        return new LinkSubscription(alias, this);
    }

    @Override
    @NotNull
    public LinkSubscription as(Table<?> alias) {
        return new LinkSubscription(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public LinkSubscription rename(String name) {
        return new LinkSubscription(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public LinkSubscription rename(Name name) {
        return new LinkSubscription(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public LinkSubscription rename(Table<?> name) {
        return new LinkSubscription(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row4<Long, Long, String, LocalDateTime> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function4<? super Long, ? super Long, ? super String, ? super LocalDateTime, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function4<? super Long, ? super Long, ? super String, ? super LocalDateTime, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
