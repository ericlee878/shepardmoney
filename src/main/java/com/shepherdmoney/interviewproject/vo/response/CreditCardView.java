package com.shepherdmoney.interviewproject.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreditCardView {

    private String issuanceBank;

    private String number;

    public String getIssuanceBank(){
        return issuanceBank;
    }

    public String getNumber(){
        return number;
    }
}
