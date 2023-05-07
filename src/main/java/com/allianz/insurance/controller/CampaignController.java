package com.allianz.insurance.controller;

import com.allianz.insurance.model.Campaign;
import com.allianz.insurance.request.CreateCampaignRequest;
import com.allianz.insurance.response.CampaignResponse;
import com.allianz.insurance.service.CampaignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insurance")
@Api(value="Insurance Campaign Management Controller", description="Operations pertaining to campaign in Insurance Management System")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    /*
    eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiZW1haWwiOiJkZW1pcmNhbmhhc2Fua2FkaXJAZ21haWwuY29tIiwiaWF0IjoxNTE2MjM5MDIyfQ.gj0OOwdIFtyS3L32NQ2hSGP1c6MqZLFZbk8isg3tqpM
     */
    @ApiOperation(value = "Create a campaign", response = Campaign.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful Create Campaign"),
                    @ApiResponse(code = 500, message = "Internal Server Error"),
            }
    )
    @PostMapping
    public CampaignResponse createWallet(@RequestHeader(name="Authorization") String jwt, CreateCampaignRequest request) {
        return campaignService.createCampaign(jwt, request.getCampaignDto());
    }
}
