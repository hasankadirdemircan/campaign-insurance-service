package com.allianz.insurance.service;

import com.allianz.insurance.dto.CampaignDto;
import com.allianz.insurance.model.Campaign;
import com.allianz.insurance.response.CampaignResponse;

public interface CampaignService {
    CampaignResponse createCampaign(String jwt, CampaignDto campaignDto);
}
