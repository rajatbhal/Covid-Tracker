package io.rajatchaudhary.covidtracker.controller;


import io.rajatchaudhary.covidtracker.CovidDataService;
import io.rajatchaudhary.covidtracker.models.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

///want it to display in html ui
@Controller
public class HomeController {
    ///since it is a service we can autowire it
    @Autowired
    CovidDataService covidDataService;

    @GetMapping("/")
    ///spring gives us object model we can use and put data to it and it can be used to display on html
    public String home(Model model){
        ///placing the name and TEST is actual value
        ///we will pass the list that we build using CovidDataService Application

        List<Location> cdata = covidDataService.getCompleteData();
        ///taking streams of object mapping to integer and summing up all the records
        int totalCasesGlobally = cdata.stream().mapToInt(m -> m.getUpdatedCases()).sum();
        int totalNewCases = cdata.stream().mapToInt(m -> m.getCasesSincePreviousDay()).sum();
        model.addAttribute("location",cdata);
        model.addAttribute("totalReportedCases",totalCasesGlobally);
        model.addAttribute("totalNewCases",totalNewCases);
        return "home";
    }

}
