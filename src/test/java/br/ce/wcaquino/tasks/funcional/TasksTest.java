package br.ce.wcaquino.tasks.funcional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {
	
	public WebDriver acessarAplicacao() throws MalformedURLException {
//		WebDriver driver = new ChromeDriver();
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.0.4:4444/wd/hub"), cap);
		driver.navigate().to("http://192.168.0.4:8001/tasks");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			//clicar em Add todo
			driver.findElement(By.id("addTodo")).click();
			//Escrever descrição
			driver.findElement(By.id("task")).sendKeys("Descricao");
			//escrever data
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
			//clicar salvar
			driver.findElement(By.id("saveButton")).click();
			//verificar mensagem sucesso
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Success!", message);
		} finally {
			//fechar o browser
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			//clicar em Add todo
			driver.findElement(By.id("addTodo")).click();
			//escrever data
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
			//clicar salvar
			driver.findElement(By.id("saveButton")).click();
			//verificar mensagem sucesso
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the task description", message);
		} finally {
			//fechar o browser
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			//clicar em Add todo
			driver.findElement(By.id("addTodo")).click();
			//Escrever descrição
			driver.findElement(By.id("task")).sendKeys("Descricao");
			//clicar salvar
			driver.findElement(By.id("saveButton")).click();
			//verificar mensagem sucesso
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the due date", message);
		} finally {
			//fechar o browser
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			//clicar em Add todo
			driver.findElement(By.id("addTodo")).click();
			//Escrever descrição
			driver.findElement(By.id("task")).sendKeys("Descricao");
			//escrever data
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2010");
			//clicar salvar
			driver.findElement(By.id("saveButton")).click();
			//verificar mensagem sucesso
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Due date must not be in past", message);
		} finally {
			//fechar o browser
			driver.quit();
		}
	}
}
