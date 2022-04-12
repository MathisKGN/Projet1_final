import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class TestCreationCalendrierClass {
	
	WebDriver driver;
	WebDriverWait wait;
	Logger logger = Logger.getLogger(TestCreationCalendrierClass.class.getName());

	@Before

	public void setup() throws Exception {

		//Initialisation du driver
		System.setProperty("webdriver.gecko.driver", "src/main/resources/driver/geckodriver.exe");
		driver = new FirefoxDriver();
		
		//initialiser le fichier.properties
		Properties propertyJDD = new Properties();
	    propertyJDD.load(new FileInputStream("src/main/resources/JDD.properties/JDD.properties"));
	    
	    //aller sur le site du PROJET
	    driver.get(propertyJDD.getProperty("URL"));
	    driver.manage().window().maximize();

	}

	@After

	public void teardown() {
		driver.quit();
	}

	
	@Test
	public void test() throws Exception {
		
		wait = new WebDriverWait(driver, 15); // Explicit wait
		
		// Create an instance of FileHandler that write log to a file called app.log. Each new message will be appended at the at of the log file.
        FileHandler fileHandler = new FileHandler("app.log", true);
        logger.addHandler(fileHandler);
		
        try {
		  //vérifier que la page est correcte
	    assertEquals("LibrePlan: accès utilisateur", driver.getTitle());
	    
	    //initialiser la PageConnexion
	    PageConnexion page_connexion = PageFactory.initElements(driver, PageConnexion.class);
	    
	    logger.info("AAAAAAAAAAAAAAAAAAAAAAAA"); 
	    //se connecter à la page d'accueil
	    PageAccueil page_accueil = page_connexion.logIn(driver,"admin","admin");
	    
	    //vérification page accueil
	    WebElement fil_d_ariane_calendrier = wait.until(ExpectedConditions.visibilityOf(page_accueil.fil_d_ariane_calendrier)); // Explicit wait
	    boolean verifPage = fil_d_ariane_calendrier.isDisplayed();

        assertTrue(verifPage);
	
	    //accéder à la page de la liste des calendriers
	   PageListeCalendrier page_liste_calendrier = page_accueil.clicItemCalendrier(driver);
	   
	   //vérification page liste calendrier
	   WebElement titre_liste_calendrier = wait.until(ExpectedConditions.visibilityOf(page_liste_calendrier.Titre_Liste_Calendrier)); // Explicit wait
	   WebElement colonne_nom = wait.until(ExpectedConditions.visibilityOf(page_liste_calendrier.Colonne_Nom));
	   WebElement colonne_opérations = wait.until(ExpectedConditions.visibilityOf(page_liste_calendrier.Colonne_Opérations));
	   WebElement colonne_hérité_date = wait.until(ExpectedConditions.visibilityOf(page_liste_calendrier.Colonne_Hérité_Date));
	   WebElement colonne_héritage_a_jour = wait.until(ExpectedConditions.visibilityOf(page_liste_calendrier.Colonne_Héritages_A_Jour));
	    boolean verifPage1 = titre_liste_calendrier.isDisplayed()&& colonne_nom.isDisplayed()&&colonne_opérations.isDisplayed()
	    		&&colonne_hérité_date.isDisplayed()&&colonne_héritage_a_jour.isDisplayed();

       assertTrue(verifPage1);
       logger.severe("MMMMMMMMMMMMMMMMMMMM");
	   
	   //accéder à la page de création des calendriers
	   
	   PageCreationCalendrier page_creation_calendrier = page_liste_calendrier.clicCreerCalendrier(driver);
	   
	   //vérification page création calendrier
	   
	   WebElement titre_créer_calendrier = wait.until(ExpectedConditions.visibilityOf(page_creation_calendrier.Titre_Creer_Calendrier)); // Explicit wait
	   WebElement données_calendrier = wait.until(ExpectedConditions.visibilityOf(page_creation_calendrier.Données_Calendrier));
	   WebElement enregistrer_button = wait.until(ExpectedConditions.visibilityOf(page_creation_calendrier.Enregistrer_button));
	   WebElement enregistrer_continuer_button = wait.until(ExpectedConditions.visibilityOf(page_creation_calendrier.Enregistrer_Continuer_button));
	   WebElement annuler_button = wait.until(ExpectedConditions.visibilityOf(page_creation_calendrier.Annuler_button));
	    boolean verifPage2 = titre_créer_calendrier.isDisplayed()&& données_calendrier.isDisplayed()&&enregistrer_button.isDisplayed()
	    		&&enregistrer_continuer_button.isDisplayed()&&annuler_button.isDisplayed();

       assertTrue(verifPage2); 
       
       //créer un calendrier
       
       boolean verif3 = page_creation_calendrier.checkBoxCodeVerif();
		assertTrue(verif3);
		
		page_creation_calendrier.CreerCalendrier(driver,"Calendrier - Test 1");
		
	   //vérification création calendrier
		WebElement calendrier_test_1 = wait.until(ExpectedConditions.visibilityOf(page_liste_calendrier.Calendrier_Test_1));
		boolean verifPage4 = titre_liste_calendrier.isDisplayed()&& calendrier_test_1.isDisplayed();

       assertTrue(verifPage4);
       
       //créer une dérive
       page_liste_calendrier.CreerDérive(driver);
       
       //vérification de la page de dérive
       
       WebElement type_dérive = wait.until(ExpectedConditions.visibilityOf(page_creation_calendrier.Type_Dérive));
       boolean verifPage5 = titre_créer_calendrier.isDisplayed()&& type_dérive.isDisplayed();
       assertTrue(verifPage5);
       //vérification case nom vide
       assertEquals(page_creation_calendrier.Nom_Fieldset.getAttribute("value"), "");
      
       //Créer un calendrier dérivé - nom non conforme
      
      	boolean verif6 = page_creation_calendrier.checkBoxCodeVerif();
		assertTrue(verif6);
		
		page_creation_calendrier.CreerCalendrier(driver,"Calendrier - Test 1");
		
		//vérification du message de non conformité
		WebElement message_non_conforme = wait.until(ExpectedConditions.visibilityOf(page_creation_calendrier.Message_Non_Conforme));
	       boolean verifPage7 = message_non_conforme.isDisplayed();
	       assertTrue(verifPage7);
	   
	     //Créer un calendrier dérivé conforme
	       
	       boolean verif8 = page_creation_calendrier.checkBoxCodeVerif();
			assertTrue(verif8);
			
			page_creation_calendrier.Creer_continuer_Calendrier(driver,"Calendrier - Test Calendrier Dérivé");
			
		//vérification création calendrier dérivé
			
			WebElement titre_dérive = wait.until(ExpectedConditions.visibilityOf(page_creation_calendrier.Titre_Dérive));
			WebElement message_confirmation_dérive = wait.until(ExpectedConditions.visibilityOf(page_creation_calendrier.Message_confirmation_dérive));
		       boolean verifPage9 = titre_dérive.isDisplayed()&&message_confirmation_dérive.isDisplayed();
		       assertTrue(verifPage9);
		       
		//Retour page de gestion des calendriers
			
		   page_creation_calendrier.Annuler_Calendrier(driver);
			
		//vérification retour page liste	
			WebElement calendrier_dérive_test_1 = wait.until(ExpectedConditions.visibilityOf(page_liste_calendrier.Calendrier_dérive_test_1));
			boolean verifPage10 = titre_liste_calendrier.isDisplayed()&& calendrier_dérive_test_1.isDisplayed();

	       assertTrue(verifPage10);	
		
	       //affichage calendrier dérivé
	       
	       page_liste_calendrier.Minusbuttonclick(driver);
	       
	       //vérification que le calendrier dérivé n'est plus affiché
	       assertEquals(page_liste_calendrier.Minus_button_close.getAttribute("class"), "z-dottree-ico z-dottree-root-close");
		
	       //cliquer sur le bouton créer une copie d'un calendrier
	       
	       page_liste_calendrier.Copybuttonclick(driver);
	       
	       //vérification affichage page creation suite à copie
	          
		    boolean verifPage11 = titre_créer_calendrier.isDisplayed();
	       assertTrue(verifPage11);
	       assertEquals(page_creation_calendrier.Nom_Fieldset.getAttribute("value"), "Calendrier - Test 1");
	       assertEquals(page_creation_calendrier.Type_Field.getText(), "Calendrier source");
	       
	      //Créer une copie non conforme
	       
	       page_creation_calendrier.Creer_continuer_Calendrier(driver,"Calendrier - Test 1");
	       
	      //vérification message erreur de la copie
	       
	       WebElement message_copie_non_conforme = wait.until(ExpectedConditions.visibilityOf(page_creation_calendrier.Message_Copie_Non_Conforme));
	       boolean verifPage12 = message_copie_non_conforme.isDisplayed();
	       assertTrue(verifPage12);
	       
	      //créer une copie conforme
	       
	       boolean verif13 = page_creation_calendrier.checkBoxCodeVerif();
			assertTrue(verif13);
	       page_creation_calendrier.CreerCalendrier(driver,"Calendrier - Test 2");
	       
	      //vérification copie conforme
	       
	       WebElement message_test_enregistré = wait.until(ExpectedConditions.visibilityOf(page_liste_calendrier.Message_test_enregistré));
	       WebElement calendrier_test_2 = wait.until(ExpectedConditions.visibilityOf(page_liste_calendrier.Calendrier_test_2));
			boolean verifPage14 = titre_liste_calendrier.isDisplayed()&& message_test_enregistré.isDisplayed()&&calendrier_test_2.isDisplayed();

	       assertTrue(verifPage14);
	      
	     //Suppression calendrier 1 et 2 pour clean database
	       
	       page_liste_calendrier.Supprimerbuttonclick(driver);
	       
	     //vérification Supression
	       
	       WebElement message_suppression_test_1 = wait.until(ExpectedConditions.visibilityOf(page_liste_calendrier.Message_supression_test_1));
	       WebElement message_suppression_test_2= wait.until(ExpectedConditions.visibilityOf(page_liste_calendrier.Message_supression_test_2));
	       WebElement message_suppression_test_dérivé= wait.until(ExpectedConditions.visibilityOf(page_liste_calendrier.Message_supression_test_dérivé));
			boolean verifPage15 = message_suppression_test_1.isDisplayed()&& message_suppression_test_2.isDisplayed()&&message_suppression_test_dérivé.isDisplayed();

	       assertTrue(verifPage15);
        } catch (Exception e) {
			PageListeCalendrier.takeSnapShot(driver, PageListeCalendrier.getNameFile());
			logger.info("Exception while taking screenshot "+e.getMessage());
			throw e;
		}
	      
	}

}

