package com.allianz.insurance.unittests.service;

import com.allianz.insurance.repository.CampaignHistoryRepository;
import com.allianz.insurance.repository.CampaignRepository;
import com.allianz.insurance.service.impl.CampaignServiceImpl;
import com.allianz.insurance.util.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CampaignServiceTest {
    @Mock
    private CampaignRepository campaignRepository;
    @Mock
    private CampaignHistoryRepository campaignHistoryRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private CampaignServiceImpl campaignService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


}
