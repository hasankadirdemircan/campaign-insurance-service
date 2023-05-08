package com.allianz.insurance.unittests.service;

import com.allianz.insurance.dto.CampaignDto;
import com.allianz.insurance.exception.DefaultExceptionHandler;
import com.allianz.insurance.model.Campaign;
import com.allianz.insurance.model.CampaignHistory;
import com.allianz.insurance.repository.CampaignHistoryRepository;
import com.allianz.insurance.repository.CampaignRepository;
import com.allianz.insurance.request.CreateCampaignRequest;
import com.allianz.insurance.response.CampaignResponse;
import com.allianz.insurance.service.impl.CampaignServiceImpl;
import com.allianz.insurance.unittests.helper.CampaignDoFactory;
import com.allianz.insurance.unittests.helper.CampaignDtoFactory;
import com.allianz.insurance.unittests.helper.CreateCampaignRequestFactory;
import com.allianz.insurance.util.Mapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CampaignServiceTest {

    private static final String JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiZW1haWwiOiJkZW1pcmNhbmhhc2Fua2FkaXJAZ21haWwuY29tIiwiaWF0IjoxNTE2MjM5MDIyfQ.gj0OOwdIFtyS3L32NQ2hSGP1c6MqZLFZbk8isg3tqpM";

    @Mock
    private CampaignRepository campaignRepository;
    @Mock
    private CampaignHistoryRepository campaignHistoryRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private CampaignServiceImpl campaignService;

    private CreateCampaignRequestFactory createCampaignRequestFactory;
    private CampaignDoFactory campaignDoFactory;
    private CampaignDtoFactory campaignDtoFactory;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.createCampaignRequestFactory = new CreateCampaignRequestFactory();
        this.campaignDoFactory = new CampaignDoFactory();
        this.campaignDtoFactory = new CampaignDtoFactory();
    }

    @Test
    public void createCampaign_test_successful() {
        //given
        String userEmail = "demircanhasankadir@gmail.com";
        CreateCampaignRequest createCampaignRequest = createCampaignRequestFactory.createCampaignRequest();
        CampaignResponse campaignResponseExpected = CampaignResponse.builder().campaign(campaignDtoFactory.campaignDto()).build();
        Campaign campaignRequest = campaignDoFactory.campaign();
        Campaign savedCampaign = campaignDoFactory.campaignWithId();
        CampaignHistory campaignHistory = campaignDoFactory.campaignHistory();
        CampaignDto campaignDto = campaignDtoFactory.campaignDto();

        //when
        Mockito.when(mapper.createCampaignRequestToModel(userEmail,createCampaignRequest)).thenReturn(campaignRequest);
        Mockito.when(campaignRepository.findCampaignByCampaignCategoryAndAdvertTitleAndCampaignDetail(campaignRequest.getCampaignCategory(), campaignRequest.getAdvertTitle(), campaignRequest.getCampaignDetail())).thenReturn(null);
        Mockito.when(campaignRepository.save(campaignRequest)).thenReturn(savedCampaign);
        Mockito.when(campaignHistoryRepository.save(campaignHistory)).thenReturn(campaignHistory);
        Mockito.when(mapper.model2Dto(savedCampaign)).thenReturn(campaignDto);

        //then
        CampaignResponse response = campaignService.createCampaign(JWT, createCampaignRequest);

        //assert
        assertEquals(campaignResponseExpected.getCampaign().getCampaignStatus(), response.getCampaign().getCampaignStatus());
        assertEquals(campaignResponseExpected.getCampaign().getId(), response.getCampaign().getId());
        assertEquals(campaignResponseExpected.getCampaign().getCampaignDetail(), response.getCampaign().getCampaignDetail());
        Mockito.verify(campaignRepository, Mockito.times(1)).findCampaignByCampaignCategoryAndAdvertTitleAndCampaignDetail(campaignRequest.getCampaignCategory(), campaignRequest.getAdvertTitle(), campaignRequest.getCampaignDetail());
        Mockito.verify(campaignRepository, Mockito.times(1)).save(campaignRequest);
        Mockito.verify(mapper, Mockito.times(1)).model2Dto(savedCampaign);
    }

    @Test
    public void waitingApprovalStatus_to_activateCampaign_test_successful() {
        //given
        Long campaignID = 1L;
        CampaignResponse campaignResponseExpected = CampaignResponse.builder().campaign(campaignDtoFactory.campaignActivatedDto()).build();
        Campaign campaign = campaignDoFactory.campaignWithIdWaitingForApproval();
        CampaignHistory campaignHistory = campaignDoFactory.campaignHistory();
        CampaignDto campaignDto = campaignDtoFactory.campaignActivatedDto();

        //when
        Mockito.when(campaignRepository.findCampaignById(campaignID)).thenReturn(campaign);
        Mockito.when(campaignRepository.save(campaign)).thenReturn(campaign);
        Mockito.when(campaignHistoryRepository.save(campaignHistory)).thenReturn(campaignHistory);
        Mockito.when(mapper.model2Dto(campaign)).thenReturn(campaignDto);

        //then
        CampaignResponse response = campaignService.activateCampaign(JWT, campaignID);

        //assert
        assertEquals(campaignResponseExpected.getCampaign().getCampaignStatus(), response.getCampaign().getCampaignStatus());
        assertEquals(campaignResponseExpected.getCampaign().getId(), response.getCampaign().getId());
        assertEquals(campaignResponseExpected.getCampaign().getCampaignDetail(), response.getCampaign().getCampaignDetail());
        Mockito.verify(campaignRepository, Mockito.times(1)).findCampaignById(campaignID);
        Mockito.verify(campaignRepository, Mockito.times(1)).save(campaign);
        Mockito.verify(mapper, Mockito.times(1)).model2Dto(campaign);
    }

    @Test
    public void activateCampaign_test_does_not_allow_to_activate_for_deactivated_campaign_fail() {
        //given
        Long campaignID = 1L;
        Campaign deactivatedCampaign = campaignDoFactory.deactivatedCampaign();

        //when
        Mockito.when(campaignRepository.findCampaignById(campaignID)).thenReturn(deactivatedCampaign);

        //then
        DefaultExceptionHandler thrown = Assertions.assertThrows(DefaultExceptionHandler.class,
                () -> campaignService.activateCampaign(JWT, campaignID));

        //assert
        assertEquals("You can not activate the campaign, campaign status is ->  DEACTIVATE", thrown.getMessage());
        Mockito.verify(campaignRepository, Mockito.times(1)).findCampaignById(campaignID);
    }

    @Test
    public void activateCampaign_test_does_not_allow_to_activate_for_activated_campaign_fail() {
        //given
        Long campaignID = 1L;
        Campaign deactivatedCampaign = campaignDoFactory.activatedCampaign();

        //when
        Mockito.when(campaignRepository.findCampaignById(campaignID)).thenReturn(deactivatedCampaign);

        //then
        DefaultExceptionHandler thrown = Assertions.assertThrows(DefaultExceptionHandler.class,
                () -> campaignService.activateCampaign(JWT, campaignID));

        //assert
        assertEquals("You can not activate the campaign, campaign status is ->  ACTIVE", thrown.getMessage());
        Mockito.verify(campaignRepository, Mockito.times(1)).findCampaignById(campaignID);
    }


    @Test
    public void waitingApprovalStatus_to_deactivateCampaign_test_successful() {
        //given
        Long campaignID = 1L;
        CampaignResponse campaignResponseExpected = CampaignResponse.builder().campaign(campaignDtoFactory.campaignDeactivatedDto()).build();
        Campaign campaign = campaignDoFactory.campaignWithIdWaitingForApproval();
        CampaignHistory campaignHistory = campaignDoFactory.campaignHistory();
        CampaignDto campaignDto = campaignDtoFactory.campaignDeactivatedDto();

        //when
        Mockito.when(campaignRepository.findCampaignById(campaignID)).thenReturn(campaign);
        Mockito.when(campaignRepository.save(campaign)).thenReturn(campaign);
        Mockito.when(campaignHistoryRepository.save(campaignHistory)).thenReturn(campaignHistory);
        Mockito.when(mapper.model2Dto(campaign)).thenReturn(campaignDto);

        //then
        CampaignResponse response = campaignService.deactivateCampaign(JWT, campaignID);

        //assert
        assertEquals(campaignResponseExpected.getCampaign().getCampaignStatus(), response.getCampaign().getCampaignStatus());
        assertEquals(campaignResponseExpected.getCampaign().getId(), response.getCampaign().getId());
        assertEquals(campaignResponseExpected.getCampaign().getCampaignDetail(), response.getCampaign().getCampaignDetail());
        Mockito.verify(campaignRepository, Mockito.times(1)).findCampaignById(campaignID);
        Mockito.verify(campaignRepository, Mockito.times(1)).save(campaign);
        Mockito.verify(mapper, Mockito.times(1)).model2Dto(campaign);
    }


}
