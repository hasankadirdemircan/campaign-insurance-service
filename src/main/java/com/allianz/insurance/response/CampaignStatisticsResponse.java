package com.allianz.insurance.response;

import com.allianz.insurance.enums.CampaignStatus;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CampaignStatisticsResponse {
    private String status;
    private Integer count;
}
