package com.github.ToDoMVCFramework;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Ignore;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class MVFramework {
	WebDriver driver;
	Utilities utility = new Utilities();

	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@Test(priority=1)
	public void MainFrameworkPage() throws InterruptedException {

		driver.get("http://todomvc.com/");

		driver.manage().window().maximize();	

		// Store the text from Main page.
		String text = driver.findElement(By.xpath("//a[@href='examples/react']")).getText();
		System.out.println("The MV Framework is :"+text);

		// Open React Framework page.
		driver.findElement(By.xpath("//a[@href='examples/react']")).click();  

		// Get the URL and store in a String.
		String url = driver.getCurrentUrl();

		// Verify the URL contains React
		Assert.assertEquals(url.contains("react"), true, "The URL opened is incorrect.");

		// Get the header of the page.
		String header = driver.findElement(By.xpath("//header//h3")).getText();

		// Verify that header is React.
		Assert.assertEquals(header, "React","The header is incorrect.");

		// Get the first footer text.
		String firstFooterText = driver.findElement(By.xpath("(//footer/p)[1]")).getText();

		// Verify first footer text.
		Assert.assertEquals(firstFooterText, "Double-click to edit a todo", "The first footer text is incorrect.");

		// Get the second footer text.
		String secondFooterText = driver.findElement(By.xpath("(//footer/p)[2]")).getText();

		// Verify second footer text.
		Assert.assertEquals(secondFooterText, "Created by petehunt", "The second footer text is incorrect.");

		// Get the third footer text.
		String thirdFooterText = driver.findElement(By.xpath("(//footer/p)[3]")).getText();

		// Verify first footer text.
		Assert.assertEquals(thirdFooterText, "Part of TodoMVC", "The third footer text is incorrect.");

		// Get the Place holder from the text box.
		String placeHolder = driver.findElement(By.xpath("//input[@class='new-todo']")).getAttribute("placeholder");

		// Verify the place holder.
		Assert.assertEquals(placeHolder, "What needs to be done?", "The place holder text is incorrect.");


	}

	@Test(priority=2)
	public void FooterLinks() throws InterruptedException {

		// Click on To Do MVC link on Footer.
		driver.findElement(By.xpath("(//footer//p)[3]//a")).click();

		// Verify that home page is displayed.
		String homePage = driver.getCurrentUrl();	  
		Assert.assertEquals(homePage, "http://todomvc.com/","Home page is not displayed");

		// Navigate back to React page.
		driver.navigate().back();

		// Click on creator link Pete Hunt.
		driver.findElement(By.xpath("(//footer//p)[2]//a")).click();

		Thread.sleep(3000);
		
		// Verify Pete Hunt's GIT Hub page is displayed.
		String url = driver.getCurrentUrl();
		Assert.assertEquals(url.contains("github.com/petehunt"), true,"GIT Hub page of Pete Hunt is not displayed.");

		// Navigate back to React page.
		driver.navigate().back();

	}

	@Test(priority=3)
	public void validatingToDos() throws InterruptedException {

		// Create an array of todos to add to the todos list.
		String[] todos = {"Create Test Plan", "Create Test Scenarios",
				"Create Test cases","Execute Test cases","Report Defects","Generate Reports"};

		WebElement todoTextbox = driver.findElement(By.xpath("//input[@class='new-todo']"));

		// Click on the textbox and enter the todos.
		for(int i=0;i<todos.length;i++)
		{
			//todoTextbox.click();
			todoTextbox.sendKeys(todos[i]);

			// Click Enter Keys.
			todoTextbox.sendKeys(Keys.ENTER);
		}

		// Get the todos count added.
		String count = driver.findElement(By.xpath("//span[@class='todo-count']//strong")).getText();

		// Convert text into String;
		int todosCount = utility.convertStringToInteger(count);

		// Verify that the todos list count is same as array length.
		Assert.assertEquals(todos.length, todosCount,"The items added to the Todo list differs.");

		// Verify that check boxes are displayed for todos added by clicking on them.
		List<WebElement> checkboxes = driver.findElements(By.className("toggle"));
		System.out.println("The list is of :"+checkboxes.size());

		for(WebElement web : checkboxes)
		{
			web.click();

		}


	}	 

	@Test(priority=4)
	public void removeCompletedTodos() throws InterruptedException {

		// Add few more todos to the list.

		WebElement todoTextbox = driver.findElement(By.xpath("//input[@class='new-todo']"));

		// Click on the textbox and enter the todos.
		for(int i=0;i<5 ;i++)
		{
			//todoTextbox.click();
			todoTextbox.sendKeys("Some more texts "+i);

			// Click Enter Keys.
			todoTextbox.sendKeys(Keys.ENTER);
		}	

		// Click on All tab.
		driver.findElement(By.xpath("//a[@class='selected']")).click();

		// Count all the todos.
		int totalTodos = driver.findElements(By.xpath("//ul[@class='todo-list']//li")).size();

		// click on Clear Completed link.
		driver.findElement(By.xpath("//button[@class='clear-completed']")).click();

		// Click on All tab.
		driver.findElement(By.xpath("//a[@class='selected']")).click();

		// Verify that todos count has decreased.
		int leftTodos = driver.findElements(By.xpath("//ul[@class='todo-list']//li")).size();

		if(leftTodos>totalTodos)
		{
			Assert.assertTrue(false, "The leftTodos are not less that totalTodos");
		}		


	}

	//@Ignore
	@Test(priority=5)
	public void selectingToDosInFibonacciSequence() throws InterruptedException {

		WebElement todoTextbox = driver.findElement(By.xpath("//input[@class='new-todo']"));

		// Click on the textbox and enter the todos.
		for(int i=0;i<100 ;i++)
		{
			//todoTextbox.click();
			todoTextbox.sendKeys("Some more texts "+i);

			// Click Enter Keys.
			todoTextbox.sendKeys(Keys.ENTER);
		}	  

		// Select the check box based on fibonacci sequence.
		// Verify that check boxes are displayed for todos added by clicking on them.
		List<WebElement> checkboxes = driver.findElements(By.className("toggle"));
		int count = checkboxes.size();		
		int t1 = 0, t2 = 1;
		for(int i=1;i<=count;++i)
		{
			System.out.print(t1 + " + ");
			int sum = t1 + t2;
			t1 = t2;
			t2 = sum;
			System.out.println("The list is :"+t2);
			if(t2<100)
			{
				driver.findElement(By.xpath("(//input[@class='toggle'])["+t2+"]")).click();
			}

			if(t2>100)
				break;

		}
	}


	@AfterTest
	public void afterTest() {
		driver.close();
	}

}
