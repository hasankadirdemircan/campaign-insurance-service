package com.allianz.insurance.unittests.helper;

import com.allianz.insurance.dto.CampaignDto;
import com.allianz.insurance.dto.CampaignHistoryDto;
import com.allianz.insurance.enums.CampaignCategory;
import com.allianz.insurance.enums.CampaignStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

    public CampaignHistoryDto campaignHistoryDtoWaiting() {
        CampaignHistoryDto campaignHistoryDto = new CampaignHistoryDto();
        campaignHistoryDto.setId(1L);
        campaignHistoryDto.setCampaignID(1L);
        campaignHistoryDto.setCampaignStatus(CampaignStatus.WAITING_FOR_APPROVAL);
        campaignHistoryDto.setModifiedUser("demircanhasankadir@gmail.com");
        campaignHistoryDto.setModifiedDate(new Date(System.currentTimeMillis()));

        return campaignHistoryDto;
    }

    public List<CampaignHistoryDto> campaignHistoryDtoList() {
        List<CampaignHistoryDto> campaignHistoryDtoList = new LinkedList<>();
        CampaignHistoryDto campaignHistoryDto = new CampaignHistoryDto();
        campaignHistoryDto.setCampaignID(1L);
        campaignHistoryDto.setCampaignStatus(CampaignStatus.WAITING_FOR_APPROVAL);
        campaignHistoryDto.setModifiedUser("demircanhasankadir@gmail.com");
        campaignHistoryDto.setModifiedDate(new Date(System.currentTimeMillis()));
        campaignHistoryDtoList.add(campaignHistoryDto);

        CampaignHistoryDto campaignHistoryDto2 = new CampaignHistoryDto();
        campaignHistoryDto2.setCampaignID(1L);
        campaignHistoryDto2.setCampaignStatus(CampaignStatus.ACTIVE);
        campaignHistoryDto2.setModifiedUser("demircanhasankadir@gmail.com");
        campaignHistoryDto2.setModifiedDate(new Date(System.currentTimeMillis()));

        campaignHistoryDtoList.add(campaignHistoryDto2);
        return campaignHistoryDtoList;
    }
}
