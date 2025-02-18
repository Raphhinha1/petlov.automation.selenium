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

    public PontoDoacao(String nome, String email, String cep, Integer numero, String complemento, String pets) {
        this.nome = nome;
        this.email = email;
        this.cep = cep;
        this.numero = numero;
        this.complemento = complemento;
        this.pets = pets;
    }
}

class Cadastro {


    private void acessarFormulario() {
        open("https://petlov.vercel.app/signup");
        $("h1").should(text("Cadastro de ponto de doação"));
    }

    private void submeterFormulario(PontoDoacao ponto) {
        $("input[name=name]").type(ponto.nome);
        $("input[name=email]").setValue(ponto.email);
        $("input[name=cep]").setValue(ponto.cep);
        $("input[value='Buscar CEP']").click();
        $("input[name=addressNumber]").setValue(ponto.numero.toString());
        $("input[name=addressDetails]").setValue(ponto.complemento);
        $(By.xpath("//span[text()=\"" + ponto.pets + "\"]/..")).click();
        $(".button-register").click();
    }

    @Test
    @DisplayName("Deve poder cadastrar um ponto de doação")
    void caminhoFeliz() {
        PontoDoacao ponto = new PontoDoacao(
                "Estação Pet",
                "raphael@teste.com",
                "02567120",
                320,
                "teste",
                "Cachorros"
        );

        acessarFormulario();
        submeterFormulario(ponto);

        String target = "Seu ponto de doação foi adicionado com sucesso. Juntos, podemos criar um mundo onde todos os animais recebam o amor e cuidado que merecem.";
        $("#success-page p").should(text(target));
    }

    @Test
    @DisplayName("Não Deve cadastrar com email inválido")
    void emailIncorreto() {
        PontoDoacao ponto = new PontoDoacao(
                "email inválido",
                "raphael&teste.com",
                "02567120",
                320,
                "teste",
                "Gatos"
        );

        acessarFormulario();
        submeterFormulario(ponto);

        String target = "Informe um email válido";
        $(".alert-error").should(text(target));
    }

    @Test
    @DisplayName("Não Deve cadastrar sem informar o cep")
    void cepIncorreto() {
        PontoDoacao ponto = new PontoDoacao(
                "cep inválido",
                "raphael@teste.com",
                "",
                320,
                "teste",
                "Gatos"
        );

        acessarFormulario();
        submeterFormulario(ponto);

        String target = "Informe o seu CEP";
        $(By.xpath("//input[@name='cep']/following-sibling::span")).should(text(target));

    }

    @Test
    @DisplayName("Não Deve cadastrar com número menor que 1")
    void numeroIncorreto() {
        PontoDoacao ponto = new PontoDoacao(
                "cep inválido",
                "raphael@teste.com",
                "02567120",
                0,
                "teste",
                "Gatos"
        );

        acessarFormulario();
        submeterFormulario(ponto);

        String target = "Informe um número maior que zero";
        $(By.xpath("//input[@name='addressNumber']/following-sibling::span")).should(text(target));
    }

    @Test
    @DisplayName("Não Deve cadastrar sem informar o nome")
    void nomeIncorreto() {
        PontoDoacao ponto = new PontoDoacao(
                "",
                "XXXXXXXXXXXXXXXXX",
                "02567120",
                320,
                "teste",
                "Gatos"
        );

        acessarFormulario();
        submeterFormulario(ponto);

        String target = "Informe o seu nome completo";
        $(By.xpath("//input[@name='name']/following-sibling::span")).should(text(target));

    }

}
