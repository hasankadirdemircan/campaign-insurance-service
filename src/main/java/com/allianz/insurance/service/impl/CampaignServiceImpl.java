package com.allianz.insurance.service.impl;

import com.allianz.insurance.dto.CampaignDto;
import com.allianz.insurance.model.Campaign;
import com.allianz.insurance.repository.CampaignRepository;
import com.allianz.insurance.service.CampaignService;
import com.allianz.insurance.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private Mapper mapper;
    @Override
    public Campaign createCampaign(CampaignDto campaignDto) {
        Campaign campaign = mapper.dto2Model(campaignDto);
        //TODO-1: add check -> if same campaign, status should be REPETITIVE
        //TODO-2: add check -> if the category is TSS,OSS and OTHER, status should be WAITING_FOR_APPROVAL

       return campaignRepository.save(campaign);
    }
}
