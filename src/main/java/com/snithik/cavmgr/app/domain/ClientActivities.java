package com.snithik.cavmgr.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ClientActivities.
 */
@Entity
@Table(name = "client_activities")
public class ClientActivities implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activity_name")
    private String activityName;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "applicability")
    private Boolean applicability;

    @Column(name = "org_id")
    private Long orgId;

    @ManyToOne
    private Employees user;

    @ManyToOne
    private Clients client;

    @ManyToOne
    private Employees responsibleMgr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public ClientActivities activityName(String activityName) {
        this.activityName = activityName;
        return this;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Integer getPriority() {
        return priority;
    }

    public ClientActivities priority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean isApplicability() {
        return applicability;
    }

    public ClientActivities applicability(Boolean applicability) {
        this.applicability = applicability;
        return this;
    }

    public void setApplicability(Boolean applicability) {
        this.applicability = applicability;
    }

    public Long getOrgId() {
        return orgId;
    }

    public ClientActivities orgId(Long orgId) {
        this.orgId = orgId;
        return this;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Employees getUser() {
        return user;
    }

    public ClientActivities user(Employees employees) {
        this.user = employees;
        return this;
    }

    public void setUser(Employees employees) {
        this.user = employees;
    }

    public Clients getClient() {
        return client;
    }

    public ClientActivities client(Clients clients) {
        this.client = clients;
        return this;
    }

    public void setClient(Clients clients) {
        this.client = clients;
    }

    public Employees getResponsibleMgr() {
        return responsibleMgr;
    }

    public ClientActivities responsibleMgr(Employees employees) {
        this.responsibleMgr = employees;
        return this;
    }

    public void setResponsibleMgr(Employees employees) {
        this.responsibleMgr = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClientActivities clientActivities = (ClientActivities) o;
        if (clientActivities.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, clientActivities.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClientActivities{" +
            "id=" + id +
            ", activityName='" + activityName + "'" +
            ", priority='" + priority + "'" +
            ", applicability='" + applicability + "'" +
            ", orgId='" + orgId + "'" +
            '}';
    }
}
