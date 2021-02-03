package com.example.demo.services;

import com.example.demo.models.Numbers;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Data
public class NumbersService {
    Numbers numbers;

    public NumbersService() {
    }

    @Autowired
    public NumbersService(Numbers numbers) {
        this.numbers = numbers;
    }

    public Map<Integer,List<Integer>> makeNewMapNonSort(int quantity){
        Random random = new Random();
        List<Integer> listNonSort = random.ints(1,1001).
                                    distinct().limit(quantity).boxed().collect(Collectors.toList());
        return parseListToMap(listNonSort);
    }

    public List<Integer> sortedList(List<Integer> list, String action){
        if(action.equals("up")){
            Collections.sort(list);
        }
        else{
            Collections.reverse(list);
        }
        return list;
    }
    //Map <key,Value<List(max 10 numbers)>>
    public Map<Integer, List<Integer>> parseListToMap(List<Integer> listParse){
        Map<Integer,List<Integer>> mapList = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        int keyMap = 1;
        int marker;
        for(int i = 0; i < listParse.size(); i++){
            list.add(listParse.get(i));
            marker = i + 1;
            if(((marker % 10) == 0) || (marker == listParse.size())){
                   mapList.put(keyMap,list);
                   keyMap++;
                   list = new ArrayList<>();
            }
        }
        return mapList;
    }

    public List<Integer> parseMapToList(Map<Integer, List<Integer>> mapList){
        List<Integer> list = new ArrayList<>();
        for (int key:mapList.keySet()) {
            list.addAll(mapList.get(key));
        }
        return list;
    }




}
