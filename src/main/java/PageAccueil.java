import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageAccueil extends PageBandeau implements PageInterfaceJDD  {
	

	
	
	//Tous les Xpaths n�cessairent � l'execution - TEst 5-1 Mathis
	@FindBy (xpath="//span[@title='Cr�er un nouveau projet']")
	WebElement bouton_NouveauProjet;
	
	@FindBy (xpath="//span[.='Nom']/../../following-sibling::td/div/input")
	WebElement text_NomProjet;
	
	@FindBy (xpath="//span[.='Mod�le']/../../following-sibling::td/div/span/i/input")
	WebElement text_ModeleProjet;
	
	@FindBy (xpath="//label[.='G�n�rer le code']/../input")
	WebElement checkbox_GenererCode;
	
	@FindBy (xpath="//span[.='Date de d�but']/../../following-sibling::td/div/i//i[@class='z-datebox-btn']")
	WebElement bouton_CalendrierDateDebut;
	
	@FindBy (xpath="//span[.='Ech�ance']/../../following-sibling::td/div/i//i[@class='z-datebox-btn']")
	WebElement bouton_CalendrierEcheance;
	
	@FindBy (xpath="//table[@id='_z_4-mid']/tbody/tr/td[contains(@class, '-seld')]")
	WebElement calendrier_JourSelectionne;
	
	@FindBy (xpath="//td[.='Accepter']")
	WebElement bouton_Accepter;
	
	@FindBy (xpath="/html/body/div[1]/div/div[1]/div[2]/div/div/div/div[1]/div[1]/div/table/tbody/tr/td/table/tbody/tr[1]/td/table/tbody/tr/td/table/tbody/tr/td[3]/span")
	WebElement bouton_AnnulerEdition;
	
	@FindBy (xpath="//table/tbody/tr/td[.='OK']/span/table/tbody/tr/td[.='OK']")
	WebElement bouton_AnnulerEditionOk;
	
	@FindBy (xpath="//div[@class='z-window-modal-cl']/div/div/div/table[@class='z-hbox']/tbody/tr/td/table/tbody/tr/td/span/table/tbody/tr/td[.='Annuler']")
	WebElement bouton_AnnulerEditionCancel;

	@FindBy(xpath = "//tr[@valign='top']//span[text()='Calendrier']")
    public WebElement fil_d_ariane_calendrier;

	@FindBy(xpath = "/html/body/div[1]/div/div[1]/div[2]/div/div/div/div[2]/div/div/span/div/div/div[2]/div/div/div/div/div[3]/div/div[3]/table/tbody[2]/tr/td[9]/div/table/tbody/tr/td/table/tbody/tr/td[3]")
    public WebElement bouton_supprimer_projet1;

	//Xpath - Test 5-2 - Mathis
	@FindBy(xpath="//tbody/tr/td/table/tbody/tr/td[.=\"Liste des projets\"]")
	public WebElement bouton_ListeProjet;
	

	public PageAccueil(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	//Nouveau Kati - Test 2
	public PageListeProjet clickListeProjet() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bouton_ListeProjet.click();
		return PageFactory.initElements(driver, PageListeProjet.class);
	}
	
	//Test 5-1 -Mathis
	public PageAccueil CreerNouveauProjet() {
		wait = new WebDriverWait(driver, 15);
		//Pause sinon ca marche pas ca cours
	    wait.until(ExpectedConditions.visibilityOf(bouton_NouveauProjet));
		//pas de test 2 
		bouton_NouveauProjet.click();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//pas de test 3 - remplissage de champ
		text_NomProjet.sendKeys(NomProjet);
		checkbox_GenererCode.click();
		bouton_CalendrierDateDebut.click();
		String jour = calendrier_JourSelectionne.getText();
		
		//Calcul jour calendrier + click
		int jourInt = Integer.parseInt(jour);
		int jour5 = jourInt + 5;
		int jour15 = jourInt + 15;
		WebElement calendrier_j5 = driver.findElement(By.xpath("//table[@id='_z_4-mid']/tbody/tr/td[@_dt='" + jour5 +"' and not (@class='z-calendar-wkday z-outside')]"));;
		calendrier_j5.click();
		bouton_CalendrierEcheance.click();
		WebElement calendrier_j15 = driver.findElement(By.xpath("//table[@id='_z_6-mid']/tbody/tr/td[@_dt='" + jour15 + "' and not (@class='z-calendar-wkday z-outside')]"));
		calendrier_j15.click();
		bouton_Accepter.click();
		//La page change un peu donc par pr�caution je la recharge
		return PageFactory.initElements(driver, PageAccueil.class);
	}
	
	public PageAccueil testBoutons() {
		//pause sinon ca ne foncionne pas
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//pas de test 7 - Cliquer sur annuler �dition
		bouton_AnnulerEdition.click();
		
		//pas de test 8 - Cliquer sur annuler dans la pop-up
		bouton_AnnulerEditionCancel.click();

		//pas de test 9 - Cliquer sur annuler �dition
		bouton_AnnulerEdition.click();
		
		//pas de test 10 - Cliquer sur ok dans la pop-up
		bouton_AnnulerEditionOk.click();
		
		return PageFactory.initElements(driver, PageAccueil.class);
	}
	
	public void verifPage() {
		
		//Pas de test11
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Actions a = new Actions(driver);
        a.moveToElement(driver.findElement(By.xpath("//button[contains(text(),'Calendrier')]"))).moveToElement(driver.findElement(By.xpath("//a[@href='/libreplan/planner/index.zul;orders_list']"))).click().build().perform();
        
        //
        

        }
	
	public PageMachines chooseOptionMenuMachine (WebElement mouseHover, WebElement mouseClick) throws InterruptedException {
		Actions action = new Actions(driver);
		action.moveToElement(mouseHover).perform();
		Thread.sleep(2000);
		mouseClick.click();
		return PageFactory.initElements(driver, PageMachines.class);
	}
	
	public PageProfils chooseOptionMenuProfils (WebElement mouseHover, WebElement mouseClick) throws InterruptedException {
		Actions action = new Actions(driver);
		action.moveToElement(mouseHover).perform();
		Thread.sleep(2000);
		mouseClick.click();
		return PageFactory.initElements(driver, PageProfils.class);
	}
	
	public void supprimerProjet() throws AWTException {
		bouton_supprimer_projet1.click();
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
	}
	}


