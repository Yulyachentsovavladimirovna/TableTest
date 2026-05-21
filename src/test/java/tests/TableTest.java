package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TableTest {
    private WebDriver driver;
    private static final String TEST_URL = "https://www.urn.su/ui/basic_test/";
    private static final String URL_HEIHEI = "www.heihei.ru";
    private static final String URL_TOPBICYCLE = "www.topbicycle.ru";
    private static final String EXPECTED_TOPIC_HEIHEI = "Travel, Holidays";
    private static final String EXPECTED_TOPIC_TOPBICYCLE = "Bicycles";

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(TEST_URL);
    }

    @Test
    void shouldExtractTopics() {
        List<WebElement> rows = driver.findElements(By.cssSelector("table tbody tr"));
        String actualTopicHeihei = "Тема не найдена";
        String actualTopicTopbicycle = "Тема не найдена";
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() == 3) {
                String url = cells.get(0).getText();
                String topic = cells.get(1).getText();
                if (url.contains(URL_HEIHEI)) {
                    actualTopicHeihei = topic;
                } else if (url.contains(URL_TOPBICYCLE)) {
                    actualTopicTopbicycle = topic;
                }
            }
        }
        assertEquals(EXPECTED_TOPIC_HEIHEI, actualTopicHeihei,
                "Тема для www.heihei.ru не соответствует ожидаемой");
        assertEquals(EXPECTED_TOPIC_TOPBICYCLE, actualTopicTopbicycle,
                "Тема для www.topbicycle.ru не соответствует ожидаемой");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
