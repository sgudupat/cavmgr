package com.snithik.cavmgr.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ClientActivityDetails.
 */
@Entity
@Table(name = "client_activity_details")
public class ClientActivityDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "due_date")
    private String dueDate;

    @Column(name = "internal_due_date")
    private String internalDueDate;

    @Column(name = "status")
    private String status;

    @Column(name = "comments")
    private String comments;

    @Column(name = "work_status")
    private String workStatus;

    @Column(name = "month_year")
    private String monthYear;

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

    @ManyToOne
    private Employees responsibleMgr;

    @ManyToOne
    private Clients client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ClientActivityDetails name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public ClientActivityDetails dueDate(String dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getInternalDueDate() {
        return internalDueDate;
    }

    public ClientActivityDetails internalDueDate(String internalDueDate) {
        this.internalDueDate = internalDueDate;
        return this;
    }

    public void setInternalDueDate(String internalDueDate) {
        this.internalDueDate = internalDueDate;
    }

    public String getStatus() {
        return status;
    }

    public ClientActivityDetails status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public ClientActivityDetails comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public ClientActivityDetails workStatus(String workStatus) {
        this.workStatus = workStatus;
        return this;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public ClientActivityDetails monthYear(String monthYear) {
        this.monthYear = monthYear;
        return this;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public ClientActivityDetails creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ClientActivityDetails createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public ClientActivityDetails lastUpdateDate(ZonedDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    public void setLastUpdateDate(ZonedDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getLastUpatedBy() {
        return lastUpatedBy;
    }

    public ClientActivityDetails lastUpatedBy(String lastUpatedBy) {
        this.lastUpatedBy = lastUpatedBy;
        return this;
    }

    public void setLastUpatedBy(String lastUpatedBy) {
        this.lastUpatedBy = lastUpatedBy;
    }

    public Long getOrgId() {
        return orgId;
    }

    public ClientActivityDetails orgId(Long orgId) {
        this.orgId = orgId;
        return this;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Employees getResponsibleMgr() {
        return responsibleMgr;
    }

    public ClientActivityDetails responsibleMgr(Employees employees) {
        this.responsibleMgr = employees;
        return this;
    }

    public void setResponsibleMgr(Employees employees) {
        this.responsibleMgr = employees;
    }

    public Clients getClient() {
        return client;
    }

    public ClientActivityDetails client(Clients clients) {
        this.client = clients;
        return this;
    }

    public void setClient(Clients clients) {
        this.client = clients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClientActivityDetails clientActivityDetails = (ClientActivityDetails) o;
        if (clientActivityDetails.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, clientActivityDetails.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClientActivityDetails{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", dueDate='" + dueDate + "'" +
            ", internalDueDate='" + internalDueDate + "'" +
            ", status='" + status + "'" +
            ", comments='" + comments + "'" +
            ", workStatus='" + workStatus + "'" +
            ", monthYear='" + monthYear + "'" +
            ", creationDate='" + creationDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", lastUpdateDate='" + lastUpdateDate + "'" +
            ", lastUpatedBy='" + lastUpatedBy + "'" +
            ", orgId='" + orgId + "'" +
            '}';
    }
}
