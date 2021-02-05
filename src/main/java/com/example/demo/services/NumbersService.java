package com.example.demo.services;

import com.example.demo.models.Numbers;

import java.util.List;
import java.util.Map;

public interface NumbersService {

   Map<Integer, List<Integer>> makeNewMapNonSort(int quantity);
   Numbers sortedList(Map<Integer,List<Integer>> map, String action);
}
