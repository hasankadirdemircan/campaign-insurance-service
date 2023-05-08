package com.allianz.insurance.dto;

import com.allianz.insurance.enums.CampaignStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CampaignHistoryDto {
    private Long id;
    private Long campaignID;
    private CampaignStatus campaignStatus;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifiedDate;
    private String modifiedUser;
}
