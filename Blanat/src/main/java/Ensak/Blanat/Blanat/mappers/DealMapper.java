package Ensak.Blanat.Blanat.mappers;

import Ensak.Blanat.Blanat.DTOs.DealDTO.CreateDealDTO;
import Ensak.Blanat.Blanat.entities.Deal;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class DealMapper {

    private final ModelMapper modelMapper;

    public DealMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CreateDealDTO dealToDealDTO(Deal deal) {
        return modelMapper.map(deal, CreateDealDTO.class);
    }

    // Map CreateDealDTO to Deal
    public Deal createDealDTOToDeal(CreateDealDTO createDealDTO) {
        return modelMapper.map(createDealDTO, Deal.class);
    }

    // Map List of Deal to List of CreateDealDTO
    public List<CreateDealDTO> dealsToDealDTOs(List<Deal> deals) {
        Type listType = new TypeToken<List<CreateDealDTO>>() {}.getType();
        return modelMapper.map(deals, listType);
    }
}
