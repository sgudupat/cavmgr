package com.snithik.cavmgr.app.service.dto;

import java.io.Serializable;

public class ClientPasswordDTO  implements Serializable {
	 private static final long serialVersionUID = 1L;

	    private Long clientPasswordId;

	    private String accountType;

	    private String userName;
	    
	    private String password;

	    public Long getClientPasswordId() {
			return clientPasswordId;
		}

		public void setClientPasswordId(Long clientPasswordId) {
			this.clientPasswordId = clientPasswordId;
		}

		public String getAccountType() {
			return accountType;
		}

		public void setAccountType(String accountType) {
			this.accountType = accountType;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		@Override
		public String toString() {
			return "ClientPasswordDTO [clientPasswordId=" + clientPasswordId + ", accountType=" + accountType
					+ ", userName=" + userName + ", password=" + password + "]";
		}

		
	    

}
