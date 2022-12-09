package com.djigitbetFE.e2eTests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;

import static org.junit.jupiter.api.Assertions.*;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.*;

public class PlayerTests {

    String BaseUrl = "http://localhost:3000/";

    HomePage homePage = new HomePage();

    LoginPage loginPage = new LoginPage();

    SlotsPage slotsPage = new SlotsPage();

    FundingPage fundingPage = new FundingPage();

    ProfilePage profilePage = new ProfilePage();

    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open(BaseUrl);
        WebDriverRunner.getWebDriver().manage().deleteAllCookies();
        var driver = WebDriverRunner.getWebDriver();
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.localStorage.clear();");
        open(BaseUrl);
        WebDriverRunner.getWebDriver().navigate().refresh();
    }

    @Test
    public void login(){
        homePage.ProfileIconNOTLoggedIN.click();
        homePage.LoginInsideProfileICN.click();
        loginPage.bodyYouNeedEnableJava.click();
        loginPage.divUsername2.click();
       // loginPage.usernameInput.click();
        loginPage.usernameInput.sendKeys("d");
        loginPage.divPassword.click();
        loginPage.divPassword2.click();
        loginPage.paswordInput.sendKeys("ddd");
        loginPage.buttonSignIN.click();
        //wait for 5 sec
        sleep(1000);
        assertEquals(BaseUrl, WebDriverRunner.getWebDriver().getCurrentUrl());
    }


    @Test
    public void updateProfile(){
        login();
        homePage.ProfileIconLoggedIn.click();
        homePage.ProfileLI.click();
        profilePage.bodyYouNeedEnableJava.click();
        profilePage.divName.click();
        profilePage.inputName.sendKeys("dS");
        profilePage.buttonUpdate.click();
        assert(profilePage.pInformationUpdatedSuccessfully.isDisplayed());
    }


    @Test
    public void bet(){
        login();
        homePage.NavBarbuttonSlots.click();
        String availableCash = slotsPage.AvailableCashH1.getText().substring(16);
        int availableCashInt = Integer.parseInt(availableCash.substring(0, availableCash.length() - 1));
        slotsPage.betInput.click();
        slotsPage.betInput.sendKeys("1");
        slotsPage.SpinButton.click();
        //wait for 5 sec
        sleep(5000);
        String availableCashAfterBet = slotsPage.AvailableCashH1.getText().substring(16);
        int availableCashAfterBetInt = Integer.parseInt(availableCashAfterBet.substring(0, availableCashAfterBet.length() - 1));
        assertNotEquals(availableCashInt, availableCashAfterBetInt); // if we were not in java we coud have just compared that bought strings are difrent , but this is java 🤮🤣
    }

    @Test
    public void  addFunds(){
        login();
        homePage.NavBarbuttonFunding.click();
        String BalnceBefore = fundingPage.BalanceAmt.getText().substring(0, fundingPage.BalanceAmt.getText().length() - 2);
        int BalnceBeforeInt = Integer.parseInt(BalnceBefore);
        fundingPage.divAmount.click();
        fundingPage.divAmount2.click();
        fundingPage.inputAmount.sendKeys("1");
        fundingPage.buttonPayWithCard.click();
        sleep(1000);

        //switch to iframe
        switchTo().frame(0);
        fundingPage.EmailInput.click();
        fundingPage.EmailInput.sendKeys("SeleniumTester@abv.bg");
        fundingPage.CardNumberInput.click();

        fundingPage.CardNumberInput.sendKeys("4242");
        fundingPage.CardNumberInput.sendKeys("4242");
        fundingPage.CardNumberInput.sendKeys("4242");
        fundingPage.CardNumberInput.sendKeys("4242");

        fundingPage.CardExpiryInput.click();

        fundingPage.CardExpiryInput.sendKeys("0424");
        fundingPage.CardExpiryInput.sendKeys("24");

        fundingPage.CardCVCInput.click();
        fundingPage.CardCVCInput.sendKeys("424");
        fundingPage.stripePAYbtn.click();
        //switch to main window
        switchTo().defaultContent();
        sleep(10000);
        String BalnceAfter = fundingPage.BalanceAmt.getText().substring(0, fundingPage.BalanceAmt.getText().length() - 2);
        int BalnceAfterInt = Integer.parseInt(BalnceAfter);
        assert(BalnceAfterInt == BalnceBeforeInt+1);
    }




    
}
