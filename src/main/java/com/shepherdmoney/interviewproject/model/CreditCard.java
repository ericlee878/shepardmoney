package com.shepherdmoney.interviewproject.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;



@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "credit_cards")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    
    private String issuanceBank;

    @Column(name = "number", nullable = false)
    private String number;

    // TODO: Credit card's owner. For detailed hint, please see User class
    // Some field here <> owner;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // TODO: Credit card's balance history. It is a requirement that the dates in the balanceHistory 
    //       list must be in chronological order, with the most recent date appearing first in the list. 
    //       Additionally, the last object in the "list" must have a date value that matches today's date, 
    //       since it represents the current balance of the credit card. For example:
    //       [
    //         {date: '2023-04-10', balance: 800},
    //         {date: '2023-04-11', balance: 1000},
    //         {date: '2023-04-12', balance: 1200},
    //         {date: '2023-04-13', balance: 1100},
    //         {date: '2023-04-16', balance: 900},
    //       ]
    @Convert(converter = TreeMapConverter.class)
    private TreeMap<String, Integer> balanceHistory = new TreeMap<>();
    // private PriorityQueue<String> pq = new PriorityQueue<String>();
    // Set<BalanceHistory> balanceHistory = new TreeSet<>(Comparator.comparing(BalanceHistory::getDate));
    // private String[] balanceHistory;
    // public class BalanceHistoryComparator implements Comparator<BalanceHistory> {
    //     @Override
    //     public int compare(BalanceHistory b1, BalanceHistory b2) {
    //         return b1.getDate().compareTo(b2.getDate());
    //     }
    // }
    // Comparator<BalanceHistory> comparator = new BalanceHistoryComparator();
    // PriorityQueue<BalanceHistory>  = new PriorityQueue<>(comparator);


    // ADDITIONAL NOTE: For the balance history, you can use any data structure that you think is appropriate.
    //        It can be a list, array, map, pq, anything. However, there are some suggestions:
    //        1. Retrieval of a balance of a single day should be fast
    //        2. Traversal of the entire balance history should be fast
    //        3. Insertion of a new balance should be fast
    //        4. Deletion of a balance should be fast
    //        5. It is possible that there are gaps in between dates (note the 04-13 and 04-16)
    //        6. In the condition that there are gaps, retrieval of "closest" balance date should also be fast. Aka, given 4-15, return 4-16 entry tuple

    // BASIC ACCESSOR METHODS //

    // Gets id of CreditCard
    public int getId() {
        return id;
    }

    // Gets issuanceBank of CreditCard
    public String getIssuanceBank() {
        return issuanceBank;
    }

    // Gets number of CreditCard
    public String getNumber() {
        return number;
    }

    // Gets user of CreditCard
    public User getUser() {
        return user;
    }

    // Gets balanceHistory of CreditCard
    public TreeMap<String, Integer> getCreditCards() {
        return balanceHistory;
    }
    //////////////////////

    // Mutator Methods //

    // Sets id of CreditCard
    public void setId(int i){
        this.id = i;
    }
    // Sets issuanceBank of CreditCard
    public void setIssuanceBank(String i){
        this.issuanceBank = i;
    }
    // Sets cardnumber of CreditCard
    public void setCardNumber(String n){
        this.number = n;
    }
    // Sets userid of CreditCard
    public void setUser(User user){
        this.user = user;
    }


    public void addBalance(String date, Integer balance) {
        balanceHistory.put(date, balance);
    }

    public Integer getBalanceOnDate(String date) {
        return balanceHistory.getOrDefault(date, null);
    }

    public Entry<String, Integer> getClosestBalance(String date) {
        // Try to find the smallest date that is greater than or equal to the given date
        Entry<String, Integer> ceiling = balanceHistory.ceilingEntry(date);
        
        // Try to find the largest date that is less than or equal to the given date
        Entry<String, Integer> floor = balanceHistory.floorEntry(date);
        
        if (ceiling != null && floor != null) {
            // If both entries are found, determine which one is closer to the given date.
            // Assuming date format is yyyy-MM-dd and using string comparison (lexicographical)
            if (Math.abs(date.compareTo(ceiling.getKey())) < Math.abs(date.compareTo(floor.getKey()))) {
                return ceiling;
            } else {
                return floor;
            }
        } else if (ceiling != null) {
            // If only ceiling is available
            return ceiling;
        } else {
            // If only floor is available or both are null
            return floor;
        }
        ///////////////////////
    }


}
