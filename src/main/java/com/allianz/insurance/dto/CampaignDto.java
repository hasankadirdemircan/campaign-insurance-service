package com.allianz.insurance.dto;

import com.allianz.insurance.enums.CampaignCategory;
import com.allianz.insurance.enums.CampaignStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampaignDto {
    private Long id;
    private String campaignTitle;
    private String campaignDetail;
    private CampaignCategory campaignCategory;
    private CampaignStatus campaignStatus;
}
