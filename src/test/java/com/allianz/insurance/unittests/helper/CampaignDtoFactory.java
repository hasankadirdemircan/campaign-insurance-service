package com.allianz.insurance.unittests.helper;

import com.allianz.insurance.dto.CampaignDto;
import com.allianz.insurance.dto.CampaignHistoryDto;
import com.allianz.insurance.enums.CampaignCategory;
import com.allianz.insurance.enums.CampaignStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CampaignDtoFactory {
    public CampaignDto campaignDto() {
        CampaignDto dto = new CampaignDto();
        dto.setCampaignTitle("Test Insurance Campaign");
        dto.setCampaignDetail("this message contains the details of this campaign");
        dto.setCampaignCategory(CampaignCategory.OSS);
        dto.setCampaignStatus(CampaignStatus.WAITING_FOR_APPROVAL);
        dto.setId(1L);

        return dto;
    }

    public CampaignDto campaignActivatedDto() {
        CampaignDto dto = new CampaignDto();
        dto.setCampaignTitle("Test Insurance Campaign");
        dto.setCampaignDetail("this message contains the details of this campaign");
        dto.setCampaignCategory(CampaignCategory.OSS);
        dto.setCampaignStatus(CampaignStatus.ACTIVE);
        dto.setId(1L);

        return dto;
    }

    public CampaignDto campaignDeactivatedDto() {
        CampaignDto dto = new CampaignDto();
        dto.setCampaignTitle("Test Insurance Campaign");
        dto.setCampaignDetail("this message contains the details of this campaign");
        dto.setCampaignCategory(CampaignCategory.OSS);
        dto.setCampaignStatus(CampaignStatus.DEACTIVATE);
        dto.setId(1L);

        return dto;
    }

    public CampaignHistoryDto campaignHistoryDto() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate modifiedDate = LocalDate.parse("08-06-2023",format);

        CampaignHistoryDto campaignHistoryDto = new CampaignHistoryDto();
        campaignHistoryDto.setId(1L);
        campaignHistoryDto.setCampaignID(1L);
        campaignHistoryDto.setCampaignStatus(CampaignStatus.REPETITIVE);
        campaignHistoryDto.setModifiedUser("demircanhasankadir@gmail.com");
        campaignHistoryDto.setModifiedDate(modifiedDate);

        return campaignHistoryDto;
    }
}
