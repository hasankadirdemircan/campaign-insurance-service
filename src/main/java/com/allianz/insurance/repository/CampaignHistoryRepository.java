package com.allianz.insurance.repository;

import com.allianz.insurance.model.CampaignHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignHistoryRepository extends JpaRepository<CampaignHistory, Long> {
}
