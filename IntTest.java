import com.underarmour.predictspring.qa.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

//Challenge
//1 In a single test, perform the following combined validation:
//        2 Navigate to https://demoqa.com/text-box.
//        3 Programmatically fill the form with the following values: - Full Name: 'John Doe' -
//Email: 'john.doe@example.com' - Current Address: '123 Main St' - Permanent
//Address: '456 Secondary St'
//        4 Submit the form and verify that the output section displays the entered values
//correctly.
//5 Send an HTTP request to https://jsonplaceholder.typicode.com/posts/1 and verify that:
//        - The response status is 200 - The JSON contains the keys: userId, id, title, body - The
//id field equals 1
//        6 If all validations pass, print exactly: All tests passed.
//7 If any validation fails, throw an assertion error with a clear message.

public class IntTest extends BaseTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

    }

    @Test
    public void testing() {
        WebDriver driver = new ChromeDriver();

        String username = "John Doe";
        String userEmail = "john.doe@example.com";
        String currentAddress = "123 Main St";
        String permanentAddress = "456 Secondary St";

        driver.get("https://demoqa.com/text-box");
        driver.findElement(By.id("userName")).sendKeys(username);
        driver.findElement(By.id("userEmail")).sendKeys(userEmail);
        driver.findElement(By.id("currentAddress")).sendKeys(currentAddress);
        driver.findElement(By.id("permanentAddress")).sendKeys(permanentAddress);
        driver.findElement(By.id("submit")).click();

        Assert.assertEquals(driver.findElement(By.id("name")).getText(), username, "Wrong name displayed");
        Assert.assertEquals(driver.findElement(By.id("email")).getText(), userEmail, "Wrong email displayed");
        Assert.assertEquals(driver.findElement(By.id("currentAddress")).getText(), currentAddress, "Wrong current address displayed");
        Assert.assertEquals(driver.findElement(By.id("permanentAddress")).getText(), permanentAddress, "Wrong permanent address displayed");

        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get("/posts/1")
                .then().extract().response();

        response.prettyPrint();

        Assert.assertEquals(response.statusCode(), 200, "Status code mismatch");
        Assert.assertNotNull(response.jsonPath().getString("id"), "ID is null");
        Assert.assertNotNull(response.jsonPath().getString("userId"), "User ID is null");
        Assert.assertNotNull(response.jsonPath().getString("title"), "Title is null");
        Assert.assertNotNull(response.jsonPath().getString("body"), "Body is null");
        Assert.assertEquals(response.jsonPath().getString("id"), "1", "ID is incorrect");


        System.out.println("All tests passed.");
    }
}
