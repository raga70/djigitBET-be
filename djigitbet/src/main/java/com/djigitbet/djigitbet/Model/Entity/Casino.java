package com.djigitbet.djigitbet.Model.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Casino 
{
    @Id
    int id = 1;
    double casinoFunds;
    double jackpot;
    double totalWinCoefficient;

  
}
