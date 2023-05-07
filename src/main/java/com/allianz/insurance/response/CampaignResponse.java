package com.allianz.insurance.response;

import com.allianz.insurance.dto.CampaignDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CampaignResponse {
    CampaignDto campaign;
}
