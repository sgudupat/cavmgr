package com.snithik.cavmgr.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A TripleSetConfig.
 */
@Entity
@Table(name = "triple_set_config")
public class TripleSetConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "control")
    private String control;

    @Column(name = "parent")
    private String parent;

    @Column(name = "child")
    private String child;

    @Column(name = "config")
    private String config;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_update_date")
    private ZonedDateTime lastUpdateDate;

    @Column(name = "last_upated_by")
    private String lastUpatedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getControl() {
        return control;
    }

    public TripleSetConfig control(String control) {
        this.control = control;
        return this;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getParent() {
        return parent;
    }

    public TripleSetConfig parent(String parent) {
        this.parent = parent;
        return this;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getChild() {
        return child;
    }

    public TripleSetConfig child(String child) {
        this.child = child;
        return this;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getConfig() {
        return config;
    }

    public TripleSetConfig config(String config) {
        this.config = config;
        return this;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public TripleSetConfig creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public TripleSetConfig createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public TripleSetConfig lastUpdateDate(ZonedDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    public void setLastUpdateDate(ZonedDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getLastUpatedBy() {
        return lastUpatedBy;
    }

    public TripleSetConfig lastUpatedBy(String lastUpatedBy) {
        this.lastUpatedBy = lastUpatedBy;
        return this;
    }

    public void setLastUpatedBy(String lastUpatedBy) {
        this.lastUpatedBy = lastUpatedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TripleSetConfig tripleSetConfig = (TripleSetConfig) o;
        if (tripleSetConfig.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tripleSetConfig.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TripleSetConfig{" +
            "id=" + id +
            ", control='" + control + "'" +
            ", parent='" + parent + "'" +
            ", child='" + child + "'" +
            ", config='" + config + "'" +
            ", creationDate='" + creationDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", lastUpdateDate='" + lastUpdateDate + "'" +
            ", lastUpatedBy='" + lastUpatedBy + "'" +
            '}';
    }
}
