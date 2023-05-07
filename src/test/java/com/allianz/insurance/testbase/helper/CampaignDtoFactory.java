package com.allianz.insurance.testbase.helper;

import com.allianz.insurance.dto.CampaignDto;
import com.allianz.insurance.enums.CampaignCategory;
import com.allianz.insurance.enums.CampaignStatus;

public class CampaignDtoFactory {
    public CampaignDto campaignDto() {
        CampaignDto dto = new CampaignDto();
        dto.setAdvertTitle("Test Insurance Campaign");
        dto.setCampaignDetail("this message contains the details of this campaign");
        dto.setCampaignCategory(CampaignCategory.OSS);
        dto.setCampaignStatus(CampaignStatus.WAITING_FOR_APPROVAL);
        dto.setId(1L);

        return dto;
    }

    public CampaignDto campaignActivatedDto() {
        CampaignDto dto = new CampaignDto();
        dto.setAdvertTitle("Test Insurance Campaign");
        dto.setCampaignDetail("this message contains the details of this campaign");
        dto.setCampaignCategory(CampaignCategory.OSS);
        dto.setCampaignStatus(CampaignStatus.ACTIVE);
        dto.setId(1L);

        return dto;
    }
}
