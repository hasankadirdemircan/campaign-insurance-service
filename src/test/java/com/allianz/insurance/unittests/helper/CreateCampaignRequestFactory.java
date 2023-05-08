package com.allianz.insurance.unittests.helper;


import com.allianz.insurance.enums.CampaignCategory;
import com.allianz.insurance.request.CreateCampaignRequest;

public class CreateCampaignRequestFactory {
    public CreateCampaignRequest createCampaignRequest() {
        CreateCampaignRequest request = new CreateCampaignRequest();
        request.setCampaignTitle("Test Insurance Campaign");
        request.setCampaignDetail("this message contains the details of this campaign");
        request.setCampaignCategory(CampaignCategory.OSS);

        return request;
    }

    public CreateCampaignRequest createCampaignRequest2() {
        CreateCampaignRequest request = new CreateCampaignRequest();
        request.setCampaignTitle("Test Insurance Campaign2");
        request.setCampaignDetail("this message contains the details of this campaign2");
        request.setCampaignCategory(CampaignCategory.TSS);

        return request;
    }
}
