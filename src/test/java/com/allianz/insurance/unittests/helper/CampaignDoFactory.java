package com.allianz.insurance.unittests.helper;

import com.allianz.insurance.enums.CampaignCategory;
import com.allianz.insurance.enums.CampaignStatus;
import com.allianz.insurance.model.Campaign;
import com.allianz.insurance.model.CampaignHistory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CampaignDoFactory {

    public Campaign campaign(){
        Campaign campaign = new Campaign();
        campaign.setCreatedBy("demircanhasankadir@gmail.com");
        campaign.setAdvertTitle("Test Insurance Campaign");
        campaign.setCampaignDetail("this message contains the details of this campaign");
        campaign.setCampaignCategory(CampaignCategory.OSS);

        return campaign;
    }

    public Campaign campaignWithId(){
        Campaign campaign = new Campaign();
        campaign.setId(1L);
        campaign.setCreatedBy("demircanhasankadir@gmail.com");
        campaign.setAdvertTitle("Test Insurance Campaign");
        campaign.setCampaignDetail("this message contains the details of this campaign");
        campaign.setCampaignCategory(CampaignCategory.OSS);

        return campaign;
    }

    public Campaign campaignWithIdWaitingForApproval(){
        Campaign campaign = new Campaign();
        campaign.setId(1L);
        campaign.setCreatedBy("demircanhasankadir@gmail.com");
        campaign.setAdvertTitle("Test Insurance Campaign");
        campaign.setCampaignDetail("this message contains the details of this campaign");
        campaign.setCampaignCategory(CampaignCategory.OSS);
        campaign.setCampaignStatus(CampaignStatus.WAITING_FOR_APPROVAL);

        return campaign;
    }

    public Campaign activatedCampaign(){
        Campaign campaign = new Campaign();
        campaign.setId(1L);
        campaign.setCreatedBy("demircanhasankadir@gmail.com");
        campaign.setAdvertTitle("Test Insurance Campaign");
        campaign.setCampaignDetail("this message contains the details of this campaign");
        campaign.setCampaignCategory(CampaignCategory.OSS);
        campaign.setCampaignStatus(CampaignStatus.ACTIVE);
        campaign.setUpdatedBy("demircanhasankadir@gmail.com");

        return campaign;
    }

    public Campaign deactivatedCampaign(){
        Campaign campaign = new Campaign();
        campaign.setId(1L);
        campaign.setCreatedBy("demircanhasankadir@gmail.com");
        campaign.setAdvertTitle("Test Insurance Campaign");
        campaign.setCampaignDetail("this message contains the details of this campaign");
        campaign.setCampaignCategory(CampaignCategory.OSS);
        campaign.setCampaignStatus(CampaignStatus.DEACTIVATE);
        campaign.setUpdatedBy("demircanhasankadir@gmail.com");

        return campaign;
    }

    public CampaignHistory campaignHistory(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate modifiedDate = LocalDate.parse("08-06-2023",format);

        CampaignHistory campaignHistory = new CampaignHistory();
        campaignHistory.setCampaignID(1L);
        campaignHistory.setModifiedUser("demircanhasankadir@gmail.com");
        campaignHistory.setModifiedDate(modifiedDate);
        campaignHistory.setCampaignStatus(CampaignStatus.WAITING_FOR_APPROVAL);

        return campaignHistory;
    }
}
