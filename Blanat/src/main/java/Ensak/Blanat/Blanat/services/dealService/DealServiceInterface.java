package Ensak.Blanat.Blanat.services.dealService;

import Ensak.Blanat.Blanat.DTOs.DealDTO.ListDealDTO;
import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.UserApp;

import java.util.List;
public interface DealServiceInterface {

    List<ListDealDTO> getListDealsDTO();

    List<Deal> getAllDeals();

    Deal getDealById(Long dealId);

    Deal saveDeal(Deal deal);

    void updateDeal(Deal deal);

    void deleteDeal(Long dealId);

    void getLatestDeals();

    void getDealsSortedByVoteup(Long dealId);

    void getActiveDeals(Long dealId);

}
