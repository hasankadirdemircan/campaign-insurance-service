package com.allianz.insurance.dto;

import com.allianz.insurance.enums.CampaignStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CampaignHistoryDto {
    private Long id;
    private Long campaignID;
    private CampaignStatus campaignStatus;
    private LocalDate modifiedDate;
    private String modifiedUser;
}
