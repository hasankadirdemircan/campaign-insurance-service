package com.allianz.insurance.service.impl;

import com.allianz.insurance.enums.CampaignCategory;
import com.allianz.insurance.enums.CampaignStatus;
import com.allianz.insurance.exception.CampaignNotFoundException;
import com.allianz.insurance.exception.DefaultExceptionHandler;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final CampaignHistoryRepository campaignHistoryRepository;
    private final Mapper mapper;

    @Autowired
    public CampaignServiceImpl(CampaignRepository campaignRepository,
                               CampaignHistoryRepository campaignHistoryRepository,
                               Mapper mapper) {
        this.campaignRepository = campaignRepository;
        this.campaignHistoryRepository = campaignHistoryRepository;
        this.mapper = mapper;
    }

    @Override
    public CampaignResponse createCampaign(String jwt, CreateCampaignRequest request) {
        Instant start = Instant.now();
        String userEmail = getEmailInJwt(jwt);
        Campaign campaignModel = mapper.createCampaignRequestToModel(userEmail, request);
        Campaign campaignExist = campaignRepository.findCampaignByCampaignCategoryAndCampaignTitleAndCampaignDetail(
                            campaignModel.getCampaignCategory(), campaignModel.getCampaignTitle(), campaignModel.getCampaignDetail());

        Campaign saveCampaign = Optional.ofNullable(campaignExist)
                .map(campaign-> generateRepetitiveCampaign(userEmail,campaign))
                .orElse(setCampaignCategory(campaignModel));

        Campaign savedCampaign = saveCampaign(saveCampaign);
        saveCampaignHistory(userEmail, savedCampaign);
        Instant end = Instant.now();
        durationLog(start, end, "createCampaign");
       return buildCampaignToCampaignResponse(savedCampaign);
    }

    @Override
    public CampaignResponse activateCampaign(String jwt, Long campaignID) {
        Instant start = Instant.now();
        String userEmail = getEmailInJwt(jwt);
        Campaign campaign = campaignExistCheck(campaignID, campaignRepository.findCampaignById(campaignID));
        Optional.of(campaign)
                .filter(c -> c.getCampaignStatus().equals(CampaignStatus.WAITING_FOR_APPROVAL))
                .map(c -> {
                    c.setCampaignStatus(CampaignStatus.ACTIVE);
                    c.setUpdatedBy(userEmail);
                    return c;
                })
                .orElseThrow(() -> new DefaultExceptionHandler("You can not activate the campaign, campaign status is ->  " + campaign.getCampaignStatus()));
        saveCampaignHistory(userEmail, campaign);
        Instant end = Instant.now();
        durationLog(start, end, "activateCampaign");
        return buildCampaignToCampaignResponse(saveCampaign(campaign));
    }

    @Override
    public CampaignResponse deactivateCampaign(String jwt, Long campaignID) {
        Instant start = Instant.now();
        String userEmail = getEmailInJwt(jwt);
        Campaign campaign = campaignExistCheck(campaignID, campaignRepository.findCampaignById(campaignID));
        Optional.of(campaign)
                .filter(c -> c.getCampaignStatus().equals(CampaignStatus.ACTIVE) || c.getCampaignStatus().equals(CampaignStatus.WAITING_FOR_APPROVAL))
                .map(c -> {
                    c.setCampaignStatus(CampaignStatus.DEACTIVATE);
                    c.setUpdatedBy(userEmail);
                    return c;
                })
                .orElseThrow(() -> new DefaultExceptionHandler("You can not deactivate the campaign, campaign status is ->  " + campaign.getCampaignStatus()));
        saveCampaignHistory(userEmail, campaign);
        Instant end = Instant.now();
        durationLog(start, end, "deactivateCampaign");
        return buildCampaignToCampaignResponse(saveCampaign(campaign));
    }

    @Override
    public CampaignResponse findCampaignById(String jwt, Long campaignID) {
        Instant start = Instant.now();
        Campaign campaign = campaignRepository.findCampaignById(campaignID);

        CampaignResponse campaignResponse = Optional.ofNullable(campaign)
                .map(c -> buildCampaignToCampaignResponse(campaign))
                .orElseThrow(() -> new CampaignNotFoundException("campaignID Not Found -> " + campaignID));
        Instant end = Instant.now();
        durationLog(start, end, "findCampaignById");

        return campaignResponse;
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
        Instant start = Instant.now();
        Map<CampaignStatus, Long> map = campaignRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Campaign::getCampaignStatus, Collectors.counting()));

        List<CampaignStatisticsResponse> campaignStatisticsResponseList = map.entrySet()
                .stream()
                .map(e -> new CampaignStatisticsResponse(e.getKey().toString(), e.getValue().intValue()))
                .toList();
        Instant end = Instant.now();
        durationLog(start, end, "findCampaignById");

        return campaignStatisticsResponseList;
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

    private Campaign saveCampaign(Campaign campaign) {
        return campaignRepository.save(campaign);
    }

    private CampaignHistory buildCampaignHistory(String userEmail, Campaign campaign) {
        return CampaignHistory.builder()
                .campaignID(campaign.getId())
                .campaignStatus(campaign.getCampaignStatus())
                .modifiedUser(userEmail)
                .modifiedDate(LocalDate.now())
                .build();
    }

    private void durationLog(Instant start, Instant end, String methodName) {
        long processTime = Duration.between(start, end).toMillis();
        if(processTime > 5) {
            log.warn("process time over 5 milliseconds!! {} -> {} ms", methodName, processTime);
        }
    }

    private Campaign campaignExistCheck(Long campaignID, Campaign campaign) {
        return Optional.ofNullable(campaign).orElseThrow(() -> new CampaignNotFoundException("campaignID Not Found -> " + campaignID));
    }

    private Campaign generateRepetitiveCampaign(String username, Campaign campaign) {
        Campaign repetitveCampaign = new Campaign();
        repetitveCampaign.setCampaignTitle(campaign.getCampaignTitle());
        repetitveCampaign.setCampaignDetail(campaign.getCampaignDetail());
        repetitveCampaign.setCampaignCategory(campaign.getCampaignCategory());
        repetitveCampaign.setCampaignStatus(CampaignStatus.REPETITIVE);
        repetitveCampaign.setUpdatedBy(username);

        return repetitveCampaign;
    }
}
