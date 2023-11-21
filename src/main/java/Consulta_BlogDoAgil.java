import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class Consulta_BlogDoAgil {
	
	private WebDriver driver;	
	
	@Test
	public void CenarioSucessoTest () {
	        Inicializa();
	        realizarPesquisaPositiva("Teste");
	        verificarResultadoDaPesquisa("Resultados da busca por: Teste");

	    }
			
	    public void Inicializa() {
	    //informa o caminho do chromedriver
	    	System.setProperty("webdriver.gecko.driver", "/Users/hiago/eclipse/Drivers/geckodriver.exe");
	    	
	    //acessa o site e verifica se o título da página corresponde ao esperado
	        driver = new FirefoxDriver();
	        driver.get("https://blogdoagi.com.br");
	        Assert.assertEquals("O título da página não corresponde ao esperado.", "Blog Do Agi - Agibank", driver.getTitle());
	    }

	    //Abre a lupa e pesquisa pela palavra Teste
	    private void realizarPesquisaPositiva(String pesquisa) {
			WebElement	BotaoLupa = driver.findElement(By.id("search-open"));
			BotaoLupa.click();
			WebElement CampodePesquisa = driver.findElement(By.className("search-field"));
			CampodePesquisa.sendKeys("Teste");
			WebElement BotaoPesquisar = driver.findElement(By.className("search-submit"));
			BotaoPesquisar.click();
	    }
	    //Verifica se a pesquisa trouxe resultados
	    private void verificarResultadoDaPesquisa(String resultadoEsperado) {
	        WebElement tituloElemento = driver.findElement(By.xpath("//header//h1"));
	        String textoDoTitulo = tituloElemento.getText();
	        String textoDesejado = "Resultados da busca por: Teste";
	        Assert.assertEquals("O título da página não corresponde ao esperado.", textoDesejado, textoDoTitulo);
	        
	    //rola a pagina para verificar os resultados tragos	        
	        WebElement elemento_alvo = driver.findElement(By.id("blog-wrap"));
	        Actions actions = new Actions(driver);
	        for (int i = 0; i < 5; i++) {            
	            actions.moveToElement(elemento_alvo);	      
	            actions.sendKeys(Keys.PAGE_DOWN);	            
	            actions.perform();
	            try {
	                Thread.sleep(1000);  
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	     //clica no botão que mostra mais resultados 
	        WebElement BotaoPostAntigos = driver.findElement(By.id("infinite-handle"));
	        BotaoPostAntigos.click();   
	            try {
	                Thread.sleep(1000);  
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        
	     //Abre uma das materias e finaliza o teste
	        String url = "https://blogdoagi.com.br/2021/07/26/chatbots/" ;
	        WebElement MateriaAleatoriat = driver.findElement(By.xpath("//a[@href='" + url + "']"));
	        MateriaAleatoriat.click();
	        Assert.assertEquals("O título da página não corresponde ao esperado.", 
	        		"Chatbots: O Que São E Como Eles Podem Ajudar O Seu Negócio - Blog Do Agi", driver.getTitle()); 
	        driver.quit();
	    }
  
	    
		@Test
		public void CenarioExcecaoTest () {
		        Inicializa();
		        realizarPesquisaNegativa("ASDFCVC");
		}
		
			    private void realizarPesquisaNegativa(String ResultadoNegativo) {
					WebElement	BotaoLupa = driver.findElement(By.id("search-open"));
					BotaoLupa.click();
					WebElement CampodePesquisa = driver.findElement(By.className("search-field"));
					CampodePesquisa.sendKeys("ASDFCVC");
					WebElement BotaoPesquisar = driver.findElement(By.className("search-submit"));
					BotaoPesquisar.click();
					WebElement tituloElemento = driver.findElement(By.xpath("//header//h1"));
			        String textoDoTitulo = tituloElemento.getText();
			        String textoDesejado = "Nenhum resultado";
			        Assert.assertEquals("O título da página não corresponde ao esperado.", textoDesejado, textoDoTitulo);        
			        driver.quit();
		    }	    
	}