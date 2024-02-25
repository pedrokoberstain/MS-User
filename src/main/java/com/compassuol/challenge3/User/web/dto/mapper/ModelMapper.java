package com.compassuol.challenge3.User.web.dto.mapper;

import java.util.ArrayList;
import java.util.List;

public class ModelMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static <O, D> D map(O origin, Class<D> destination) {
        return modelMapper.map(origin, destination);
    }

    public static <O, D> List<D> mapList(List<O> origin, Class<D> destination) {
        List<D> destinationObjects = new ArrayList<>();
        for (Object o : origin) {
            destinationObjects.add(modelMapper.map(o, destination));
        }
        return destinationObjects;
    }
}
