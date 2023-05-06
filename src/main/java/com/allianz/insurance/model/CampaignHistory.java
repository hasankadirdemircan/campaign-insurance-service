package com.allianz.insurance.model;

import com.allianz.insurance.enums.CampaignStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name="campaign_history")
public class CampaignHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "campaign_id")
    private Long campaignID;

    @Column(name = "status")
    private CampaignStatus campaignStatus;

    @DateTimeFormat(pattern="dd/MM/yyyy")
    private LocalDate date;
}
