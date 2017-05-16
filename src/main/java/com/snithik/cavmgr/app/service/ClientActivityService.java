package com.snithik.cavmgr.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snithik.cavmgr.app.domain.ClientActivities;
import com.snithik.cavmgr.app.domain.Clients;
import com.snithik.cavmgr.app.domain.Employees;
import com.snithik.cavmgr.app.domain.User;
import com.snithik.cavmgr.app.repository.ClientActivitiesRepository;
import com.snithik.cavmgr.app.service.dto.ClientActivityDTO;
import com.snithik.cavmgr.app.service.mapper.ClientActivityMapper;

@Service
@Transactional
public class ClientActivityService {
	
	private final Logger log = LoggerFactory.getLogger(ClientActivityService.class);
	
	@Autowired
	private ClientActivityMapper clientActivityMapper;
	@Autowired
    private	ClientActivitiesRepository clientActivitiesRepository;

	
	public List<ClientActivityDTO> createClientActivities(List<ClientActivityDTO> clientActivityDTOs,
			Employees employees, Clients savedclient,User user) throws Exception {

		List<ClientActivityDTO> savedClientActivityDTOs = new ArrayList<ClientActivityDTO>();
		clientActivityDTOs.forEach(clientActivityDTO -> {
			log.debug("REST request to save ClientActivities : {}", clientActivityDTO);
			HashMap<Long, String> ClientActivityErrors = new HashMap<Long, String>();
			if (clientActivityDTO.getClientActivityId() != null) {
				ClientActivityErrors.put(clientActivityDTO.getClientActivityId(), "idexists -Ignoring the record");
				return;
			}
			ClientActivities clientActivities = clientActivityMapper
					.clientActivityDTOToClientActivities(clientActivityDTO);
			clientActivities.setResponsibleMgr(employees);
			clientActivities.setClient(savedclient);
			clientActivities.orgId(user.getId());
			ClientActivities savedClientActivities = clientActivitiesRepository.save(clientActivities);
			ClientActivityDTO savedClientPasswordDTO = clientActivityMapper
					.clientActivitiesToClientActivityDTO(savedClientActivities);
			savedClientActivityDTOs.add(savedClientPasswordDTO);
		});

		return savedClientActivityDTOs;
	}
	
	public List<ClientActivityDTO> updateClientActivities(List<ClientActivityDTO> clientActivityDTOs,
			Employees employees, Clients savedclient,User user) throws Exception {

		List<ClientActivityDTO> savedClientActivityDTOs = new ArrayList<ClientActivityDTO>();
		clientActivityDTOs.forEach(clientActivityDTO -> {
			log.debug("REST request to save ClientActivities : {}", clientActivityDTO);
		//	HashMap<Long, String> ClientActivityErrors = new HashMap<Long, String>();
			if (clientActivityDTO.getClientActivityId() == null) {
				try {
					createClientActivities(clientActivityDTO,employees,savedclient,user);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			ClientActivities clientActivities = clientActivityMapper
					.clientActivityDTOToClientActivities(clientActivityDTO);
			clientActivities.setResponsibleMgr(employees);
			clientActivities.setClient(savedclient);
			clientActivities.orgId(user.getId());
			ClientActivities savedClientActivities = clientActivitiesRepository.save(clientActivities);
			ClientActivityDTO savedClientPasswordDTO = clientActivityMapper
					.clientActivitiesToClientActivityDTO(savedClientActivities);
			savedClientActivityDTOs.add(savedClientPasswordDTO);
		});

		return savedClientActivityDTOs;
	}

	private ClientActivityDTO createClientActivities(ClientActivityDTO clientActivityDTO, Employees employees, Clients savedclient,User user) {
		// TODO Auto-generated method stub
		ClientActivities clientActivities = clientActivityMapper
				.clientActivityDTOToClientActivities(clientActivityDTO);
		clientActivities.setResponsibleMgr(employees);
		clientActivities.setClient(savedclient);
		clientActivities.orgId(user.getId());
		ClientActivities savedClientActivities = clientActivitiesRepository.save(clientActivities);
		ClientActivityDTO savedClientPasswordDTO = clientActivityMapper
				.clientActivitiesToClientActivityDTO(savedClientActivities);
		return savedClientPasswordDTO;
	}


}
