package com.allianz.insurance.dto;

import com.allianz.insurance.enums.CampaignCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampaignDto {
    private String advertTitle;
    private String campaignDetail;
    private CampaignCategory campaignCategory;
}
