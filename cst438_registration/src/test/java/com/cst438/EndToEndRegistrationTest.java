package com.cst438;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;

/*
 * This example shows how to use selenium testing using the web driver 
 * with Chrome browser.
 * 
 *  - Buttons, input, and anchor elements are located using XPATH expression.
 *  - onClick( ) method is used with buttons and anchor tags.
 *  - Input fields are located and sendKeys( ) method is used to enter test data.
 *  - Spring Boot JPA is used to initialize, verify and reset the database before
 *      and after testing.
 *      
 *    Make sure that TEST_COURSE_ID is a valid course for TEST_SEMESTER.
 *    
 *    URL is the server on which Node.js is running.
 */

@SpringBootTest
public class EndToEndRegistrationTest {

	public static final String CHROME_DRIVER_FILE_LOCATION = "C:/chromedriver-win64/chromedriver.exe";;

	public static final String URL = "http://localhost:3000";

	public static final String TEST_USER_EMAIL = "test@csumb.edu";

	public static final int TEST_COURSE_ID = 40442; 
	
	public static final int TEST_STUDENT_ID = 2;

	public static final String TEST_SEMESTER = "2021 Fall";

	public static final int SLEEP_DURATION = 1000; // 1 second.


	@Test
	public void deleteStudentTest() throws Exception {

	    System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
	    WebDriver driver = new ChromeDriver();
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	    try {
	        driver.get(URL);
	        Thread.sleep(SLEEP_DURATION);

	        driver.findElement(By.linkText("Admin")).click();
	        Thread.sleep(SLEEP_DURATION);

	        driver.findElement(By.xpath("//tr[1]//button[text()='Edit']")).click();
	        Thread.sleep(SLEEP_DURATION);

	        String studentName = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();
	        String studentEmail = driver.findElement(By.xpath("//tr[1]//td[2]")).getText();

	        driver.findElement(By.xpath("//button[text()='Delete']")).click();
	        Thread.sleep(SLEEP_DURATION);

	        assertThrows(NoSuchElementException.class, () -> {
	            driver.findElement(By.xpath("//td[text()='" + studentName + "']"));
	            driver.findElement(By.xpath("//td[text()='" + studentEmail + "']"));
	        });

	    } catch (Exception ex) {
	        throw ex;
	    } finally {
	        driver.quit();
	    }
	}

	@Test
	public void addStudentTest() throws Exception {

	    System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
	    WebDriver driver = new ChromeDriver();
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	    try {
	        driver.get(URL);
	        Thread.sleep(SLEEP_DURATION);

	        driver.findElement(By.linkText("Admin")).click();
	        Thread.sleep(SLEEP_DURATION);

	        WebElement nameField = driver.findElement(By.xpath("//input[@placeholder='Name']"));
	        WebElement emailField = driver.findElement(By.xpath("//input[@placeholder='Email']"));
	        
	        nameField.clear();
	        emailField.clear();

	        nameField.sendKeys("NameTest");
	        emailField.sendKeys("emailtest@example.com");
	        driver.findElement(By.xpath("//button[text()='Add']")).click();
	        Thread.sleep(SLEEP_DURATION);

	        assertNotNull(driver.findElement(By.xpath("//td[text()='NameTest']")));
	        assertNotNull(driver.findElement(By.xpath("//td[text()='emailtest@example.com']")));

	    } catch (Exception ex) {
	        throw ex;
	    } finally {
	        driver.quit();
	    }
	}


	@Test
	public void updateStudentTest() throws Exception {

	    System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
	    WebDriver driver = new ChromeDriver();
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	    try {
	        driver.get(URL);
	        Thread.sleep(SLEEP_DURATION);

	        driver.findElement(By.linkText("Admin")).click();
	        Thread.sleep(SLEEP_DURATION);

	        driver.findElement(By.xpath("//tr[1]//button[text()='Edit']")).click();
	        Thread.sleep(SLEEP_DURATION);
	        
            WebElement nameField = driver.findElement(By.id("editname"));
            WebElement emailField = driver.findElement(By.id("editmail"));
	        
	        nameField.clear();
	        emailField.clear();  // this one does not clear idk y 
	        //Thread.sleep(SLEEP_DURATION+2000);

	        nameField.sendKeys("x");
	        emailField.sendKeys("x@example.com");
	        driver.findElement(By.xpath("//button[text()='Update']")).click();
	        Thread.sleep(SLEEP_DURATION);

	        assertNotNull(driver.findElement(By.xpath("//td[text()='x']")));
	        assertNotNull(driver.findElement(By.xpath("//td[text()='x@example.com']")));

	    } catch (Exception ex) {
	        throw ex;
	    } finally {
	        driver.quit();
	    }
	}





	
	
}
