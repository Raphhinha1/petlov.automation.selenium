package br.com.rocketskills.petlov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

class Cadastro {
	WebDriver driver;

	@BeforeEach
	void start(){
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
	}
	
	@AfterEach
	void finish(){
		driver.close();

	}

	@Test
	@DisplayName("Deve poder cadastrar um ponto de doação")
	void createPoint() {
		driver.get("https://petlov.vercel.app/signup");

		WebElement title = driver.findElement(By.cssSelector("h1"));
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		wait.until(d -> title.isDisplayed());
		assertEquals("Cadastro de ponto de doação", title.getText(), "Verificando o Slogan");

		WebElement name = driver.findElement(By.cssSelector("input[name=name]"));
		name.sendKeys("Raphael Point");

		WebElement email = driver.findElement(By.cssSelector("input[name=email]"));
		email.sendKeys("raphael@teste.com");	
		
		WebElement cep = driver.findElement(By.cssSelector("input[name=cep]"));
		cep.sendKeys("02567120");
		
		WebElement btnCep = driver.findElement(By.cssSelector("input[value='Buscar CEP']"));
		btnCep.click();

		WebElement addressNumber = driver.findElement(By.cssSelector("input[name=addressNumber]"));
		addressNumber.sendKeys("320");

		WebElement addressDetails = driver.findElement(By.cssSelector("input[name=addressDetails]"));
		addressDetails.sendKeys("teste");
		
		WebElement getDog = driver.findElement(By.xpath("//span[text()=\"Cachorros\"]/.."));
		getDog.click();

		WebElement submit = driver.findElement(By.className("button-register"));
		submit.click();
		
		WebElement result = driver.findElement(By.cssSelector("#success-page p"));
		Wait<WebDriver> waitResult = new WebDriverWait(driver, Duration.ofSeconds(2));
		waitResult.until(d -> result.isDisplayed());
		String target = "Seu ponto de doacão foi adicionado com sucesso. Juntos, podemos criar um mundo onde todos os animais recebam o amor e cuidado que merecem.";
		assertEquals(target, result.getText(), "Verificando a mensagem de sucesso.");

	}
}
