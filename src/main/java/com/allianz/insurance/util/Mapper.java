package com.allianz.insurance.util;

import com.allianz.insurance.dto.CampaignDto;
import com.allianz.insurance.dto.CampaignHistoryDto;
import com.allianz.insurance.model.Campaign;
import com.allianz.insurance.model.CampaignHistory;
import com.allianz.insurance.request.CreateCampaignRequest;
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

    public Campaign createCampaignRequestToModel(String userEmail, CreateCampaignRequest request) {
        Campaign campaign = new Campaign();
        campaign.setCreatedBy(userEmail);
        campaign.setCampaignTitle(request.getCampaignTitle());
        campaign.setCampaignDetail(request.getCampaignDetail());
        campaign.setCampaignCategory(request.getCampaignCategory());

        return campaign;
    }

    /*
   Campaign History
    */
    public CampaignHistory dto2Model(CampaignHistoryDto dto){
        return dto2Model(dto, null);
    }

    public CampaignHistory dto2Model(CampaignHistoryDto dto, CampaignHistory model){
        if(model == null){
            model = new CampaignHistory();
        }
        model = modelMapper.map(dto, CampaignHistory.class);
        return model;
    }

    public CampaignHistoryDto model2Dto(CampaignHistory model) {
        return model2Dto(model, null);
    }
    public CampaignHistoryDto model2Dto(CampaignHistory model, CampaignHistoryDto dto) {
        if(dto == null) {
            dto = new CampaignHistoryDto();
        }
        dto = modelMapper.map(model, CampaignHistoryDto.class);
        return dto;
    }

}
