package com.allianz.insurance.service;

import com.allianz.insurance.dto.CampaignDto;
import com.allianz.insurance.model.Campaign;

public interface CampaignService {
    Campaign createCampaign(CampaignDto campaignDto);
}
