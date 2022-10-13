package com.djigitbet.djigitbet.Services;

import com.djigitbet.djigitbet.DataAcessLayer.ICassinoRepository;
import com.djigitbet.djigitbet.Model.DTO.SlotCalculationsDTO;
import com.djigitbet.djigitbet.Model.Entity.Casino;
import com.djigitbet.djigitbet.Model.Entity.Player;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SlotEngine {   //the correct way to do this  is with a static class, but since java doesn't support it, here is a janky implementation


    ICassinoRepository casinoRepository;
    UserService userService;


    Casino casino;   //TODO: replace with cassino from db

    double prize;

    public SlotEngine(ICassinoRepository casinoRepository, UserService userService) {
        this.casinoRepository = casinoRepository;
        this.userService = userService;
        casino = casinoRepository.findById(1).get();
    }

    public SlotCalculationsDTO Play(Player player, double betAmount) {
        if (player.getBalance() < betAmount) {

            throw new IllegalArgumentException("Not enough funds");
        }
        prize = 0; //reset prize
        var results = rngGenerator(); //TODO: add rigging
        Win(results, player, betAmount);
        if (prize > casino.getCasinoFunds() / 2) {
            Play(player, betAmount);   //anti bankruptcy
        }
        if (prize > 0) {
            casino.setCasinoFunds(casino.getCasinoFunds() - prize);
            casino.setTotalWinCoefficient(casino.getTotalWinCoefficient() + 0.1);
            player.setBalance(player.getBalance() + prize);
            player.setFundsPayedOut(player.getFundsPayedOut() + prize);

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
        return results;

    }


    private SlotCalculationsDTO rngGenerator() {
        var Calcs = new SlotCalculationsDTO();
        int low = 1;
        int high = 100;
        Random r = new Random();
        Calcs.setRing1(r.nextInt(high - low) + low);
        Random r2 = new Random();
        Calcs.setRing2(r2.nextInt(high - low) + low);
        Random r3 = new Random();
        Calcs.setRing3(r3.nextInt(high - low) + low);
        return Calcs;

    }


    private void Win(SlotCalculationsDTO incomingSlotEntityDTO, Player player, double betAmount) { //TODO: add Statistics, and log all wins
        int ring1 = incomingSlotEntityDTO.getRing1();
        int ring2 = incomingSlotEntityDTO.getRing2();
        int ring3 = incomingSlotEntityDTO.getRing3();


        if (ring1 <= 30 && ring2 <= 30 && ring3 <= 30 && ring1 != 0) {

            player.setWinCoefficient(player.getWinCoefficient() + 0.1);
            prize = betAmount * 5;

        } else if (ring1 > 30 && ring1 <= 50 && ring2 > 30 && ring2 <= 50 && ring3 > 30 && ring3 <= 50 && ring1 != 0) {

            player.setWinCoefficient(player.getWinCoefficient() + 0.3);
            prize = 15;


        } else if (ring1 > 50 && ring1 <= 75 && ring2 > 50 && ring2 <= 75 && ring3 > 50 && ring3 <= 75 && ring1 != 0) {

            player.setWinCoefficient(player.getWinCoefficient() + 0.4);
            prize = betAmount * 20;


        } else if (ring1 > 75 && ring1 <= 95 && ring2 > 75 && ring2 <= 95 && ring3 > 75 && ring3 <= 95 && ring1 != 0) {

            player.setWinCoefficient(player.getWinCoefficient() + 0.5);
            prize = betAmount * 25;


        } else if (ring1 > 95 && ring1 <= 100 && ring2 > 95 && ring2 <= 100 && ring3 > 95 && ring3 <= 100 && ring1 != 0) {

            prize = betAmount * casino.getJackpot();
            resetJackpot();

        } else {
            player.setBalance(player.getBalance() - betAmount);
            player.setFundsLost(player.getFundsLost() + betAmount);
            player.setWinCoefficient(player.getWinCoefficient() - 0.1);
            prize = 0;

        }
    }


    public void resetJackpot() {
        if (casino.getJackpot() > casino.getCasinoFunds() / 2) return;
        casino.setJackpot(0);

    }


    private void supportWin(SlotCalculationsDTO incomingSlotEntityDTO, Player player, double betAmount) { //when the player loses a lot of money in a row, he gets a small win


        if (betAmount > casino.getCasinoFunds() / 10) return;

        if (player.getWinCoefficient() < -10) {
            incomingSlotEntityDTO.setRing1(11);
            incomingSlotEntityDTO.setRing2(12);
            incomingSlotEntityDTO.setRing3(13);
            player.setWinCoefficient(player.getWinCoefficient() + 2);
            prize = betAmount * 5;
        }

    }

    public double getJackpot() {
        return casino.getJackpot();
    }


}
