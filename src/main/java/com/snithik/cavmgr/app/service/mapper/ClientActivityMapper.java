/**
 * 
 */
package com.snithik.cavmgr.app.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.snithik.cavmgr.app.domain.ClientActivities;
import com.snithik.cavmgr.app.service.dto.ClientActivityDTO;

/**
 * @author sai
 *
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public abstract class ClientActivityMapper {


    @Mapping(source = "ClientActivities.id", target = "clientActivityId")
    public abstract ClientActivityDTO clientActivitiesToClientActivityDTO(ClientActivities ClientActivities);

    @Mapping(source = "clientActivityId", target = "ClientActivities.id")
    public abstract ClientActivities clientActivityDTOToClientActivities(ClientActivityDTO clientActivityDTO);

}

