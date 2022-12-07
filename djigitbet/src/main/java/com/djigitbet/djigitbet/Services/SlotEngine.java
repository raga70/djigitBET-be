package com.djigitbet.djigitbet.Services;

import com.djigitbet.djigitbet.Repositories.ICassinoRepository;
import com.djigitbet.djigitbet.Model.DTO.SlotCalculationsDTO;
import com.djigitbet.djigitbet.Model.Entity.Casino;
import com.djigitbet.djigitbet.Model.Entity.Player;
import lombok.*;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Service
public class SlotEngine {   //the correct way to do this  is with a static class, but since java doesn't support it, here is a janky implementation


    ICassinoRepository casinoRepository;
    UserService userService;
    //switched to a normal random because of the deployment docker container
   // SecureRandom randomOld = SecureRandom.getInstanceStrong();
    Random random = new Random();
    Casino casino;

 private final ModelMapper modelMapper = new ModelMapper();
 @Getter @Setter @AllArgsConstructor @NoArgsConstructor
 public static class SlotCalculationsLOCAL{
        int ring1;
        int ring2;
        int ring3;
        double jackpot;
        double balance;
        double prize; //hurray long live java witch doesn't support passing primitive types by reference
 }
    
    
    @SneakyThrows
    public SlotEngine(ICassinoRepository casinoRepository, UserService userService) throws NoSuchAlgorithmException {
        this.casinoRepository = casinoRepository;
        this.userService = userService;
        casino = casinoRepository.getCasinoById(1);
//        if (casino == null) {
//            casino = new Casino();
//            casino.setCasinoFunds(1000);
//            casino.setJackpot(100);
//            casino.setTotalWinCoefficient(-2.45);
//            casino.setId(0);
//        }
        
    }

    public SlotCalculationsDTO Play(Player player, double betAmount) {
        if(casino == null) 
            casino = casinoRepository.getCasinoById(1);
        
        if (player.getBalance() < betAmount) 
            throw new IllegalArgumentException("Not enough funds");

        var results = rngGenerator(); 
        results.prize = 0; //reset prize
        Win(results, player, betAmount);
        if (results.prize > casino.getCasinoFunds() / 2) {
            Play(player, betAmount);   //anti bankruptcy
        }
        if (results.prize > 0) {
            casino.setCasinoFunds(casino.getCasinoFunds() - results.prize);
            casino.setTotalWinCoefficient(casino.getTotalWinCoefficient() + 0.1);
            player.setBalance(player.getBalance() + results.prize);
            player.setFundsPayedOut(player.getFundsPayedOut() + results.prize);

        } else {
            supportWin(results, player, betAmount);
            casino.setCasinoFunds(casino.getCasinoFunds() + betAmount);
            casino.setTotalWinCoefficient(casino.getTotalWinCoefficient() - 0.1);
            casino.setJackpot(casino.getJackpot() + betAmount / 1.3);
        }
        casinoRepository.save(casino);
        userService.SaveUser(player);
        results.setBalance(player.getBalance());
        results.setJackpot(casino.getJackpot());
        return modelMapper.map(results, SlotCalculationsDTO.class);

    }


    private SlotCalculationsLOCAL rngGenerator() {
        var Calcs = new SlotCalculationsLOCAL();
        int low = 1;
        int high = 100;
        Calcs.setRing1(random.nextInt(high - low) + low);
        Calcs.setRing2(random.nextInt(high - low) + low);
        Calcs.setRing3(random.nextInt(high - low) + low);
        return Calcs;
    }

    //it`s not a good practice for this method to be public, but since reflection is a nightmare in java,I`m not spending 3 days on it 
    public void Win(SlotCalculationsLOCAL incomingSlotEntityDTO, Player player, double betAmount) { //TODO: add Statistics, and log all wins
        int ring1 = incomingSlotEntityDTO.getRing1();
        int ring2 = incomingSlotEntityDTO.getRing2();
        int ring3 = incomingSlotEntityDTO.getRing3();

        //testing   
        if(casino == null) //for testing
            casino = casinoRepository.getCasinoById(1);

        if (ring1 <= 30 && ring2 <= 30 && ring3 <= 30 && ring1 != 0) {

            player.setWinCoefficient(player.getWinCoefficient() + 0.1);
            incomingSlotEntityDTO.prize = betAmount * 5;

        } else if (ring1 > 30 && ring1 <= 50 && ring2 > 30 && ring2 <= 50 && ring3 > 30 && ring3 <= 50 && ring1 != 0) {

            player.setWinCoefficient(player.getWinCoefficient() + 0.3);
            incomingSlotEntityDTO.prize = betAmount * 15;


        } else if (ring1 > 50 && ring1 <= 75 && ring2 > 50 && ring2 <= 75 && ring3 > 50 && ring3 <= 75 && ring1 != 0) {

            player.setWinCoefficient(player.getWinCoefficient() + 0.4);
            incomingSlotEntityDTO.prize = betAmount * 20;


        } else if (ring1 > 75 && ring1 <= 95 && ring2 > 75 && ring2 <= 95 && ring3 > 75 && ring3 <= 95 && ring1 != 0) {

            player.setWinCoefficient(player.getWinCoefficient() + 0.5);
            incomingSlotEntityDTO.prize = betAmount * 25;


        } else if (ring1 > 95 && ring1 <= 100 && ring2 > 95 && ring2 <= 100 && ring3 > 95 && ring3 <= 100 && ring1 != 0) {

            incomingSlotEntityDTO.prize =  casino.getJackpot();
            resetJackpot();

        } else {
            player.setBalance(player.getBalance() - betAmount);
            player.setFundsLost(player.getFundsLost() + betAmount);
            player.setWinCoefficient(player.getWinCoefficient() - 0.1);
            incomingSlotEntityDTO.prize = 0;

        }
    }


    public void resetJackpot() {
        if (casino.getJackpot() > casino.getCasinoFunds() / 2) return;
        casino.setJackpot(0);

    }


    public   void supportWin(SlotCalculationsLOCAL incomingSlotEntityDTO, Player player, double betAmount) { //when the player loses a lot of money in a row, he gets a small win
        if(casino == null)  //for testing
            casino = casinoRepository.getCasinoById(1);
        
        if (betAmount > casino.getCasinoFunds() / 10) return;

        if (player.getWinCoefficient() < -10) {
            incomingSlotEntityDTO.setRing1(11);
            incomingSlotEntityDTO.setRing2(12);
            incomingSlotEntityDTO.setRing3(13);
            player.setWinCoefficient(player.getWinCoefficient() + 2);
            incomingSlotEntityDTO.prize = betAmount * 5;
        }

    }

    public double getJackpot() {
        return casino.getJackpot();
    }


}
