package controllers;
import models.*;

import models.payment.*;
import views.html.*;
import play.mvc.*;
import play.api.Environment;
import play.data.*;
import play.db.ebean.Transactional;

public class ChargeController extends Controller {
 
    // @Autowired
    // private StripeService paymentsService;
 
    // @PostMapping("/charge")
    // public String charge(ChargeRequest chargeRequest, Model model)
    //   throws StripeException {
    //     chargeRequest.setDescription("Example charge");
    //     chargeRequest.setCurrency(Currency.EUR);
    //     Charge charge = paymentsService.charge(chargeRequest);
    //     model.addAttribute("id", charge.getId());
    //     model.addAttribute("status", charge.getStatus());
    //     model.addAttribute("chargeId", charge.getId());
    //     model.addAttribute("balance_transaction", charge.getBalanceTransaction());
    //     return "result";
    // }
 
    // @ExceptionHandler(StripeException.class)
    // public String handleError(Model model, StripeException ex) {
    //     model.addAttribute("error", ex.getMessage());
    //     return "result";
    // }

    //Also no idea how to make this work, I think this is suppose to take the charge request and send it to stripe to actually charge the card but not working at all. I don't know how to handle the model and what String is being returned
}