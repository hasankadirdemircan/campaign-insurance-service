package com.allianz.insurance.repository;

import com.allianz.insurance.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long>{
}
