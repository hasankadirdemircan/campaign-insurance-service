package com.allianz.insurance.response;

import com.allianz.insurance.dto.CampaignHistoryDto;
import com.allianz.insurance.enums.CampaignCategory;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CampaignHistoryResponse {
    private String campaignTitle;
    private String campaignDetail;
    private CampaignCategory campaignCategory;
    private List<CampaignHistoryDto> campaignHistory;
}
