package login_IPO;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;

public class Ipo_Login {

    public static void main(String[] args) {
        // Set the path to your WebDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\hachn\\OneDrive\\Desktop\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // Maximize the browser window
        driver.manage().window().maximize();

        // Define the path to your CSV file
        String csvFilePath = "C:\\Users\\hachn\\OneDrive\\Documents\\credentials.csv";

        // Read the CSV file
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            String[] credentials;

            // Loop through all rows in the CSV file
            while ((credentials = csvReader.readNext()) != null) {
                String username = credentials[0];
                String password = credentials[1];

                // Open the login page
                driver.get("https://bsec-ipo-test.tappware.com/login");

                // Perform login
                WebElement usernameField = driver.findElement(By.id("username"));
                WebElement passwordField = driver.findElement(By.id("passwordInput"));
                WebElement loginButton = driver.findElement(By.id("kt_sign_in_submit"));

                usernameField.sendKeys(username);
                passwordField.sendKeys(password);
                loginButton.click();

                // Wait for redirection after login
                try {
                    Thread.sleep(10000); // Wait for 10 seconds (adjust as needed)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

                // Navigate to the desired page after login
                driver.get("https://bsec-ipo-test.tappware.com/ipo-application/process");

                // Perform actions on the desired page (if needed)

                // Perform hover action to reveal the logout button
                Actions actions = new Actions(driver);
                WebElement menuTrigger = driver.findElement(By.cssSelector("div.cursor-pointer.symbol.symbol-30px.symbol-md-40px"));
                actions.moveToElement(menuTrigger).perform();

                // Click the logout button
                WebElement logoutButton = driver.findElement(By.xpath("//*[@id='kt_header_user_menu_toggle']/div[2]/div[6]/a"));
                logoutButton.click();

                // Wait for logout to complete
                try {
                    Thread.sleep(5000); // Wait for 5 seconds to ensure logout is processed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
