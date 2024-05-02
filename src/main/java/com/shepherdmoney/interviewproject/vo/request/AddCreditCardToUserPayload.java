package com.shepherdmoney.interviewproject.vo.request;

import lombok.Data;

@Data
public class AddCreditCardToUserPayload {

    private int userId;

    private String cardIssuanceBank;

    private String cardNumber;

    // BASIC ACCESSOR METHODS //
    public int getUserId(){
        return userId;
    }

    public String getCardIssuanceBank(){
        return cardIssuanceBank;
    }

    public String getCardNumber(){
        return cardNumber;
    }
    //////////////////////

    // MUTATOR METHODS //
    
}
