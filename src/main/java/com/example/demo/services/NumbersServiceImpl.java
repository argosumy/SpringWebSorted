package com.example.demo.services;

import com.example.demo.models.Numbers;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Data
public class NumbersServiceImpl implements NumbersService {
    Numbers numbers;

    public NumbersServiceImpl() {
    }

    @Autowired
    public NumbersServiceImpl(Numbers numbers) {
        this.numbers = numbers;
    }

    @Override
    public Map<Integer,List<Integer>> makeNewMapNonSort(int quantity){
        Random random = new Random();
        List<Integer> listNonSort = random.ints(1,1001).
                                    distinct().limit(quantity).boxed().collect(Collectors.toList());
        return parseListToMap(listNonSort);
    }

    @Override
    public Numbers sortedList(Map<Integer,List<Integer>> map, String action){
        List<Integer> list = parseMapToList(map);
        if(action.equals("up")){
            Collections.sort(list);
        }
        else{
            Collections.reverse(list);
        }
        map = parseListToMap(list);
        Numbers numbers = new Numbers();
        numbers.setIntegerListMap(map);
        return numbers;
    }

    //Map <key,Value<List(max 10 numbers)>>
    private Map<Integer, List<Integer>> parseListToMap(List<Integer> listParse){
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

    private List<Integer> parseMapToList(Map<Integer, List<Integer>> mapList){
        List<Integer> list = new ArrayList<>();
        for (int key:mapList.keySet()) {
            list.addAll(mapList.get(key));
        }
        return list;
    }




}
