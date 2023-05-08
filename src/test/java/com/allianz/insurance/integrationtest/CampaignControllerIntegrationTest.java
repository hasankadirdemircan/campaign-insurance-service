package com.allianz.insurance.integrationtest;

import com.allianz.insurance.model.Campaign;
import com.allianz.insurance.request.CreateCampaignRequest;
import com.allianz.insurance.response.CampaignHistoryResponse;
import com.allianz.insurance.response.CampaignResponse;
import com.allianz.insurance.unittests.helper.CampaignDoFactory;
import com.allianz.insurance.unittests.helper.CampaignDtoFactory;
import com.allianz.insurance.unittests.helper.CreateCampaignRequestFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CampaignControllerIntegrationTest{
    @Autowired
    private MockMvc mockMvc;

    private static final String JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiZW1haWwiOiJkZW1pcmNhbmhhc2Fua2FkaXJAZ21haWwuY29tIiwiaWF0IjoxNTE2MjM5MDIyfQ.gj0OOwdIFtyS3L32NQ2hSGP1c6MqZLFZbk8isg3tqpM";

    private CreateCampaignRequestFactory createCampaignRequestFactory;

    private CampaignDtoFactory dtoFactory;
    private CampaignDoFactory campaignDoFactory;
    @Before
    public void setup() {
        this.createCampaignRequestFactory = new CreateCampaignRequestFactory();
        this.dtoFactory = new CampaignDtoFactory();
        this.campaignDoFactory = new CampaignDoFactory();
    }
    @Test
    public void should_createCampaign() throws Exception {
        CampaignResponse campaignResponseExpected = CampaignResponse.builder().campaign(dtoFactory.campaignDto()).build();

        MvcResult result = createCampaign();

        CampaignResponse campaignResponse = new Gson().fromJson(result.getResponse().getContentAsString(), CampaignResponse.class);
        assertEquals(campaignResponseExpected.getCampaign().getCampaignTitle(),campaignResponse.getCampaign().getCampaignTitle());
        assertEquals(campaignResponseExpected.getCampaign().getCampaignDetail(),campaignResponse.getCampaign().getCampaignDetail());
        assertEquals(campaignResponseExpected.getCampaign().getCampaignStatus(),campaignResponse.getCampaign().getCampaignStatus());
    }

    @Test
    public void should_activateCampaign() throws Exception {
        Long campaignID = 1L;
        CampaignResponse campaignResponseExpected = CampaignResponse.builder().campaign(dtoFactory.campaignActivatedDto()).build();
        createCampaign();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/insurance/campaigns/activate/" + campaignID)
                        .header("Authorization", JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CampaignResponse campaignResponse = new Gson().fromJson(result.getResponse().getContentAsString(), CampaignResponse.class);
        assertEquals(campaignResponseExpected.getCampaign().getCampaignTitle(),campaignResponse.getCampaign().getCampaignTitle());
        assertEquals(campaignResponseExpected.getCampaign().getCampaignDetail(),campaignResponse.getCampaign().getCampaignDetail());
        assertEquals(campaignResponseExpected.getCampaign().getCampaignStatus(),campaignResponse.getCampaign().getCampaignStatus());
    }

    @Test
    public void should_deactivateCampaign() throws Exception {
        Long campaignID = 1L;
        CampaignResponse campaignResponseExpected = CampaignResponse.builder().campaign(dtoFactory.campaignDeactivatedDto()).build();
        createCampaign();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/insurance/campaigns/deactivate/" + campaignID)
                        .header("Authorization", JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CampaignResponse campaignResponse = new Gson().fromJson(result.getResponse().getContentAsString(), CampaignResponse.class);
        assertEquals(campaignResponseExpected.getCampaign().getCampaignTitle(),campaignResponse.getCampaign().getCampaignTitle());
        assertEquals(campaignResponseExpected.getCampaign().getCampaignDetail(),campaignResponse.getCampaign().getCampaignDetail());
        assertEquals(campaignResponseExpected.getCampaign().getCampaignStatus(),campaignResponse.getCampaign().getCampaignStatus());
    }

    @Test
    public void should_throw_NotFoundException() throws Exception {
        Long campaignID = 1L;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/insurance/campaigns/deactivate/" + campaignID)
                        .header("Authorization", JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertEquals(404 ,result.getResponse().getStatus());
    }

    @Test
    public void should_getCampaignById() throws Exception {
        Long campaignID = 1L;
        CampaignResponse campaignResponseExpected = CampaignResponse.builder().campaign(dtoFactory.campaignDto()).build();
        createCampaign();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/insurance/campaigns/" + campaignID)
                        .header("Authorization", JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CampaignResponse campaignResponse = new Gson().fromJson(result.getResponse().getContentAsString(), CampaignResponse.class);
        assertEquals(campaignResponseExpected.getCampaign().getCampaignTitle(),campaignResponse.getCampaign().getCampaignTitle());
        assertEquals(campaignResponseExpected.getCampaign().getCampaignDetail(),campaignResponse.getCampaign().getCampaignDetail());
        assertEquals(campaignResponseExpected.getCampaign().getCampaignStatus(),campaignResponse.getCampaign().getCampaignStatus());
    }

    @Test
    public void should_findAllCampaign() throws Exception {
        List<CampaignResponse> campaignResponseExpected = Arrays.asList(CampaignResponse.builder().campaign(dtoFactory.campaignDto()).build());
        createCampaign();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/insurance/campaigns")
                        .header("Authorization", JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<CampaignResponse> campaignResponse = Arrays.asList(new Gson().fromJson(result.getResponse().getContentAsString(), CampaignResponse.class));
        assertEquals(campaignResponseExpected, campaignResponse);
        assertEquals(campaignResponseExpected.get(0).getCampaign().getCampaignTitle(),campaignResponse.get(0).getCampaign().getCampaignTitle());
        assertEquals(campaignResponseExpected.get(0).getCampaign().getCampaignDetail(),campaignResponse.get(0).getCampaign().getCampaignDetail());
        assertEquals(campaignResponseExpected.get(0).getCampaign().getCampaignStatus(),campaignResponse.get(0).getCampaign().getCampaignStatus());
    }


    @Test
    public void should_findCampaignHistoryById() throws Exception {
        Campaign campaign = campaignDoFactory.campaign();
        Long campaignID = 1L;
        CampaignHistoryResponse campaignHistoryResponseExpected = CampaignHistoryResponse.builder()
                .campaignTitle(campaign.getCampaignTitle())
                .campaignDetail(campaign.getCampaignDetail())
                .campaignCategory(campaign.getCampaignCategory())
                .campaignHistory(dtoFactory.campaignHistoryDtoList()).build();

        createCampaign();
        activateCampaign(campaignID);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/insurance/campaigns/history/" + campaignID)
                        .header("Authorization", JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CampaignHistoryResponse campaignHistoryResponse = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().fromJson(result.getResponse().getContentAsString(), CampaignHistoryResponse.class);
        assertEquals(campaignHistoryResponseExpected.getCampaignHistory().size(), campaignHistoryResponse.getCampaignHistory().size());
        assertEquals(campaignHistoryResponseExpected.getCampaignHistory().get(0).getCampaignID(),campaignHistoryResponse.getCampaignHistory().get(0).getCampaignID());
        assertEquals(campaignHistoryResponseExpected.getCampaignHistory().get(0).getModifiedUser(), campaignHistoryResponse.getCampaignHistory().get(0).getModifiedUser());
        assertEquals(campaignHistoryResponseExpected.getCampaignHistory().get(0).getCampaignStatus(), campaignHistoryResponse.getCampaignHistory().get(0).getCampaignStatus());
        assertEquals(campaignHistoryResponseExpected.getCampaignHistory().get(1).getCampaignID(),campaignHistoryResponse.getCampaignHistory().get(1).getCampaignID());
        assertEquals(campaignHistoryResponseExpected.getCampaignHistory().get(1).getModifiedUser(), campaignHistoryResponse.getCampaignHistory().get(1).getModifiedUser());
        assertEquals(campaignHistoryResponseExpected.getCampaignHistory().get(1).getCampaignStatus(), campaignHistoryResponse.getCampaignHistory().get(1).getCampaignStatus());
    }



    private MvcResult createCampaign() throws Exception {
        CreateCampaignRequest createCampaignRequest = createCampaignRequestFactory.createCampaignRequest();

        return mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/insurance/campaigns")
                        .header("Authorization", JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("campaignTitle", createCampaignRequest.getCampaignTitle())
                        .param("campaignDetail", createCampaignRequest.getCampaignDetail())
                        .param("campaignCategory", String.valueOf(createCampaignRequest.getCampaignCategory()))
                )
                .andExpect(status().isOk())
                .andReturn();
    }

    private MvcResult createCampaign2() throws Exception {
        CreateCampaignRequest createCampaignRequest = createCampaignRequestFactory.createCampaignRequest2();

        return mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/insurance/campaigns")
                        .header("Authorization", JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("campaignTitle", createCampaignRequest.getCampaignTitle())
                        .param("campaignDetail", createCampaignRequest.getCampaignDetail())
                        .param("campaignCategory", String.valueOf(createCampaignRequest.getCampaignCategory()))
                )
                .andExpect(status().isOk())
                .andReturn();
    }

    private MvcResult activateCampaign(Long campaignID) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/insurance/campaigns/activate/" + campaignID)
                        .header("Authorization", JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}
