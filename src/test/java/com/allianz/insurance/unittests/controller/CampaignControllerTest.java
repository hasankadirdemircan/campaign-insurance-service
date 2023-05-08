package com.allianz.insurance.unittests.controller;

import com.allianz.insurance.controller.CampaignController;
import com.allianz.insurance.enums.CampaignStatus;
import com.allianz.insurance.request.CreateCampaignRequest;
import com.allianz.insurance.response.CampaignHistoryResponse;
import com.allianz.insurance.response.CampaignResponse;
import com.allianz.insurance.response.CampaignStatisticsResponse;
import com.allianz.insurance.service.CampaignService;
import com.allianz.insurance.unittests.helper.CampaignDtoFactory;
import com.allianz.insurance.unittests.helper.CreateCampaignRequestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CampaignControllerTest {

    private static final String JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiZW1haWwiOiJkZW1pcmNhbmhhc2Fua2FkaXJAZ21haWwuY29tIiwiaWF0IjoxNTE2MjM5MDIyfQ.gj0OOwdIFtyS3L32NQ2hSGP1c6MqZLFZbk8isg3tqpM";
    @Mock
    private CampaignService campaignService;

    @InjectMocks
    private CampaignController controller;

    private CreateCampaignRequestFactory createCampaignRequestFactory;

    private CampaignDtoFactory dtoFactory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        createCampaignRequestFactory = new CreateCampaignRequestFactory();
        dtoFactory = new CampaignDtoFactory();
    }

    @Test
    public void createCampaign_test_successful() {
        //given
        CreateCampaignRequest createCampaignRequest = createCampaignRequestFactory.createCampaignRequest();
        CampaignResponse campaignResponseExpected = CampaignResponse.builder().campaign(dtoFactory.campaignDto()).build();

        //when
        Mockito.when(campaignService.createCampaign(JWT, createCampaignRequest)).thenReturn(campaignResponseExpected);

        //then
        CampaignResponse campaignResponse = controller.createCampaign(JWT, createCampaignRequest);

        //assert
        assertEquals(campaignResponseExpected, campaignResponse);
        assertEquals(campaignResponseExpected.getCampaign().getCampaignTitle(),campaignResponse.getCampaign().getCampaignTitle());
        assertEquals(campaignResponseExpected.getCampaign().getCampaignDetail(),campaignResponse.getCampaign().getCampaignDetail());
        Mockito.verify(campaignService, Mockito.times(1)).createCampaign(JWT, createCampaignRequest);
    }

    @Test
    public void activateCampaign_test_successful() {
        //given
        Long campaignID = 1L;
        CampaignResponse campaignResponseExpected = CampaignResponse.builder().campaign(dtoFactory.campaignActivatedDto()).build();

        //when
        Mockito.when(campaignService.activateCampaign(JWT, campaignID)).thenReturn(campaignResponseExpected);

        //then
        CampaignResponse campaignResponse = controller.activateCampaign(JWT, campaignID);

        //assert
        assertEquals(campaignResponseExpected, campaignResponse);
        assertEquals(campaignResponseExpected.getCampaign().getCampaignTitle(),campaignResponse.getCampaign().getCampaignTitle());
        assertEquals(campaignResponseExpected.getCampaign().getCampaignDetail(),campaignResponse.getCampaign().getCampaignDetail());
        assertEquals(campaignResponseExpected.getCampaign().getCampaignStatus(),campaignResponse.getCampaign().getCampaignStatus());
        Mockito.verify(campaignService, Mockito.times(1)).activateCampaign(JWT, campaignID);
    }

    @Test
    public void deactivateCampaign_test_successful() {
        //given
        Long campaignID = 1L;
        CampaignResponse campaignResponseExpected = CampaignResponse.builder().campaign(dtoFactory.campaignDeactivatedDto()).build();

        //when
        Mockito.when(campaignService.deactivateCampaign(JWT, campaignID)).thenReturn(campaignResponseExpected);

        //then
        CampaignResponse campaignResponse = controller.deactivateCampaign(JWT, campaignID);

        //assert
        assertEquals(campaignResponseExpected, campaignResponse);
        assertEquals(campaignResponseExpected.getCampaign().getCampaignTitle(),campaignResponse.getCampaign().getCampaignTitle());
        assertEquals(campaignResponseExpected.getCampaign().getCampaignDetail(),campaignResponse.getCampaign().getCampaignDetail());
        assertEquals(campaignResponseExpected.getCampaign().getCampaignStatus(),campaignResponse.getCampaign().getCampaignStatus());
        Mockito.verify(campaignService, Mockito.times(1)).deactivateCampaign(JWT, campaignID);
    }



    @Test
    public void getCampaignById_test_successful() {
        //given
        Long campaignID = 1L;
        CampaignResponse campaignResponseExpected = CampaignResponse.builder().campaign(dtoFactory.campaignDeactivatedDto()).build();

        //when
        Mockito.when(campaignService.findCampaignById(JWT, campaignID)).thenReturn(campaignResponseExpected);

        //then
        CampaignResponse campaignResponse = controller.getCampaignById(JWT, campaignID);

        //assert
        assertEquals(campaignResponseExpected, campaignResponse);
        assertEquals(campaignResponseExpected.getCampaign().getCampaignTitle(),campaignResponse.getCampaign().getCampaignTitle());
        assertEquals(campaignResponseExpected.getCampaign().getCampaignDetail(),campaignResponse.getCampaign().getCampaignDetail());
        assertEquals(campaignResponseExpected.getCampaign().getCampaignStatus(),campaignResponse.getCampaign().getCampaignStatus());
        Mockito.verify(campaignService, Mockito.times(1)).findCampaignById(JWT, campaignID);
    }

    @Test
    public void findAllCampaign_test_successful() {
        //given
        List<CampaignResponse> campaignResponseExpected = Arrays.asList(CampaignResponse.builder().campaign(dtoFactory.campaignDeactivatedDto()).build());

        //when
        Mockito.when(campaignService.findAllCampaign(JWT)).thenReturn(campaignResponseExpected);

        //then
        List<CampaignResponse> campaignResponse = controller.findAllCampaign(JWT);

        //assert
        assertEquals(campaignResponseExpected, campaignResponse);
        assertEquals(campaignResponseExpected.get(0).getCampaign().getCampaignTitle(),campaignResponse.get(0).getCampaign().getCampaignTitle());
        assertEquals(campaignResponseExpected.get(0).getCampaign().getCampaignDetail(),campaignResponse.get(0).getCampaign().getCampaignDetail());
        assertEquals(campaignResponseExpected.get(0).getCampaign().getCampaignStatus(),campaignResponse.get(0).getCampaign().getCampaignStatus());
        Mockito.verify(campaignService, Mockito.times(1)).findAllCampaign(JWT);
    }

    @Test
    public void findCampaignHistoryById_test_successful() {
        //given
        Long campaignID = 1L;
        List<CampaignHistoryResponse> campaignHistoryResponseExpected = Arrays.asList(CampaignHistoryResponse.builder().campaignHistory(dtoFactory.campaignHistoryDto()).build());

        //when
        Mockito.when(campaignService.findCampaignHistoryById(JWT, campaignID)).thenReturn(campaignHistoryResponseExpected);

        //then
        List<CampaignHistoryResponse> campaignHistoryResponse = controller.findCampaignHistoryById(JWT, campaignID);

        //assert
        assertEquals(campaignHistoryResponseExpected, campaignHistoryResponse);
        assertEquals(campaignHistoryResponseExpected.get(0).getCampaignHistory().getCampaignID(),campaignHistoryResponse.get(0).getCampaignHistory().getCampaignID());
        assertEquals(campaignHistoryResponseExpected.get(0).getCampaignHistory().getModifiedUser(), campaignHistoryResponse.get(0).getCampaignHistory().getModifiedUser());
        Mockito.verify(campaignService, Mockito.times(1)).findCampaignHistoryById(JWT, campaignID);
    }


    @Test
    public void getCampaignStatistics_test_successful() {
        //given
        List<CampaignStatisticsResponse> campaignStatisticsResponseExpected = Arrays.asList(CampaignStatisticsResponse.builder().count(5).status(CampaignStatus.ACTIVE.name()).build());

        //when
        Mockito.when(campaignService.getCampaignStatistics(JWT)).thenReturn(campaignStatisticsResponseExpected);

        //then
        List<CampaignStatisticsResponse> campaignStatisticsResponse = controller.getCampaignStatistics(JWT);

        //assert
        assertEquals(campaignStatisticsResponseExpected, campaignStatisticsResponse);
        assertEquals(campaignStatisticsResponseExpected.get(0).getCount(),campaignStatisticsResponse.get(0).getCount());
        assertEquals(campaignStatisticsResponseExpected.get(0).getStatus(), campaignStatisticsResponse.get(0).getStatus());
        Mockito.verify(campaignService, Mockito.times(1)).getCampaignStatistics(JWT);
    }


}
