package com.example.demo.controllers;

import com.example.demo.models.Numbers;
import com.example.demo.services.NumbersService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    private NumbersService numbersService;
    @Autowired
    public MainController(NumbersService numbersService) {
        this.numbersService = numbersService;
    }

    @GetMapping(value = "/")
    public String getMainMenu(){
        return "mainPage";
    }

    @PostMapping(value = "/arrayNumber")
    public String getArrayNonSort(Model model,
                                  @RequestParam(value = "quantity")int quantity){
        Numbers numbers = new Numbers();
        numbers.setIntegerListMap(numbersService.makeNewMapNonSort(quantity));
//        numbers.setQuantity(quantity);
        model.addAttribute("numbers",numbers);
        model.addAttribute("action","up");
        return "resultPage";
    }

    @GetMapping(value = "/arrayNumber/{quantity}")
    public String getArrayNonSSort( Model model,
                                    @PathVariable("quantity")int quantity){
        Numbers numbers = new Numbers();
        numbers.setIntegerListMap(numbersService.makeNewMapNonSort(quantity));
//        numbers.setQuantity(quantity);
        model.addAttribute("numbers",numbers);
        model.addAttribute("action","up");
        return "resultPage";
    }

    @PostMapping(value = "/sorted/{action}")
    public String sorted(@RequestParam("map")String map,
                         @PathVariable("action") String action, Model model) throws JsonProcessingException {
        String mapJson = map.replace('=',':');
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        Map<Integer,List<Integer>> mapList = mapper.readValue(mapJson,new TypeReference<HashMap<Integer,List<Integer>>>(){});
        List<Integer> list = numbersService.parseMapToList(mapList);
        list = numbersService.sortedList(list,action);
        Numbers numbers = new Numbers();
        mapList = numbersService.parseListToMap(list);
        numbers.setIntegerListMap(mapList);
        if(action.equals("up")){
            model.addAttribute("action","down");
        }
        else{
            model.addAttribute("action","up");
        }
        model.addAttribute("numbers",numbers);
        return "resultPage";
    }



}
