package com.snithik.cavmgr.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Clients.
 */
@Entity
@Table(name = "clients")
public class Clients implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "office_phone")
    private Long officePhone;

    @Column(name = "mobile_phone")
    private Long mobilePhone;

    @Column(name = "billing_period")
    private String billingPeriod;

    @Column(name = "pan_number")
    private String panNumber;

    @Column(name = "tan_number")
    private String tanNumber;

    @Column(name = "date_of_incorp")
    private ZonedDateTime dateOfIncorp;

    @Column(name = "grade")
    private String grade;

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

    public String getEntityName() {
        return entityName;
    }

    public Clients entityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public Clients contactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
        return this;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Clients emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Long getOfficePhone() {
        return officePhone;
    }

    public Clients officePhone(Long officePhone) {
        this.officePhone = officePhone;
        return this;
    }

    public void setOfficePhone(Long officePhone) {
        this.officePhone = officePhone;
    }

    public Long getMobilePhone() {
        return mobilePhone;
    }

    public Clients mobilePhone(Long mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public void setMobilePhone(Long mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getBillingPeriod() {
        return billingPeriod;
    }

    public Clients billingPeriod(String billingPeriod) {
        this.billingPeriod = billingPeriod;
        return this;
    }

    public void setBillingPeriod(String billingPeriod) {
        this.billingPeriod = billingPeriod;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public Clients panNumber(String panNumber) {
        this.panNumber = panNumber;
        return this;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getTanNumber() {
        return tanNumber;
    }

    public Clients tanNumber(String tanNumber) {
        this.tanNumber = tanNumber;
        return this;
    }

    public void setTanNumber(String tanNumber) {
        this.tanNumber = tanNumber;
    }

    public ZonedDateTime getDateOfIncorp() {
        return dateOfIncorp;
    }

    public Clients dateOfIncorp(ZonedDateTime dateOfIncorp) {
        this.dateOfIncorp = dateOfIncorp;
        return this;
    }

    public void setDateOfIncorp(ZonedDateTime dateOfIncorp) {
        this.dateOfIncorp = dateOfIncorp;
    }

    public String getGrade() {
        return grade;
    }

    public Clients grade(String grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Clients creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Clients createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public Clients lastUpdateDate(ZonedDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    public void setLastUpdateDate(ZonedDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getLastUpatedBy() {
        return lastUpatedBy;
    }

    public Clients lastUpatedBy(String lastUpatedBy) {
        this.lastUpatedBy = lastUpatedBy;
        return this;
    }

    public void setLastUpatedBy(String lastUpatedBy) {
        this.lastUpatedBy = lastUpatedBy;
    }

    public Long getOrgId() {
        return orgId;
    }

    public Clients orgId(Long orgId) {
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
        Clients clients = (Clients) o;
        if (clients.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, clients.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Clients{" +
            "id=" + id +
            ", entityName='" + entityName + "'" +
            ", contactPerson='" + contactPerson + "'" +
            ", emailAddress='" + emailAddress + "'" +
            ", officePhone='" + officePhone + "'" +
            ", mobilePhone='" + mobilePhone + "'" +
            ", billingPeriod='" + billingPeriod + "'" +
            ", panNumber='" + panNumber + "'" +
            ", tanNumber='" + tanNumber + "'" +
            ", dateOfIncorp='" + dateOfIncorp + "'" +
            ", grade='" + grade + "'" +
            ", creationDate='" + creationDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", lastUpdateDate='" + lastUpdateDate + "'" +
            ", lastUpatedBy='" + lastUpatedBy + "'" +
            ", orgId='" + orgId + "'" +
            '}';
    }
}
