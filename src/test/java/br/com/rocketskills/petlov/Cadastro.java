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

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

class PontoDoacao {
	String nome;
	String email;
	String cep;
	Integer numero;
	String complemento;
	String pets;

	public PontoDoacao(String nome, String email,String cep, Integer numero, String complemento, String pets){
		this.nome = nome;
		this.email = email;
		this.cep = cep;	
		this.numero = numero;
		this.complemento = complemento;
		this.pets = pets;
	}
}

class Cadastro {
	@Test
	@DisplayName("Deve poder cadastrar um ponto de doação")
	void createPoint() {

		// Pré-condição
		PontoDoacao ponto = new PontoDoacao(
			"Estação Pet",
			"raphael@teste.com",
			"02567120",
			320,
			"teste",
			"Cachorros"
		);

		open("https://petlov.vercel.app/signup");
		$("h1").should(text("Cadastro de ponto de doação"));

		// Ação
		$("input[name=name]").type(ponto.nome);
		$("input[name=email]").setValue(ponto.email);
		$("input[name=cep]").setValue(ponto.cep);
		$("input[value='Buscar CEP']").click();
		$("input[name=addressNumber]").setValue(ponto.numero.toString());
		$("input[name=addressDetails]").setValue(ponto.complemento);
		$(By.xpath("//span[text()=\"" + ponto.pets + "\"]/..")).click();
		$(".button-register").click();

		// Resultado esperado
		String target = "Seu ponto de doação foi adicionado com sucesso. Juntos, podemos criar um mundo onde todos os animais recebam o amor e cuidado que merecem.";
		$("#success-page p").should(text(target));
	}
}
