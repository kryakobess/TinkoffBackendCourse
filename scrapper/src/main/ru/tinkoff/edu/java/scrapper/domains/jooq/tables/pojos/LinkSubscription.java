/*
 * This file is generated by jOOQ.
 */

package scrapper.domains.jooq.tables.pojos;


import jakarta.validation.constraints.Size;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


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
public class LinkSubscription implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long tguserid;
    private String link;
    private LocalDateTime lastupdate;

    public LinkSubscription() {}

    public LinkSubscription(LinkSubscription value) {
        this.id = value.id;
        this.tguserid = value.tguserid;
        this.link = value.link;
        this.lastupdate = value.lastupdate;
    }

    @ConstructorProperties({ "id", "tguserid", "link", "lastupdate" })
    public LinkSubscription(
        @Nullable Long id,
        @NotNull Long tguserid,
        @NotNull String link,
        @Nullable LocalDateTime lastupdate
    ) {
        this.id = id;
        this.tguserid = tguserid;
        this.link = link;
        this.lastupdate = lastupdate;
    }

    /**
     * Getter for <code>LINK_SUBSCRIPTION.ID</code>.
     */
    @Nullable
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>LINK_SUBSCRIPTION.ID</code>.
     */
    public void setId(@Nullable Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>LINK_SUBSCRIPTION.TGUSERID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getTguserid() {
        return this.tguserid;
    }

    /**
     * Setter for <code>LINK_SUBSCRIPTION.TGUSERID</code>.
     */
    public void setTguserid(@NotNull Long tguserid) {
        this.tguserid = tguserid;
    }

    /**
     * Getter for <code>LINK_SUBSCRIPTION.LINK</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 256)
    @NotNull
    public String getLink() {
        return this.link;
    }

    /**
     * Setter for <code>LINK_SUBSCRIPTION.LINK</code>.
     */
    public void setLink(@NotNull String link) {
        this.link = link;
    }

    /**
     * Getter for <code>LINK_SUBSCRIPTION.LASTUPDATE</code>.
     */
    @Nullable
    public LocalDateTime getLastupdate() {
        return this.lastupdate;
    }

    /**
     * Setter for <code>LINK_SUBSCRIPTION.LASTUPDATE</code>.
     */
    public void setLastupdate(@Nullable LocalDateTime lastupdate) {
        this.lastupdate = lastupdate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final LinkSubscription other = (LinkSubscription) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.tguserid == null) {
            if (other.tguserid != null)
                return false;
        }
        else if (!this.tguserid.equals(other.tguserid))
            return false;
        if (this.link == null) {
            if (other.link != null)
                return false;
        }
        else if (!this.link.equals(other.link))
            return false;
        if (this.lastupdate == null) {
            if (other.lastupdate != null)
                return false;
        }
        else if (!this.lastupdate.equals(other.lastupdate))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.tguserid == null) ? 0 : this.tguserid.hashCode());
        result = prime * result + ((this.link == null) ? 0 : this.link.hashCode());
        result = prime * result + ((this.lastupdate == null) ? 0 : this.lastupdate.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("LinkSubscription (");

        sb.append(id);
        sb.append(", ").append(tguserid);
        sb.append(", ").append(link);
        sb.append(", ").append(lastupdate);

        sb.append(")");
        return sb.toString();
    }
}
