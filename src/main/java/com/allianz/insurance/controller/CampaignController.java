package com.allianz.insurance.controller;

import com.allianz.insurance.request.CreateCampaignRequest;
import com.allianz.insurance.response.CampaignHistoryResponse;
import com.allianz.insurance.response.CampaignResponse;
import com.allianz.insurance.response.CampaignStatisticsResponse;
import com.allianz.insurance.service.CampaignService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/v1/insurance")
@Api(value="Insurance Campaign Management Controller", description="Operations pertaining to campaign in Insurance Management System")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @ApiOperation(value = "Create a campaign", response = CampaignResponse.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Successful Create Campaign"),
                    @ApiResponse(code = 400, message = "The Campaign request is invalid and therefore the campaign has not been created.", response = String.class),
                    @ApiResponse(code = 415, message = "The content type is unsupported"),
                    @ApiResponse(code = 500, message = "An unexpected error has occurred. The error has been logged and is being investigated."),
            }
    )
    @PostMapping("/campaigns")
    public CampaignResponse createCampaign(
            @ApiParam(
                    value = "JWT Token" ,
                    example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiZW1haWwiOiJkZW1pcmNhbmhhc2Fua2FkaXJAZ21haWwuY29tIiwiaWF0IjoxNTE2MjM5MDIyfQ.gj0OOwdIFtyS3L32NQ2hSGP1c6MqZLFZbk8isg3tqpM")
            @RequestHeader(name="Authorization") String jwt,
            @Valid CreateCampaignRequest request) {
        return campaignService.createCampaign(jwt, request);
    }

    @ApiOperation(value = "Activate a campaign", response = CampaignResponse.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful Activated the Campaign"),
                    @ApiResponse(code = 400, message = "The Campaign request is invalid", response = String.class),
                    @ApiResponse(code = 404, message = "The campaign is not found"),
                    @ApiResponse(code = 415, message = "The content type is unsupported"),
                    @ApiResponse(code = 500, message = "An unexpected error has occurred. The error has been logged and is being investigated."),
            }
    )
    @PutMapping("/campaigns/activate/{campaignID}")
    public CampaignResponse activateCampaign(@ApiParam(
            value = "JWT Token" ,
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiZW1haWwiOiJkZW1pcmNhbmhhc2Fua2FkaXJAZ21haWwuY29tIiwiaWF0IjoxNTE2MjM5MDIyfQ.gj0OOwdIFtyS3L32NQ2hSGP1c6MqZLFZbk8isg3tqpM")
            @RequestHeader(name="Authorization") String jwt, @PathVariable(value = "campaignID") Long campaignID) {
        return campaignService.activateCampaign(jwt, campaignID);
    }

    @ApiOperation(value = "Deactivate a campaign", response = CampaignResponse.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful Deactivated the Campaign"),
                    @ApiResponse(code = 400, message = "The Campaign request is invalid", response = String.class),
                    @ApiResponse(code = 404, message = "The campaign is not found"),
                    @ApiResponse(code = 415, message = "The content type is unsupported"),
                    @ApiResponse(code = 500, message = "An unexpected error has occurred. The error has been logged and is being investigated."),
            }
    )
    @PutMapping("/campaigns/deactivate/{campaignID}")
    public CampaignResponse deactivateCampaign(
            @ApiParam(
                    value = "JWT Token" ,
                    example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiZW1haWwiOiJkZW1pcmNhbmhhc2Fua2FkaXJAZ21haWwuY29tIiwiaWF0IjoxNTE2MjM5MDIyfQ.gj0OOwdIFtyS3L32NQ2hSGP1c6MqZLFZbk8isg3tqpM")
            @RequestHeader(name="Authorization") String jwt, @PathVariable(value = "campaignID") Long campaignID) {
        return campaignService.deactivateCampaign(jwt, campaignID);
    }

    @ApiOperation(value = "Get a campaign with id", response = CampaignResponse.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful Get campaign"),
                    @ApiResponse(code = 400, message = "The Campaign request is invalid", response = String.class),
                    @ApiResponse(code = 404, message = "The campaign is not found"),
                    @ApiResponse(code = 415, message = "The content type is unsupported"),
                    @ApiResponse(code = 500, message = "An unexpected error has occurred. The error has been logged and is being investigated."),
            }
    )
    @GetMapping("/campaigns/{campaignID}")
    public CampaignResponse getCampaignById(
            @ApiParam(
                    value = "JWT Token" ,
                    example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiZW1haWwiOiJkZW1pcmNhbmhhc2Fua2FkaXJAZ21haWwuY29tIiwiaWF0IjoxNTE2MjM5MDIyfQ.gj0OOwdIFtyS3L32NQ2hSGP1c6MqZLFZbk8isg3tqpM")
            @RequestHeader(name="Authorization") String jwt, @PathVariable(value = "campaignID") Long campaignID) {
        return campaignService.findCampaignById(jwt, campaignID);
    }

    @ApiOperation(value = "Get All Campaign", response = CampaignResponse.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful Get all campaign"),
                    @ApiResponse(code = 400, message = "The Campaign request is invalid", response = String.class),
                    @ApiResponse(code = 404, message = "The campaign is not found"),
                    @ApiResponse(code = 415, message = "The content type is unsupported"),
                    @ApiResponse(code = 500, message = "An unexpected error has occurred. The error has been logged and is being investigated."),
            }
    )
    @GetMapping("/campaigns")
    public List<CampaignResponse> findAllCampaign(
            @ApiParam(
                    value = "JWT Token" ,
                    example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiZW1haWwiOiJkZW1pcmNhbmhhc2Fua2FkaXJAZ21haWwuY29tIiwiaWF0IjoxNTE2MjM5MDIyfQ.gj0OOwdIFtyS3L32NQ2hSGP1c6MqZLFZbk8isg3tqpM")
            @RequestHeader(name="Authorization") String jwt) {
        return campaignService.findAllCampaign(jwt);
    }

    @ApiOperation(value = "Get a Campaign History", response = CampaignResponse.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful Get Campaign History by campaignID"),
                    @ApiResponse(code = 400, message = "The Campaign request is invalid", response = String.class),
                    @ApiResponse(code = 404, message = "The campaign is not found"),
                    @ApiResponse(code = 415, message = "The content type is unsupported"),
                    @ApiResponse(code = 500, message = "An unexpected error has occurred. The error has been logged and is being investigated."),
            }
    )
    @GetMapping("/campaigns/history/{campaignID}")
    public CampaignHistoryResponse findCampaignHistoryById(
            @ApiParam(
                    value = "JWT Token" ,
                    example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiZW1haWwiOiJkZW1pcmNhbmhhc2Fua2FkaXJAZ21haWwuY29tIiwiaWF0IjoxNTE2MjM5MDIyfQ.gj0OOwdIFtyS3L32NQ2hSGP1c6MqZLFZbk8isg3tqpM")
            @RequestHeader(name="Authorization") String jwt, @PathVariable(value = "campaignID") Long campaignID) {
        return campaignService.findCampaignHistoryById(jwt, campaignID);
    }

    @ApiOperation(value = "Get All Campaign Statics", response = CampaignResponse.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful Get Campaign Statics"),
                    @ApiResponse(code = 400, message = "The Campaign request is invalid", response = String.class),
                    @ApiResponse(code = 404, message = "The campaign is not found"),
                    @ApiResponse(code = 415, message = "The content type is unsupported"),
                    @ApiResponse(code = 500, message = "An unexpected error has occurred. The error has been logged and is being investigated."),
            }
    )
    @GetMapping("/campaigns/statistics")
    public List<CampaignStatisticsResponse> getCampaignStatistics(
            @ApiParam(
                    value = "JWT Token" ,
                    example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiZW1haWwiOiJkZW1pcmNhbmhhc2Fua2FkaXJAZ21haWwuY29tIiwiaWF0IjoxNTE2MjM5MDIyfQ.gj0OOwdIFtyS3L32NQ2hSGP1c6MqZLFZbk8isg3tqpM")
            @RequestHeader(name="Authorization") String jwt) {
        return campaignService.getCampaignStatistics(jwt);
    }
}
