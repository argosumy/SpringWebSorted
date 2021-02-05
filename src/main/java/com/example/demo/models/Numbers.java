package com.example.demo.models;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;
@Data
@Component
public class Numbers {

    @Max(value = 1000,message = "Pick  a  number  before  1000")
    @Min(value = 1, message = "Pick  a  number  after  1")
    int quantity;
    Map<Integer, List<Integer>> integerListMap;

    public Numbers() {
    }
}
