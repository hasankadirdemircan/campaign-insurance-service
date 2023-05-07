package com.allianz.insurance.service.impl;

import com.allianz.insurance.enums.CampaignCategory;
import com.allianz.insurance.enums.CampaignStatus;
import com.allianz.insurance.model.Campaign;
import com.allianz.insurance.model.CampaignHistory;
import com.allianz.insurance.model.JwtInfo;
import com.allianz.insurance.repository.CampaignHistoryRepository;
import com.allianz.insurance.repository.CampaignRepository;
import com.allianz.insurance.request.CreateCampaignRequest;
import com.allianz.insurance.response.CampaignHistoryResponse;
import com.allianz.insurance.response.CampaignResponse;
import com.allianz.insurance.response.CampaignStatisticsResponse;
import com.allianz.insurance.service.CampaignService;
import com.allianz.insurance.util.Mapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    public CampaignResponse createCampaign(String jwt, CreateCampaignRequest request) {
        String userEmail = getEmailInJwt(jwt);
        Campaign campaignModel = mapper.createCampaignRequestToModel(userEmail, request);
        Campaign campaignExist = campaignRepository.findCampaignByCampaignCategoryAndAdvertTitleAndCampaignDetail(
                            campaignModel.getCampaignCategory(), campaignModel.getAdvertTitle(), campaignModel.getCampaignDetail());

        Campaign saveCampaign = Optional.ofNullable(campaignExist).map(campaign-> {
            campaign.setCampaignStatus(CampaignStatus.REPETITIVE);
            campaign.setUpdatedBy(userEmail);
            return campaign;
        }).orElse(setCampaignCategory(campaignModel));

        Campaign savedCampaign = campaignRepository.save(saveCampaign);
        saveCampaignHistory(userEmail, savedCampaign);
       return buildCampaignToCampaignResponse(savedCampaign);
    }

    @Override
    public CampaignResponse activateCampaign(String jwt, Long campaignID) {
        String userEmail = getEmailInJwt(jwt);
        Campaign campaign = campaignRepository.findCampaignById(campaignID);
        Optional.ofNullable(campaign)
                .filter(c -> c.getCampaignStatus().equals(CampaignStatus.WAITING_FOR_APPROVAL))
                .map(c -> {
                    c.setCampaignStatus(CampaignStatus.ACTIVE);
                    c.setUpdatedBy(userEmail);
                    return c;
                })
                .orElseThrow(() -> new IllegalArgumentException("You can not update the campaign"));
        saveCampaignHistory(userEmail, campaign);
        return buildCampaignToCampaignResponse(campaign);
    }

    @Override
    public CampaignResponse deactivateCampaign(String jwt, Long campaignID) {
        String userEmail = getEmailInJwt(jwt);
        Campaign campaign = campaignRepository.findCampaignById(campaignID);
        Optional.ofNullable(campaign)
                .filter(c -> c.getCampaignStatus().equals(CampaignStatus.ACTIVE) || c.getCampaignStatus().equals(CampaignStatus.WAITING_FOR_APPROVAL))
                .map(c -> {
                    c.setCampaignStatus(CampaignStatus.DEACTIVATED);
                    c.setUpdatedBy(userEmail);
                    return c;
                })
                .orElseThrow(() -> new IllegalArgumentException("You can not update the campaign"));
        saveCampaignHistory(userEmail, campaign);
        return buildCampaignToCampaignResponse(campaign);
    }

    @Override
    public CampaignResponse findCampaignById(String jwt, Long campaignID) {
        Campaign campaign = campaignRepository.findCampaignById(campaignID);
        return Optional.ofNullable(campaign)
                .map(c -> buildCampaignToCampaignResponse(campaign))
                .orElseThrow(() -> new IllegalArgumentException("invalid campaignID"));
    }

    @Override
    public List<CampaignResponse> findAllCampaign(String jwt) {
        return campaignRepository.findAll()
                .stream()
                .map(this::buildCampaignToCampaignResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CampaignHistoryResponse> findCampaignHistoryById(String jwt, Long campaignID) {
        return campaignHistoryRepository.findCampaignHistoriesByCampaignID(campaignID).stream().map(this::buildCampaignHistoryToCampaignHistoryResponse).collect(Collectors.toList());
    }

    @Override
    public List<CampaignStatisticsResponse> getCampaignStatistics(String jwt) {
        Map<CampaignStatus, Long> map = campaignRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Campaign::getCampaignStatus, Collectors.counting()));

        return map.entrySet()
                .stream()
                .map(e -> new CampaignStatisticsResponse(e.getKey().toString(), e.getValue().intValue()))
                .toList();
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

    private CampaignHistoryResponse buildCampaignHistoryToCampaignHistoryResponse(CampaignHistory campaignHistory) {
        return CampaignHistoryResponse.builder()
                .campaignHistory(mapper.model2Dto(campaignHistory))
                .build();
    }

    private String getEmailInJwt(String jwt){
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

    private void saveCampaignHistory(String userEmail, Campaign campaign) {
        campaignHistoryRepository.save(buildCampaignHistory(userEmail, campaign));
    }

    private CampaignHistory buildCampaignHistory(String userEmail, Campaign campaign) {
        return CampaignHistory.builder()
                .campaignID(campaign.getId())
                .campaignStatus(campaign.getCampaignStatus())
                .modifiedUser(userEmail)
                .modifiedDate(LocalDate.now())
                .build();
    }
}
