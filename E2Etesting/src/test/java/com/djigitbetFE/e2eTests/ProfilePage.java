package com.djigitbetFE.e2eTests;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ProfilePage {
    public SelenideElement bodyYouNeedEnableJava = $("body");

    public SelenideElement inputName = $x("//*[@id=':r9:']");

    public SelenideElement divName = $x("//div[contains(@class, 'MuiTextField-root')][.//*[@id=':r9:']]");

    public SelenideElement buttonUpdate = $x("//button[@type='submit']");

    public SelenideElement pInformationUpdatedSuccessfully = $x("//p[@style='color: green;']");

   
    
}
