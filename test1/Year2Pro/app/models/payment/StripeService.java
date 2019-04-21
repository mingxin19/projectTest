package models.payment;

public class StripeService {
 
    // @Value("sk_test_j70u3XLdPSzc5dus6XmetX6V00TLdspBGS")
    // private String secretKey;
     
    // @PostConstruct
    // public void init() {
    //     Stripe.apiKey = secretKey;
    // }
    // public Charge charge(ChargeRequest chargeRequest) 
    //   throws AuthenticationException, InvalidRequestException,
    //     APIConnectionException, CardException, APIException {
    //     Map<String, Object> chargeParams = new HashMap<>();
    //     chargeParams.put("amount", chargeRequest.getAmount());
    //     chargeParams.put("currency", chargeRequest.getCurrency());
    //     chargeParams.put("description", chargeRequest.getDescription());
    //     chargeParams.put("source", chargeRequest.getStripeToken());
    //     return Charge.create(chargeParams);
    // }
}