package Ensak.Blanat.Blanat.services.dealService;

import Ensak.Blanat.Blanat.DTOs.dealDTO.ListDealDTO;
import Ensak.Blanat.Blanat.DTOs.dealDTO.ModifyDealDTO;
import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.ImagesDeal;
import Ensak.Blanat.Blanat.entities.UserApp;

import java.util.List;
public interface DealServiceInterface {

    List<ListDealDTO> getListDealsDTO();

    List<ListDealDTO> getValidatedDeals();
    List<ListDealDTO> getUnvalidatedDeals();

    List<ImagesDeal> getDealImages(long dealId);
    List<Deal> getAllDeals();

    Deal getDealById(Long dealId);

    Deal saveDeal(Deal deal);

    void updateDeal(Deal deal);

    void deleteDeal(Long dealId);

    void getLatestDeals();

    void getDealsSortedByVoteup(Long dealId);

    void getActiveDeals(Long dealId);

    List<ListDealDTO> getListDealsDTOByUserId(long id);

    void updateCommentCount(long dealId);
    void incrementDeg(Long dealId, UserApp user);
    void decrementDeg(Long dealId, UserApp user);


    void validateDeal(Long dealId);

    void modifyDeal(Long dealId, ModifyDealDTO modifyDealDTO);

    List<ListDealDTO> getValidatedDealsByCurrentUser();
}