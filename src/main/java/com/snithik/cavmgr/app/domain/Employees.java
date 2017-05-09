package com.snithik.cavmgr.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Employees.
 */
@Entity
@Table(name = "employees")
public class Employees implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "mobile_phone")
    private Long mobilePhone;

    @Column(name = "status")
    private String status;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_update_date")
    private ZonedDateTime lastUpdateDate;

    @Column(name = "last_upated_by")
    private String lastUpatedBy;

    @Column(name = "org_id")
    private Long orgId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Employees name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public Employees password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Employees emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Long getMobilePhone() {
        return mobilePhone;
    }

    public Employees mobilePhone(Long mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public void setMobilePhone(Long mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getStatus() {
        return status;
    }

    public Employees status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Employees creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Employees createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public Employees lastUpdateDate(ZonedDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    public void setLastUpdateDate(ZonedDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getLastUpatedBy() {
        return lastUpatedBy;
    }

    public Employees lastUpatedBy(String lastUpatedBy) {
        this.lastUpatedBy = lastUpatedBy;
        return this;
    }

    public void setLastUpatedBy(String lastUpatedBy) {
        this.lastUpatedBy = lastUpatedBy;
    }

    public Long getOrgId() {
        return orgId;
    }

    public Employees orgId(Long orgId) {
        this.orgId = orgId;
        return this;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employees employees = (Employees) o;
        if (employees.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, employees.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Employees{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", password='" + password + "'" +
            ", emailAddress='" + emailAddress + "'" +
            ", mobilePhone='" + mobilePhone + "'" +
            ", status='" + status + "'" +
            ", creationDate='" + creationDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", lastUpdateDate='" + lastUpdateDate + "'" +
            ", lastUpatedBy='" + lastUpatedBy + "'" +
            ", orgId='" + orgId + "'" +
            '}';
    }
}
