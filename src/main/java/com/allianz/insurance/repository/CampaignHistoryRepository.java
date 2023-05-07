package com.allianz.insurance.repository;

import com.allianz.insurance.model.CampaignHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignHistoryRepository extends JpaRepository<CampaignHistory, Long> {
    List<CampaignHistory> findCampaignHistoriesByCampaignID(Long campaignID);
}
