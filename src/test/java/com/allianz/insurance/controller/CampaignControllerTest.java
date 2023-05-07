package com.allianz.insurance.controller;

import com.allianz.insurance.request.CreateCampaignRequest;
import com.allianz.insurance.response.CampaignResponse;
import com.allianz.insurance.service.CampaignService;
import com.allianz.insurance.testbase.helper.CampaignDtoFactory;
import com.allianz.insurance.testbase.helper.CreateCampaignRequestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
        assertEquals(campaignResponseExpected.getCampaign().getAdvertTitle(),campaignResponse.getCampaign().getAdvertTitle());
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
        assertEquals(campaignResponseExpected.getCampaign().getAdvertTitle(),campaignResponse.getCampaign().getAdvertTitle());
        assertEquals(campaignResponseExpected.getCampaign().getCampaignDetail(),campaignResponse.getCampaign().getCampaignDetail());
        assertEquals(campaignResponseExpected.getCampaign().getCampaignStatus(),campaignResponse.getCampaign().getCampaignStatus());
        Mockito.verify(campaignService, Mockito.times(1)).activateCampaign(JWT, campaignID);
    }

}
