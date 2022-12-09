package com.djigitbetFE.e2eTests;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

// page_url = https://www.jetbrains.com/
public class HomePage {
    

    public SelenideElement ProfileIconNOTLoggedIN = $("svg[data-testid='PersonIcon']");
    public SelenideElement LoginInsideProfileICN = $x("//a[@href='/login']");

    public SelenideElement NavBarbuttonSlots = $("html > body > div:nth-of-type(1) > div > header > div > div > div:nth-of-type(2) > div > a:nth-of-type(1) > button");

    public SelenideElement NavBarbuttonFunding = $("html > body > div:nth-of-type(1) > div > header > div > div > div:nth-of-type(2) > div > a:nth-of-type(2) > button");

    public SelenideElement ProfileIconLoggedIn = $x("//div[contains(@class, 'MuiAvatar-root')]");

    public SelenideElement ProfileLI = $x("//a[@href='/profile']");

    

        
}
