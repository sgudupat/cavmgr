package com.snithik.cavmgr.app.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.snithik.cavmgr.app.domain.Clients;
import com.snithik.cavmgr.app.service.dto.ClientsDTO;

@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public abstract class ClientMapper {


    @Mapping(source = "clients.id", target = "clientId")
    public abstract ClientsDTO clientsToClientsDTO(Clients clients);

    @Mapping(source = "clientId", target = "clients.id")
    public abstract Clients clientsDTOToClients(ClientsDTO clientsDTO);

}
