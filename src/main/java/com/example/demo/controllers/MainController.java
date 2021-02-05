package com.example.demo.controllers;
import com.example.demo.models.Numbers;
import com.example.demo.services.NumbersService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Validated
@Controller
public class MainController {
    private NumbersService numbersService;
    private Numbers numbers;

    @Autowired
    public MainController(NumbersService numbersService, Numbers numbers) {
        this.numbers = numbers;
        this.numbersService = numbersService;
    }

    @GetMapping(value = "/")
    public String getMainMenu(Model model){
        model.addAttribute("numbers", numbers);
        return "mainPage";
    }

    @PostMapping(value = "/arrayNumber")
    public String getArrayNonSort(@ModelAttribute("numbers")@Valid Numbers numbers,
                                  BindingResult bindingResult,Model model){
        this.numbers = numbers;
        this.numbers.setIntegerListMap(numbersService.makeNewMapNonSort(numbers.getQuantity()));
        model.addAttribute("numbers",this.numbers);
        model.addAttribute("action","up");
        return "resultPage";
    }

    @GetMapping(value = "/arrayNumber/{quantity}")
    public String getArrayNonSort( Model model,
                                    @PathVariable("quantity")@Max(value = 30, message = "Pick a number before 30")int quantity){
        this.numbers = new Numbers();
        this.numbers.setIntegerListMap(numbersService.makeNewMapNonSort(quantity));
        model.addAttribute("numbers",this.numbers);
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
        model.addAttribute("numbers",numbersService.sortedList(mapList,action));
        if(action.equals("up")){
            model.addAttribute("action","down");
        }
        else{
            model.addAttribute("action","up");
        }
        return "resultPage";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleValidationExceptions(ConstraintViolationException e){
        ModelAndView modelAndView = new ModelAndView();
        String error = StringUtils.substringBefore(e.getLocalizedMessage(),":");
        if(error.equals("getArrayNonSort.numbers.quantity")){
            modelAndView.setViewName("mainPage");
            modelAndView.addObject("error", StringUtils.substringAfter(e.getMessage(),":"));
            modelAndView.addObject("numbers",new Numbers());
        }else {
            modelAndView.setViewName("resultPage");
            modelAndView.addObject("numbers",numbers);
            modelAndView.addObject("action","up");
            modelAndView.addObject("error", StringUtils.substringAfter(e.getMessage(),":"));
        }
        return modelAndView;
    }


}
