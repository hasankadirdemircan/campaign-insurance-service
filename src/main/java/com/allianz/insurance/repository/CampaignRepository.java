package com.allianz.insurance.repository;

import com.allianz.insurance.enums.CampaignCategory;
import com.allianz.insurance.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long>{
    Campaign findCampaignByCampaignCategoryAndCampaignTitleAndCampaignDetail(CampaignCategory category,
                                                                             String title, String detail);
    Campaign findCampaignById(Long id);

}
