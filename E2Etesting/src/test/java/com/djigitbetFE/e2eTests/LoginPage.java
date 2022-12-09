package com.djigitbetFE.e2eTests;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    public SelenideElement usernameInput = $("input[id=':r3:']");

    public SelenideElement paswordInput = $("input[id=':r5:']");


    public SelenideElement divUsername2 = $("html > body > div:nth-of-type(1) > div > div > div > div:nth-of-type(2)");

    public SelenideElement bodyYouNeedEnableJava = $("body");

    public SelenideElement divPassword = $("html > body > div:nth-of-type(1) > div > div > div > div:nth-of-type(3)");

    public SelenideElement buttonSignIN = $("button[class*='MuiButton-fullWidth']");

    public SelenideElement divPassword2 = $("html > body > div:nth-of-type(1) > div > div > div > div:nth-of-type(3) > div");

}
