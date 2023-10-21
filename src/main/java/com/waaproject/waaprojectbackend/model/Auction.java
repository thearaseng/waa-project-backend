package com.waaproject.waaprojectbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double startPrice;
    private double depositAmount;
    private double highestPrice;

    private LocalDateTime bidDueDateTime;
    private LocalDate payDate;

    @OneToMany
    @JoinColumn(name = "auction_id")
    private List<Bid> bids;
}
