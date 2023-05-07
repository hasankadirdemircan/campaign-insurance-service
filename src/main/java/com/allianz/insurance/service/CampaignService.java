package com.allianz.insurance.service;

import com.allianz.insurance.dto.CampaignDto;
import com.allianz.insurance.request.CreateCampaignRequest;
import com.allianz.insurance.response.CampaignResponse;

import java.util.List;

public interface CampaignService {
    CampaignResponse createCampaign(String jwt, CreateCampaignRequest request);

    CampaignResponse activateCampaign(String jwt, Long campaignID);

    CampaignResponse deactivateCampaign(String jwt, Long campaignID);

    CampaignResponse findCampaignById(String jwt, Long campaignID);

    List<CampaignResponse> findAllCampaign(String jwt);
}
