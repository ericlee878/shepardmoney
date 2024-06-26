package com.shepherdmoney.interviewproject.controller;

import com.shepherdmoney.interviewproject.model.CreditCard;
import com.shepherdmoney.interviewproject.repository.CreditCardRepository;
import com.shepherdmoney.interviewproject.repository.UserRepository;
import com.shepherdmoney.interviewproject.vo.request.AddCreditCardToUserPayload;
import com.shepherdmoney.interviewproject.vo.request.UpdateBalancePayload;
import com.shepherdmoney.interviewproject.vo.response.CreditCardView;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
public class CreditCardController {

    // TODO: wire in CreditCard repository here (~1 line)
    private CreditCardRepository creditCardRepository;

    @PostMapping("/credit-card")
    public ResponseEntity<?> addCreditCardToUser(@RequestBody AddCreditCardToUserPayload payload) {
        // TODO: Create a credit card entity, and then associate that credit card with user with given userId
        //       Return 200 OK with the credit card id if the user exists and credit card is successfully associated with the user
        //       Return other appropriate response code for other exception cases
        //       Do not worry about validating the card number, assume card number could be any arbitrary format and length
        try {
            CreditCard creditCard = new CreditCard();
            creditCard.setCardNumber(payload.getCardNumber());
            creditCard.setId(payload.getUserId());

            CreditCard savedCard = creditCardRepository.save(creditCard);
            return ResponseEntity.ok(savedCard.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add credit card.");
        }
    }

    @GetMapping("/credit-card:all")
    public ResponseEntity<List<CreditCardView>> getAllCardOfUser(@RequestParam int userId) {
        // TODO: return a list of all credit card associated with the given userId, using CreditCardView class
        //       if the user has no credit card, return empty list, never return null
        List<CreditCard> creditCards = creditCardRepository.findAllByUserId(userId); // Assuming a service method to fetch cards
        List<CreditCardView> output = new ArrayList<>();;
        for(CreditCard cc : creditCards){
            String issuanceBank = cc.getIssuanceBank();
            String number = cc.getNumber();
            CreditCardView ccv = new CreditCardView(issuanceBank, number);
            output.add(ccv);
        }

        // Check if the list is null or empty
        if (output.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList()); // Return an empty list instead of null
        }

        return ResponseEntity.ok(output); // Return the list of credit cards

    }

    @GetMapping("/credit-card:user-id")
    public ResponseEntity<Integer> getUserIdForCreditCard(@RequestParam String creditCardNumber) {
        // TODO: Given a credit card number, efficiently find whether there is a user associated with the credit card
        //       If so, return the user id in a 200 OK response. If no such user exists, return 400 Bad Request
        Integer userId = creditCardRepository.findUserIdByCreditCardNumber(creditCardNumber);

        if (userId == null) {
            // If no user ID is found, return a 400 Bad Request
            return ResponseEntity.badRequest().build();
        }

        // If a user ID is found, return it with a 200 OK response
        return ResponseEntity.ok(userId);
    }

    // @PostMapping("/credit-card:update-balance")
    // public SomeEnityData postMethodName(@RequestBody UpdateBalancePayload[] payload) {
    //     //TODO: Given a list of transactions, update credit cards' balance history.
    //     //      1. For the balance history in the credit card
    //     //      2. If there are gaps between two balance dates, fill the empty date with the balance of the previous date
    //     //      3. Given the payload `payload`, calculate the balance different between the payload and the actual balance stored in the database
    //     //      4. If the different is not 0, update all the following budget with the difference
    //     //      For example: if today is 4/12, a credit card's balanceHistory is [{date: 4/12, balance: 110}, {date: 4/10, balance: 100}],
    //     //      Given a balance amount of {date: 4/11, amount: 110}, the new balanceHistory is
    //     //      [{date: 4/12, balance: 120}, {date: 4/11, balance: 110}, {date: 4/10, balance: 100}]
    //     //      Return 200 OK if update is done and successful, 400 Bad Request if the given card number
    //     //        is not associated with a card.
        
    //     return null;
    // }
    
}
