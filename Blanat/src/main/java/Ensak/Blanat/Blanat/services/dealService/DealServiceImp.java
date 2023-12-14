package Ensak.Blanat.Blanat.services.dealService;

import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.repositories.DealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DealServiceImp implements DealServiceInterface{

    private final DealRepository dealRepository;

    @Autowired
    public DealServiceImp(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }


    //===============working on ==========================

    @Override
    public Deal saveDeal(Deal deal) {
        Deal CreatedDeal = dealRepository.save(deal);
        return CreatedDeal;
    }

    //==================================================

    @Override
    public List<Deal> getAllDeals() {
        return null;
    }

    @Override
    public Deal getDealById(Long dealId) {
        return null;
    }

    @Override
    public void updateDeal(Deal deal) {

    }

    @Override
    public void deleteDeal(Long dealId) {

    }

    @Override
    public void getLatestDeals() {

    }

    @Override
    public void getDealsSortedByVoteup(Long dealId) {

    }

    @Override
    public void getActiveDeals(Long dealId) {

    }
}
