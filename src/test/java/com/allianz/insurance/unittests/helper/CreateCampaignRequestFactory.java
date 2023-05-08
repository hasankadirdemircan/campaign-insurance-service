package com.allianz.insurance.unittests.helper;


import com.allianz.insurance.enums.CampaignCategory;
import com.allianz.insurance.request.CreateCampaignRequest;

public class CreateCampaignRequestFactory {
    public CreateCampaignRequest createCampaignRequest() {
        CreateCampaignRequest request = new CreateCampaignRequest();
        request.setAdvertTitle("Test Insurance Campaign");
        request.setCampaignDetail("this message contains the details of this campaign");
        request.setCampaignCategory(CampaignCategory.OSS);

        return request;
    }
}
