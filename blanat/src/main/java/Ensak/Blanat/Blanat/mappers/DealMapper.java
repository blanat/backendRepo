package Ensak.Blanat.Blanat.mappers;

import Ensak.Blanat.Blanat.DTOs.dealDTO.CreateDealDTO;
import Ensak.Blanat.Blanat.DTOs.dealDTO.ListDealDTO;
import Ensak.Blanat.Blanat.entities.Deal;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DealMapper {

    private final ModelMapper modelMapper;

    public DealMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CreateDealDTO dealToDealDTO(Deal deal) {
        return modelMapper.map(deal, CreateDealDTO.class);
    }

    public Deal createDealDTOToDeal(CreateDealDTO createDealDTO) {
        return modelMapper.map(createDealDTO, Deal.class);
    }

    public List<CreateDealDTO> dealsToDealDTOs(List<Deal> deals) {
        Type listType = new TypeToken<List<CreateDealDTO>>() {}.getType();
        return modelMapper.map(deals, listType);
    }

    public ListDealDTO dealToListDealDTO(Deal deal) {
        return modelMapper.map(deal, ListDealDTO.class);

    }

    public List<ListDealDTO> dealsToListDealDTOs(List<Deal> deals) {
        Type listType = new TypeToken<List<ListDealDTO>>() {}.getType();
        return deals.stream()
                .map(this::dealToListDealDTO)
                .collect(Collectors.toList());
    }
}