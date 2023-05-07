package com.allianz.insurance.response;

import com.allianz.insurance.dto.CampaignHistoryDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CampaignHistoryResponse {
    private CampaignHistoryDto campaignHistory;
}
