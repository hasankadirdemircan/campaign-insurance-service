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

    @Enumerated(EnumType.STRING)
    @Column(name = "campaign_category")
    private CampaignCategory campaignCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CampaignStatus campaignStatus;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

}
