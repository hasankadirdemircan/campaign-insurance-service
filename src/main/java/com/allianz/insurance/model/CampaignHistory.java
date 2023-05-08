package com.allianz.insurance.model;

import com.allianz.insurance.enums.CampaignStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="campaign_history")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CampaignHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "campaign_id")
    private Long campaignID;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CampaignStatus campaignStatus;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "modified_user")
    private String modifiedUser;

}
