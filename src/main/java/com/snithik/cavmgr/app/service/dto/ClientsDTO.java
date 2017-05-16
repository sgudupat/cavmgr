package com.snithik.cavmgr.app.service.dto;


import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;


/**
 * A Request Client.
 */
public class ClientsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    
    private Long clientId;

  
    private String entityName;

  
    private String contactPerson;

  
    private String emailAddress;

 
    private Long officePhone;

   
    private Long mobilePhone;

      private String billingPeriod;

   
    private String panNumber;

 
    private String tanNumber;

	private ZonedDateTime dateOfIncorp;

   
    private String grade;
    
    private Long responsibleMgr;
    
    private List<ClientPasswordDTO> clientPasswords;
    
    private List<ClientActivityDTO> ClientActivities;
    
   

    
    public List<ClientActivityDTO> getClientActivities() {
		return ClientActivities;
	}

	public void setClientActivities(List<ClientActivityDTO> clientActivities) {
		ClientActivities = clientActivities;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Long getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(Long officePhone) {
		this.officePhone = officePhone;
	}

	public Long getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(Long mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getBillingPeriod() {
		return billingPeriod;
	}

	public void setBillingPeriod(String billingPeriod) {
		this.billingPeriod = billingPeriod;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getTanNumber() {
		return tanNumber;
	}

	public void setTanNumber(String tanNumber) {
		this.tanNumber = tanNumber;
	}

	public ZonedDateTime getDateOfIncorp() {
		return dateOfIncorp;
	}

	public void setDateOfIncorp(ZonedDateTime dateOfIncorp) {
		this.dateOfIncorp = dateOfIncorp;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
   
	 public Long getResponsibleMgr() {
			return responsibleMgr;
		}

		public void setResponsibleMgr(Long responsibleMgr) {
			this.responsibleMgr = responsibleMgr;
		}

		public List<ClientPasswordDTO> getClientPasswords() {
			return clientPasswords;
		}

		public void setClientPasswords(List<ClientPasswordDTO> clientPasswords) {
			this.clientPasswords = clientPasswords;
		}

	
	
  
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClientsDTO clients = (ClientsDTO) o;
        if (clients.clientId == null || clientId == null) {
            return false;
        }
        return Objects.equals(clientId, clients.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(clientId);
    }

    @Override
    public String toString() {
        return "Clients{" +
            "id=" + clientId +
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
            ", responsibleMgr = '" +responsibleMgr + "'" +
            '}';
    }
}
