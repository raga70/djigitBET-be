package com.djigitbetFE.e2eTests;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

// page_url = https://www.jetbrains.com/
public class FundingPage {
    public SelenideElement BalanceAmt = $x("//h1");

    public SelenideElement inputAmount = $x("//*[@id='outlined-basic']");

    public SelenideElement divAmount = $x("//div[contains(@style, '5px;')]");

    public SelenideElement divAmount2 = $x("//div[contains(@class, 'MuiInputBase-root')]");

    public SelenideElement buttonPayWithCard = $x("//button[contains(@style, '0,')]");

    public SelenideElement EmailInput = $("#email");

    public SelenideElement CardNumberDIV = $("body > div.overlayView.active > form > div.bodyView > div > div:nth-child(4) > div > div:nth-child(2) > div > div > div > div > div > div > div.cardNumberInput.input.top");
    public SelenideElement CardNumberInput = $x("//*[@id=\"card_number\"]");
    
    public SelenideElement CardExpiryInput = $x("//*[@id=\"cc-exp\"]");
    
    public SelenideElement CardCVCInput = $x("//*[@id=\"cc-csc\"]");
    
    public SelenideElement stripePAYbtn = $x("//*[@id=\"submitButton\"]");
}
