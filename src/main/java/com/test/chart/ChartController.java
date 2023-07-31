package com.test.chart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ChartController {

    @Autowired
    ChartRepository repository;
    @Autowired
    ethRepository erep;
    @Autowired
    bchRepository hrep;

    @GetMapping("/chart")
    public @ResponseBody List<tblminute5> getCandleData() {
        // Fetch all candle data from the database using the custom method
        List<tblminute5> candleData = repository.findAll();
        return candleData;
    }

    @PostMapping("/chart")
    @ResponseStatus(HttpStatus.CREATED) // Optional: Set the response status to 201 Created
    public String saveCandleData(@RequestBody tblminute5 candleData, RedirectAttributes rttr) {
        // Save the object to the database using the repository
        tblminute5 savedData = repository.save(candleData);
        System.out.println(savedData);
        
        rttr.addAttribute("price", savedData);
        return "redirect:/";
    }
    
    
    @Autowired
    PredictionsRepository predictions;
    
    @Autowired
    ethPredRepository epred;
    
    @Autowired
    bchPredRepository bpred;
    
    @GetMapping("/predictions")
    public @ResponseBody List<PredictionEntity> getPredictionsData() {
        // Fetch all predictions data from the database using the custom method in PredictionsRepository
        List<PredictionEntity> predictionsData = predictions.findAll();
        return predictionsData;
    }
    @GetMapping("/ethpredictions")
    public @ResponseBody List<ethpredictions> getEthPredictionsData() {
        // Fetch all predictions data from the database using the custom method in PredictionsRepository
        List<ethpredictions> predictionsData = epred.findAll();
        return predictionsData;
    }
    @GetMapping("/bchpredictions")
    public @ResponseBody List<bchpredictions> getBchPredictionsData() {
        // Fetch all predictions data from the database using the custom method in PredictionsRepository
        List<bchpredictions> predictionsData = bpred.findAll();
        return predictionsData;
    }
    @GetMapping("/ethdata")
    public @ResponseBody List<ethusdtdata> getEthData() {
        // Fetch all predictions data from the database using the custom method in PredictionsRepository
        List<ethusdtdata> result = erep.findAll();
        return result;
    }
    @GetMapping("/bchdata")
    public @ResponseBody List<bchusdtdata> getBchData() {
        // Fetch all predictions data from the database using the custom method in PredictionsRepository
        List<bchusdtdata> result = hrep.findAll();
        return result;
    }

   
    
    
}
