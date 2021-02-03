package com.example.demo.models;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Data
@Component
public class Numbers {
    int quantity;
    Map<Integer, List<Integer>> integerListMap;
    public Numbers() {
    }
}
