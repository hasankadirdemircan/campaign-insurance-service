package com.allianz.insurance.request;

import com.allianz.insurance.enums.CampaignCategory;
import com.allianz.insurance.request.base.BaseRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCampaignRequest extends BaseRequest {
    private String advertTitle;
    private String campaignDetail;
    private CampaignCategory campaignCategory;
}
