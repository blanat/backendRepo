package Ensak.Blanat.Blanat.controllers.DealsController;

import Ensak.Blanat.Blanat.services.DealServices.DealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/api/deals")
@RestController
public class DealController {
    private final DealService dealService;

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }
}
