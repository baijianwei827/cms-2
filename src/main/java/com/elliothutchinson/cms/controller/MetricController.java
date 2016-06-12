package com.elliothutchinson.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elliothutchinson.cms.service.AuthenticationService;
import com.elliothutchinson.cms.service.rest.RestMetricService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api/v1/metrics")
public class MetricController extends AbstractRestController {

    private RestMetricService restMetricService;

    @Autowired
    public MetricController(AuthenticationService authenticationService, RestMetricService restMetricService) {
        super(authenticationService);
        this.restMetricService = restMetricService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getMetrics(@RequestParam("auth") String auth) throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        return restMetricService.findAllMetrics();
    }

    @RequestMapping(value = "/authors/{id}", method = RequestMethod.GET)
    public String getAuthorMetrics(@PathVariable Long id, @RequestParam("auth") String auth)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        return restMetricService.findAuthorMetricsById(id);
    }

    @RequestMapping(value = "/sections/{id}", method = RequestMethod.GET)
    public String getSectionMetrics(@PathVariable Long id, @RequestParam("auth") String auth)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        return restMetricService.findSectionMetricsById(id);
    }

    @RequestMapping(value = "/articles/{id}", method = RequestMethod.GET)
    public String getArticleMetrics(@PathVariable Long id, @RequestParam("auth") String auth)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        return restMetricService.findArticleMetricsById(id);
    }
    
    @RequestMapping(value = "/tags/{id}", method = RequestMethod.GET)
    public String getTagMetrics(@PathVariable Long id, @RequestParam("auth") String auth)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        return restMetricService.findTagMetricsById(id);
    }
}
