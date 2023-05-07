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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/insurance")
@Api(value="Insurance Campaign Management Controller", description="Operations pertaining to campaign in Insurance Management System")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    /*
    eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiZW1haWwiOiJkZW1pcmNhbmhhc2Fua2FkaXJAZ21haWwuY29tIiwiaWF0IjoxNTE2MjM5MDIyfQ.gj0OOwdIFtyS3L32NQ2hSGP1c6MqZLFZbk8isg3tqpM
     */
    @ApiOperation(value = "Create a campaign", response = CampaignResponse.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Successful Create Campaign"),
                    @ApiResponse(code = 500, message = "Internal Server Error"),
            }
    )
    @PostMapping
    public CampaignResponse createCampaign(@RequestHeader(name="Authorization") String jwt, @Valid CreateCampaignRequest request) {
        return campaignService.createCampaign(jwt, request);
    }
    //

    @ApiOperation(value = "Activate a campaign", response = CampaignResponse.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful Activated the Campaign"),
                    @ApiResponse(code = 500, message = "Internal Server Error"),
            }
    )
    @PutMapping("/activate/{campaignID}")
    public CampaignResponse activateCampaign(@RequestHeader(name="Authorization") String jwt, @PathVariable(value = "campaignID") Long campaignID) {
        return campaignService.activateCampaign(jwt, campaignID);
    }

    @ApiOperation(value = "Deactivate a campaign", response = CampaignResponse.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful Deactivated the Campaign"),
                    @ApiResponse(code = 500, message = "Internal Server Error"),
            }
    )
    @PutMapping("/deactivate/{campaignID}")
    public CampaignResponse deactivateCampaign(@RequestHeader(name="Authorization") String jwt, @PathVariable(value = "campaignID") Long campaignID) {
        return campaignService.deactivateCampaign(jwt, campaignID);
    }

    @ApiOperation(value = "Get a campaign with id", response = CampaignResponse.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful Get campaign"),
                    @ApiResponse(code = 500, message = "Internal Server Error"),
            }
    )
    @GetMapping("{campaignID}")
    public CampaignResponse getCampaignById(@RequestHeader(name="Authorization") String jwt, @PathVariable(value = "campaignID") Long campaignID) {
        return campaignService.findCampaignById(jwt, campaignID);
    }

    @ApiOperation(value = "Get All Campaign", response = CampaignResponse.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful Get all campaign"),
                    @ApiResponse(code = 500, message = "Internal Server Error"),
            }
    )
    @GetMapping
    public List<CampaignResponse> getAllCampaign(@RequestHeader(name="Authorization") String jwt) {
        return campaignService.findAllCampaign(jwt);
    }
}
