import domain.tradesman.*;
import events.AddedTradesManEvent;
import events.DefaultEventBus;
import module.AddedUserEventSubscription;
import module.Payment;
import module.SendMailToUser;
import application.AddedService;
import application.CreditCardVerificationService;
import application.TradesManVerificationService;

import java.time.LocalDateTime;
import java.util.Collections;


public class Main {

    public static void main(String[] args) throws Exception {
        var subscriptionMap = Collections.singletonMap(AddedTradesManEvent.class,
                        Collections.singletonList(new AddedUserEventSubscription(new SendMailToUser(), new Payment())));

        var eventBus = new DefaultEventBus(subscriptionMap);


        CreditCardVerificationService creditCardVerificationService = new CreditCardVerificationService(eventBus);
        TradesManVerificationService userVerificationService = new TradesManVerificationService(eventBus);
        AddedService addedService = new AddedService(eventBus);


        Email email = new Email("kelyan.bervin@gmail.com");
        CreditCard creditCard = CreditCard.of("458729053978354612", "BERVIN", LocalDateTime.of(2070, 12, 15,0,0,0));
        Location location = Location.of("ile de france", "Paris");
        TradesManId tradesManId = TradesManId.of("1");
        TradesMan tradesMan = TradesMan.of(tradesManId,"Kélyan ", "BERVIN", email,
                creditCard, "Macon", "maçon", 2.3, location, "Bac");

        creditCardVerificationService.creditCardVerification(creditCard, tradesMan);


        userVerificationService.userVerification(tradesMan);

        addedService.register(tradesMan);

        //paiement




    }
}