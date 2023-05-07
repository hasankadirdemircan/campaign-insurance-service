package com.allianz.insurance.util;

import com.allianz.insurance.dto.CampaignDto;
import com.allianz.insurance.model.Campaign;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
@Configuration
public class Mapper {
    @Autowired
    private ModelMapper modelMapper;

    /*
    Campaign
     */
    public Campaign dto2Model(String username, CampaignDto dto){
        return dto2Model(username, dto, null);
    }

    public Campaign dto2Model(String username, CampaignDto dto, Campaign model){
        if(model == null){
            model = new Campaign();
        }
        model = modelMapper.map(dto, Campaign.class);
        model.setCreatedBy(username);
        return model;
    }

    public CampaignDto model2Dto(Campaign model) {
        return model2Dto(model, null);
    }
    public CampaignDto model2Dto(Campaign model, CampaignDto dto) {
        if(dto == null) {
            dto = new CampaignDto();
        }
        dto = modelMapper.map(model, CampaignDto.class);
        return dto;
    }
}
