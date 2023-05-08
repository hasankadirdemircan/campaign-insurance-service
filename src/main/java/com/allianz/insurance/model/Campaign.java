package com.allianz.insurance.model;

import com.allianz.insurance.enums.CampaignCategory;
import com.allianz.insurance.enums.CampaignStatus;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name="campaign")
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 10, max = 50, message = "Advert Title must be between 10 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9ğüşöçıİĞÜŞÖÇ](?!.*?[^\\na-zA-Z0-9ğüşöçıİĞÜŞÖÇ]{2}).*?[a-zA-Z0-9ğüşöçıİĞÜŞÖÇ]$", message = "Only accept Turkish Characters and Numbers. Not allowed special characters")
    @Column(name = "advert_title")
    private String advertTitle;

    @Size(min = 20, max = 200, message = "Campaign Detail must be between 20 and 200 characters")
    @Pattern(regexp = "^[a-zA-Z0-9äöüÄÖÜşŞiİ./!.,&+]*$", message = "accept Turkish Characters, Numbers and special characters ")
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
