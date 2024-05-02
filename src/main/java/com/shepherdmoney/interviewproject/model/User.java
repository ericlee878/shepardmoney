package com.shepherdmoney.interviewproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "MyUser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String email;

    // TODO: User's credit card
    // HINT: A user can have one or more, or none at all. We want to be able to query credit cards by user
    //       and user by a credit card.

    private Set<CreditCard> creditCards;


    // BASIC ACCESSOR METHODS //

    // Gets id of User
    public int getId() {
        return id;
    }

    // Gets name of User
    public String getName() {
        return name;
    }

    // Gets email of User
    public String getEmail() {
        return email;
    }

    // Gets CreditCards of User
    public Set<CreditCard> getCreditCards() {
        return creditCards;
    }
    //////////////////////

    // BASIC MUTATOR METHODS //

    // Adds a CreditCard to a user
    public void addCreditCard(CreditCard creditCard) {
        this.creditCards.add(creditCard);
        creditCard.setId(id);
    }
}
