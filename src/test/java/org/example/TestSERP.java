package org.example;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class TestSERP {

    public static SearchTerm searchTerm;
    public static WebDriver driver;

    @BeforeClass
    public static void Setup(){
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        driver = new ChromeDriver();
        searchTerm = new SearchTerm(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(ConfProperties.getProperty("link"));
        /* Waiting until geolocation div loading*/
        driver.findElement(By.xpath("//*[contains(@class, 'header-user-address__selector')]"));
    }

    @Test
    public void CheckSERP(){
        searchTerm.SendRequest();
        List<WebElement> items = searchTerm.ItemsList();
        List<String> items_str =  new ArrayList<>();
        /* Switch list of catalog items to list of string with item names*/
        items.stream().map(WebElement::getText).map(String::toLowerCase).forEach(items_str::add);
        /* Check that all items on page contains request in title*/
        Assert.assertTrue(items_str.stream().anyMatch(n -> n.contains(ConfProperties.getProperty("request"))));
    }

    @AfterClass
    public static void Close(){
        /* Close browser */
        driver.close();
    }


}
