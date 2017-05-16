package com.snithik.cavmgr.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snithik.cavmgr.app.domain.ClientPasswords;
import com.snithik.cavmgr.app.domain.Clients;
import com.snithik.cavmgr.app.domain.Employees;
import com.snithik.cavmgr.app.domain.User;
import com.snithik.cavmgr.app.repository.ClientPasswordsRepository;
import com.snithik.cavmgr.app.service.dto.ClientPasswordDTO;
import com.snithik.cavmgr.app.service.mapper.ClientPasswordsMapper;

@Service
@Transactional
public class ClientPasswordsService {

	@Autowired
	private ClientPasswordsMapper clientPasswordsMapper;
	@Autowired
	private ClientPasswordsRepository clientPasswordsRepository;
	
	public List<ClientPasswordDTO> createClientPasswords(List<ClientPasswordDTO> clientPasswordDTOs,
			Employees employees, Clients savedclient,User user) {

		List<ClientPasswordDTO> savedclientPasswordDTOs = new ArrayList<ClientPasswordDTO>();
		HashMap<Long,String> ClientPasswordsErrors = new HashMap<Long,String>();
		clientPasswordDTOs.forEach(clientPasswordDTO -> {
			if (clientPasswordDTO.getClientPasswordId() != null) {
				ClientPasswordsErrors.put(clientPasswordDTO.getClientPasswordId(), "idexists -Ignoring the record");
				return;
			}
			ClientPasswords clientPasswords = clientPasswordsMapper
					.clientPasswordsDTOToClientPasswords(clientPasswordDTO);
			clientPasswords.setClient(savedclient);
			clientPasswords.setOrgId(user.getId());
			ClientPasswords savedClientPasswords = clientPasswordsRepository.save(clientPasswords);
			ClientPasswordDTO savedClientPasswordDTO = clientPasswordsMapper
					.clientPasswordsToClientPasswordsDTO(savedClientPasswords);
			savedclientPasswordDTOs.add(savedClientPasswordDTO);

		});

		return savedclientPasswordDTOs;

	}

	public List<ClientPasswordDTO> updateClientPasswords(List<ClientPasswordDTO> clientPasswordDTOs,
			Employees employees, Clients savedclient,User user) {
		// TODO Auto-generated method stub
		List<ClientPasswordDTO> savedclientPasswordDTOs = new ArrayList<ClientPasswordDTO>();

		clientPasswordDTOs.forEach(clientPasswordDTO -> {
			ClientPasswordDTO savedClientPasswordDTO = new ClientPasswordDTO();
			if (clientPasswordDTO.getClientPasswordId() == null) {
				savedClientPasswordDTO = createClientPasswords(clientPasswordDTO, employees, savedclient,user);
			} else {
				ClientPasswords clientPasswords = clientPasswordsMapper
						.clientPasswordsDTOToClientPasswords(clientPasswordDTO);
				clientPasswords.setClient(savedclient);
				clientPasswords.setOrgId(user.getId());
				ClientPasswords savedClientPasswords = clientPasswordsRepository.save(clientPasswords);
				savedClientPasswordDTO = clientPasswordsMapper
						.clientPasswordsToClientPasswordsDTO(savedClientPasswords);
			}
			savedclientPasswordDTOs.add(savedClientPasswordDTO);

		});

		return savedclientPasswordDTOs;

	}

	public ClientPasswordDTO createClientPasswords(ClientPasswordDTO clientPasswordDTO, Employees employees,
			Clients savedclient,User user) {

		ClientPasswords clientPasswords = clientPasswordsMapper.clientPasswordsDTOToClientPasswords(clientPasswordDTO);
		clientPasswords.setClient(savedclient);
		clientPasswords.setOrgId(user.getId());
		ClientPasswords savedClientPasswords = clientPasswordsRepository.save(clientPasswords);
		ClientPasswordDTO savedClientPasswordDTO = clientPasswordsMapper
				.clientPasswordsToClientPasswordsDTO(savedClientPasswords);

		return savedClientPasswordDTO;

	}

}
