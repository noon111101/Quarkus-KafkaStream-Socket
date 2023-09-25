package org.acme.kafka.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;


@ApplicationScoped
public class MyModelMapperBean  {
    @Produces
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
