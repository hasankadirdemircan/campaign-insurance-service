package com.allianz.insurance.repository;

import com.allianz.insurance.enums.CampaignCategory;
import com.allianz.insurance.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CampaignRepository extends JpaRepository<Campaign, Long>{
    Campaign findCampaignByCampaignCategoryAndAdvertTitleAndCampaignDetail(CampaignCategory category,
                                                                                      String title, String detail);
    Campaign findCampaignById(Long id);

}
