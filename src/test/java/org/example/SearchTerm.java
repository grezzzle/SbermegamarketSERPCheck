package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;


public class SearchTerm {

    public WebDriver driver;
    public SearchTerm(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }


    /* Поисковая строка */
    @FindBy(xpath = "//*[contains(@class, 'search-field-input')]")
    private WebElement searchBar;
    /* Кнопка поиска в поисковой строке */
    @FindBy(xpath = "//*[contains(@class, 'header-search-form__send-button')]")
    private WebElement searchButton;


    public void SendRequest(){
        /* Send request */
        searchBar.click();
        searchBar.sendKeys(ConfProperties.getProperty("request"));
        searchButton.sendKeys(Keys.ENTER);
        /* Wait until items load */
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        /* Check - First item loaded? */
        driver.findElement(By.xpath("//*[contains(@class, 'catalog-item')]"));
    }

    public List<WebElement> ItemsList(){
        return driver.findElements(By.xpath("//*[contains(@class, 'catalog-item')]//div[contains(@class, 'item-title')]/a"));
    }

}
