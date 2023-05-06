package com.allianz.insurance.request;

import com.allianz.insurance.dto.CampaignDto;
import com.allianz.insurance.request.base.BaseRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCampaignRequest extends BaseRequest {
    private CampaignDto campaignDto;
}
