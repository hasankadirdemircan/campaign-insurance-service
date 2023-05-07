package com.allianz.insurance.service.impl;

import com.allianz.insurance.dto.CampaignDto;
import com.allianz.insurance.enums.CampaignCategory;
import com.allianz.insurance.enums.CampaignStatus;
import com.allianz.insurance.model.Campaign;
import com.allianz.insurance.model.CampaignHistory;
import com.allianz.insurance.model.JwtInfo;
import com.allianz.insurance.repository.CampaignHistoryRepository;
import com.allianz.insurance.repository.CampaignRepository;
import com.allianz.insurance.response.CampaignResponse;
import com.allianz.insurance.service.CampaignService;
import com.allianz.insurance.util.Mapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Optional;

@Service
@Transactional
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private CampaignHistoryRepository campaignHistoryRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public CampaignResponse createCampaign(String jwt, CampaignDto campaignDto) {
        String username = getUsernameInJwt(jwt);
        Campaign campaignModel = mapper.dto2Model(username, campaignDto);
        Optional<Campaign> campaignExist = campaignRepository.findCampaignByCampaignCategoryAndAdvertTitleAndCampaignDetail(
                            campaignModel.getCampaignCategory(), campaignModel.getAdvertTitle(), campaignModel.getCampaignDetail());

        Campaign saveCampaign;
        if(campaignExist.isPresent()) {
            Campaign campaignPresent = campaignExist.get();
            campaignPresent.setCampaignStatus(CampaignStatus.REPETITIVE);
            campaignPresent.setUpdatedBy(username);
            saveCampaign = campaignPresent;
        }else {
            saveCampaign = setCampaignCategory(campaignModel);
        }
        Campaign savedCampaign = campaignRepository.save(saveCampaign);
        saveCampaignHistory(username, savedCampaign);
       return buildCampaignToCampaignResponse(savedCampaign);
    }

    private Campaign setCampaignCategory(Campaign campaign) {
        if(campaign.getCampaignCategory() == CampaignCategory.HAYAT_INSURANCE) {
            campaign.setCampaignStatus(CampaignStatus.ACTIVE);
        }else {
            campaign.setCampaignStatus(CampaignStatus.WAITING_FOR_APPROVAL);
        }
        return campaign;
    }

    private CampaignResponse buildCampaignToCampaignResponse(Campaign campaign) {
        return CampaignResponse.builder()
                .campaign(mapper.model2Dto(campaign))
                .build();
    }

    private String getUsernameInJwt(String jwt){
        String[] chunks = jwt.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        ObjectMapper mapper = new ObjectMapper();
        JwtInfo jwtInfo;
        try {
            jwtInfo = mapper.readValue(new String(decoder.decode(chunks[1])), JwtInfo.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jwtInfo.getEmail();
    }

    private void saveCampaignHistory(String username, Campaign campaign) {
        campaignHistoryRepository.save(buildCampaignHistory(username, campaign));
    }

    private CampaignHistory buildCampaignHistory(String username, Campaign campaign) {
        return CampaignHistory.builder()
                .campaignID(campaign.getId())
                .campaignStatus(campaign.getCampaignStatus())
                .modifiedUser(username)
                .modifiedDate(LocalDate.now())
                .build();
    }
}
