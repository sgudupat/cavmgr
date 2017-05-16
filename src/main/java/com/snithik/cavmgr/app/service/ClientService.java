package com.snithik.cavmgr.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snithik.cavmgr.app.domain.Clients;
import com.snithik.cavmgr.app.domain.Employees;
import com.snithik.cavmgr.app.domain.User;
import com.snithik.cavmgr.app.repository.ClientsRepository;
import com.snithik.cavmgr.app.repository.EmployeesRepository;
import com.snithik.cavmgr.app.service.dto.ClientActivityDTO;
import com.snithik.cavmgr.app.service.dto.ClientPasswordDTO;
import com.snithik.cavmgr.app.service.dto.ClientsDTO;
import com.snithik.cavmgr.app.service.mapper.ClientMapper;

@Service
@Transactional
public class ClientService {
	private final Logger log = LoggerFactory.getLogger(ClientService.class);
      @Autowired
      private ClientMapper clientMapper;
      @Autowired
      private EmployeesRepository employeesRepository;
      @Autowired
      private ClientActivityService clientActivityService;
      @Autowired
      private ClientPasswordsService clientPasswordsService;
      @Autowired
      private ClientsRepository clientsRepository;
      
	public ClientsDTO createClients(ClientsDTO clientsDTO,User user) throws Exception{
		
		Clients clients = clientMapper.clientsDTOToClients(clientsDTO);
		Employees employees = employeesRepository.findOne(clientsDTO.getResponsibleMgr());
		log.debug("Manager is " + employees.getId());
		List<ClientPasswordDTO> clientPasswordDTOs = clientsDTO.getClientPasswords();
		clients.setOrgId(user.getId());
		Clients savedclient = clientsRepository.save(clients);
		List<ClientActivityDTO> clientActivityDTOs = clientsDTO.getClientActivities();
		List<ClientActivityDTO> savedClientActivityDTOs = clientActivityService
				.createClientActivities(clientActivityDTOs, employees, savedclient,user);

		List<ClientPasswordDTO> savedclientPasswordDTOs = clientPasswordsService
				.createClientPasswords(clientPasswordDTOs, employees, savedclient);
		ClientsDTO result = clientMapper.clientsToClientsDTO(savedclient);
		result.setClientPasswords(savedclientPasswordDTOs);
		result.setClientActivities(savedClientActivityDTOs);

		return result;
		
	}

	public ClientsDTO updateClients(ClientsDTO clientsDTO, User user) throws Exception {
		// TODO Auto-generated method stub
		Clients clients = clientMapper.clientsDTOToClients(clientsDTO);
		Employees employees = employeesRepository.findOne(clientsDTO.getResponsibleMgr());
		log.debug("Manager is " + employees.getId());
		List<ClientPasswordDTO> clientPasswordDTOs = clientsDTO.getClientPasswords();
		clients.setOrgId(user.getId());
		Clients savedclient = clientsRepository.save(clients);
		List<ClientActivityDTO> clientActivityDTOs = clientsDTO.getClientActivities();
		List<ClientActivityDTO> savedClientActivityDTOs = clientActivityService
				.updateClientActivities(clientActivityDTOs, employees, savedclient,user);

		List<ClientPasswordDTO> savedclientPasswordDTOs = clientPasswordsService
				.updateClientPasswords(clientPasswordDTOs, employees, savedclient);
		ClientsDTO result = clientMapper.clientsToClientsDTO(savedclient);
		result.setClientPasswords(savedclientPasswordDTOs);
		result.setClientActivities(savedClientActivityDTOs);

		return result;
	}
}
