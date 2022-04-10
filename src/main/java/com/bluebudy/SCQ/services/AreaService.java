package com.bluebudy.SCQ.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.bluebudy.SCQ.domain.Area;

import org.springframework.stereotype.Service;

@Service
public class AreaService {

    public List<Area> groupAreas(List<Area> areas) {
        List<Area> groupedAreas = new ArrayList<>();
        Set<Integer> groupsAreas = new HashSet<>();
        areas.forEach(area -> {
            groupsAreas.add(area.getGroupArea());
        });
        groupsAreas.forEach(gArea -> {
            List<Area> areasByGroup = areas.stream().filter(area -> {
                return area.getGroupArea().equals(gArea);
            }).collect(Collectors.toList());
            Double totalArea = 0.0;
            for (Area area : areasByGroup) {
                totalArea = totalArea + area.getArea();
            }
            Area novaArea = new Area();
            novaArea.setArea(totalArea);
            novaArea.setDataInicio(areas.get(0).getDataInicio());
            novaArea.setDataFim(areas.get(0).getDataFim());
            novaArea.setGroupArea(areas.get(0).getGroupArea());
            groupedAreas.add(novaArea);

        });
        return groupedAreas;
    }


    public boolean isOutSideDataRange(Area area1, Area area2) {
        long inicio1 = area1.getDataInicio().getTime();
        long fim1 = area1.getDataFim().getTime();
        long inicio2 = area2.getDataInicio().getTime();

        if(inicio1 > inicio2){
            return true;
        } else if ((inicio1 < inicio2) && (fim1 < inicio2)) {
            return true;
        } else {
            return false;
        }
    }
    
}
