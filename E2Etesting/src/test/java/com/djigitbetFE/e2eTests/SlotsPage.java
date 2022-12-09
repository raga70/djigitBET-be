package com.djigitbetFE.e2eTests;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
public class SlotsPage {
   public SelenideElement betInput = $("input[class='betInput']");

  public SelenideElement SpinButton = $("button[class='spinButton']");

  public SelenideElement AvailableCashH1 = $("html > body > div:nth-of-type(1) > div > div > div > h1:nth-of-type(3)");

  
    
}
