package com.snithik.cavmgr.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A EmployeeRoles.
 */
@Entity
@Table(name = "employee_roles")
public class EmployeeRoles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name")
    private String roleName;

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
    private Employees user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public EmployeeRoles roleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public EmployeeRoles creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public EmployeeRoles createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public EmployeeRoles lastUpdateDate(ZonedDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    public void setLastUpdateDate(ZonedDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getLastUpatedBy() {
        return lastUpatedBy;
    }

    public EmployeeRoles lastUpatedBy(String lastUpatedBy) {
        this.lastUpatedBy = lastUpatedBy;
        return this;
    }

    public void setLastUpatedBy(String lastUpatedBy) {
        this.lastUpatedBy = lastUpatedBy;
    }

    public Long getOrgId() {
        return orgId;
    }

    public EmployeeRoles orgId(Long orgId) {
        this.orgId = orgId;
        return this;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Employees getUser() {
        return user;
    }

    public EmployeeRoles user(Employees employees) {
        this.user = employees;
        return this;
    }

    public void setUser(Employees employees) {
        this.user = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmployeeRoles employeeRoles = (EmployeeRoles) o;
        if (employeeRoles.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, employeeRoles.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EmployeeRoles{" +
            "id=" + id +
            ", roleName='" + roleName + "'" +
            ", creationDate='" + creationDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", lastUpdateDate='" + lastUpdateDate + "'" +
            ", lastUpatedBy='" + lastUpatedBy + "'" +
            ", orgId='" + orgId + "'" +
            '}';
    }
}
