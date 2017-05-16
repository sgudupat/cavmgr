package com.snithik.cavmgr.app.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.snithik.cavmgr.app.domain.ClientPasswords;
import com.snithik.cavmgr.app.service.dto.ClientPasswordDTO;

@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public abstract class ClientPasswordsMapper {


    @Mapping(source = "clientPasswords.id", target = "clientPasswordId")
    public abstract ClientPasswordDTO clientPasswordsToClientPasswordsDTO(ClientPasswords clientPasswords);

    @Mapping(source = "clientPasswordId", target = "clientPasswords.id")
    public abstract ClientPasswords clientPasswordsDTOToClientPasswords(ClientPasswordDTO clientPasswordDTO);

}
