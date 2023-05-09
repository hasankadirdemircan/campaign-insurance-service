package com.allianz.insurance.integrationtest;

import com.allianz.insurance.enums.CampaignCategory;
import com.allianz.insurance.enums.CampaignStatus;
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

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        CreateCampaignRequest createCampaignRequest = createCampaignRequestFactory.createCampaignRequest();
        MvcResult result = createCampaign(createCampaignRequest);

        CampaignResponse campaignResponse = new Gson().fromJson(result.getResponse().getContentAsString(), CampaignResponse.class);
        assertEquals(campaignResponseExpected.getCampaign().getCampaignTitle(),campaignResponse.getCampaign().getCampaignTitle());
        assertEquals(campaignResponseExpected.getCampaign().getCampaignDetail(),campaignResponse.getCampaign().getCampaignDetail());
        assertEquals(campaignResponseExpected.getCampaign().getCampaignStatus(),campaignResponse.getCampaign().getCampaignStatus());
    }

    @Test
    public void should_createCampaign_ACTIVE() throws Exception {
        CreateCampaignRequest createCampaignRequest = createCampaignRequestFactory.createCampaignRequestActive();
        MvcResult result = createCampaign(createCampaignRequest);

        CampaignResponse campaignResponse = new Gson().fromJson(result.getResponse().getContentAsString(), CampaignResponse.class);
        assertEquals("Test Active Insurance Campaign",campaignResponse.getCampaign().getCampaignTitle());
        assertEquals("this message contains the details of this campaign",campaignResponse.getCampaign().getCampaignDetail());
        assertEquals(CampaignStatus.ACTIVE,campaignResponse.getCampaign().getCampaignStatus());
        assertEquals(CampaignCategory.HAYAT_INSURANCE,campaignResponse.getCampaign().getCampaignCategory());
    }

    @Test
    public void should_activateCampaign() throws Exception {
        CampaignResponse campaignResponseExpected = CampaignResponse.builder().campaign(dtoFactory.campaignActivatedDto()).build();
        CreateCampaignRequest createCampaignRequest = createCampaignRequestFactory.createCampaignRequest5();
        CampaignResponse createdCampaign = getCampaignResponseFromMvcResult(createCampaign(createCampaignRequest));
        Long campaignID = createdCampaign.getCampaign().getId();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/insurance/campaigns/activate/" + campaignID)
                        .header("Authorization", JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CampaignResponse campaignResponse = new Gson().fromJson(result.getResponse().getContentAsString(), CampaignResponse.class);
        assertEquals(campaignResponseExpected.getCampaign().getCampaignStatus(),campaignResponse.getCampaign().getCampaignStatus());
        assertEquals("Test Insurance Campaign5",campaignResponse.getCampaign().getCampaignTitle());
        assertEquals("this message contains the details of this campaign5",campaignResponse.getCampaign().getCampaignDetail());
    }

    @Test
    public void should_deactivateCampaign() throws Exception {
        CreateCampaignRequest createCampaignRequest = createCampaignRequestFactory.createCampaignRequest3();

        CampaignResponse createdCampaign = getCampaignResponseFromMvcResult(createCampaign(createCampaignRequest));
        Long campaignID = createdCampaign.getCampaign().getId();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/insurance/campaigns/deactivate/" + campaignID)
                        .header("Authorization", JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CampaignResponse campaignResponse = new Gson().fromJson(result.getResponse().getContentAsString(), CampaignResponse.class);
        assertEquals(CampaignStatus.DEACTIVATE,campaignResponse.getCampaign().getCampaignStatus());
        assertEquals(CampaignCategory.OSS,campaignResponse.getCampaign().getCampaignCategory());
    }

    @Test
    public void should_throw_NotFoundException() throws Exception {
        Long campaignID = 13242423434343423L;
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
        CreateCampaignRequest createCampaignRequest = createCampaignRequestFactory.createCampaignRequest4();
        CampaignResponse createdCampaign = getCampaignResponseFromMvcResult(createCampaign(createCampaignRequest));
        Long campaignID = createdCampaign.getCampaign().getId();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/insurance/campaigns/" + campaignID)
                        .header("Authorization", JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CampaignResponse campaignResponse = new Gson().fromJson(result.getResponse().getContentAsString(), CampaignResponse.class);
        assertEquals("Test Insurance Campaign4",campaignResponse.getCampaign().getCampaignTitle());
        assertEquals("this message contains the details of this campaign4",campaignResponse.getCampaign().getCampaignDetail());
        assertEquals(CampaignCategory.OSS,campaignResponse.getCampaign().getCampaignCategory());
        assertEquals(CampaignStatus.WAITING_FOR_APPROVAL,campaignResponse.getCampaign().getCampaignStatus());
    }

    @Test
    public void should_findAllCampaign() throws Exception {
        CreateCampaignRequest createCampaignRequest = createCampaignRequestFactory.createCampaignRequest4();
        getCampaignResponseFromMvcResult(createCampaign(createCampaignRequest));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/insurance/campaigns")
                        .header("Authorization", JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<CampaignResponse> campaignResponse = Arrays.asList(new Gson().fromJson(result.getResponse().getContentAsString(), CampaignResponse[].class));

        assertEquals(200, result.getResponse().getStatus());
        assertNotNull(campaignResponse.get(0));
    }


    @Test
    public void should_findCampaignHistoryById() throws Exception {
        Campaign campaign = campaignDoFactory.campaign();

        CampaignHistoryResponse campaignHistoryResponseExpected = CampaignHistoryResponse.builder()
                .campaignTitle(campaign.getCampaignTitle())
                .campaignDetail(campaign.getCampaignDetail())
                .campaignCategory(campaign.getCampaignCategory())
                .campaignHistory(dtoFactory.campaignHistoryDtoList()).build();

        MvcResult createdCampaign = createCampaign2();
        CampaignResponse campaignResponse = getCampaignResponseFromMvcResult(createdCampaign);
        Long campaignID = campaignResponse.getCampaign().getId();
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
        assertEquals(campaignID,campaignHistoryResponse.getCampaignHistory().get(0).getCampaignID());
        assertEquals(campaignHistoryResponseExpected.getCampaignHistory().get(0).getModifiedUser(), campaignHistoryResponse.getCampaignHistory().get(0).getModifiedUser());
        assertEquals(campaignHistoryResponseExpected.getCampaignHistory().get(0).getCampaignStatus(), campaignHistoryResponse.getCampaignHistory().get(0).getCampaignStatus());
        assertEquals(campaignID,campaignHistoryResponse.getCampaignHistory().get(1).getCampaignID());
        assertEquals(campaignHistoryResponseExpected.getCampaignHistory().get(1).getModifiedUser(), campaignHistoryResponse.getCampaignHistory().get(1).getModifiedUser());
        assertEquals(campaignHistoryResponseExpected.getCampaignHistory().get(1).getCampaignStatus(), campaignHistoryResponse.getCampaignHistory().get(1).getCampaignStatus());
    }



    private MvcResult createCampaign(CreateCampaignRequest createCampaignRequest) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
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
        CampaignResponse campaignResponse = new Gson().fromJson(result.getResponse().getContentAsString(), CampaignResponse.class);
        System.out.println("campaignResponse isss " + campaignResponse.getCampaign().getId());

        return result;
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

    private CampaignResponse getCampaignResponseFromMvcResult(MvcResult result) throws UnsupportedEncodingException {
        return new Gson().fromJson(result.getResponse().getContentAsString(), CampaignResponse.class);

    }
}
