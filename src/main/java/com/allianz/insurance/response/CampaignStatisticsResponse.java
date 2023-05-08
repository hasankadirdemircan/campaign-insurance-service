package com.allianz.insurance.response;

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
