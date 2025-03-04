import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;


public class TestCreationProjet {
	
	WebDriver driver;
	WebDriverWait wait;

	@Before

	public void setup() throws Exception {

		//Initialisation du driver
		System.setProperty("webdriver.gecko.driver", "src/main/resources/driver/geckodriver.exe");
		driver = new FirefoxDriver();
		
		//initialiser le fichier.properties
		Properties propertyJDD = new Properties();
	    propertyJDD.load(new FileInputStream("src/main/resources/JDD.properties/JDD.properties"));
	    
	    //aller sur le site du challenge
	    driver.get(propertyJDD.getProperty("URL"));
	    driver.manage().window().maximize();

	}

	@After

	public void teardown() {
	   // PageAccueil pageAccueil = PageFactory.initElements(driver, PageAccueil.class);
	   // pageAccueil.bouton_supprimer_projet1.click();
		driver.quit();
	}

	
	@Test
	public void test5_1() throws Exception {

	    //v�rifier que la page est correcte
	    assertEquals("LibrePlan: acc�s utilisateur", driver.getTitle());
	    
	    //initialiser la PageConnexion
	    PageConnexion page_connexion = PageFactory.initElements(driver, PageConnexion.class);
	    
	    //se logger
	    page_connexion.logIn(driver, "admin", "admin");
	    //Test Mathis 1 (5-1)
	    PageAccueil pageAccueil = PageFactory.initElements(driver, PageAccueil.class);
		pageAccueil.CreerNouveauProjet();
		pageAccueil.testBoutons();
		pageAccueil.verifPage();
	    PageListeProjet pageListeProjets = PageFactory.initElements(driver, PageListeProjet.class);
		pageListeProjets.verifTableau();
		

	}
	
	@Test
	public void test5_2() throws Exception {
		//Test Mathis 2 (5-2)
		
	    //v�rifier que la page est correcte
	    assertEquals("LibrePlan: acc�s utilisateur", driver.getTitle());
	    
	    //initialiser la PageConnexion
	    PageConnexion page_connexion = PageFactory.initElements(driver, PageConnexion.class);
	    
	    //se logger
	    page_connexion.logIn(driver, "admin", "admin");
	    
	    PageAccueil pageAccueil = PageFactory.initElements(driver, PageAccueil.class);
	    PageListeProjet page_ListeProjet = pageAccueil.clickListeProjet();
	    page_ListeProjet.ajouterTache();
	    PageAccueil pageAccueil2 = PageFactory.initElements(driver, PageAccueil.class);
	    pageAccueil2.supprimerProjet();
	}
	


}
