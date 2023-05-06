package com.allianz.insurance.model;

import com.allianz.insurance.enums.CampaignCategory;
import com.allianz.insurance.enums.CampaignStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="campaign")
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "advert_title")
    private String advertTitle;

    @Column(name = "campaign_detail")
    private String campaignDetail;

    @Column(name = "campaign_category")
    private CampaignCategory campaignCategory;

    @Column(name = "status")
    private CampaignStatus campaignStatus;

}
