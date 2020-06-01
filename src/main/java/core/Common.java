package core;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Common {

	static Properties p = new Properties();
	static Writer report;
	static String ls = System.getProperty("line.separator");
	static WebDriver driver;
	
	static void getWebDriver(String browser) throws IOException {
		Logger.getLogger("").setLevel(Level.OFF);
		p.load(new FileInputStream("input.properties"));
		report = new FileWriter("./report_firefox.csv", false);
		String driverPath = "";
		
		switch (browser.toLowerCase()) {
		
		case "chrome": {
			if (getOS().toUpperCase().contains("MAC")
					|| getOS().toUpperCase().contains("LINUX"))
				driverPath = "/usr/local/bin/chromedriver";
			else if (getOS().toUpperCase().contains("WINDOWS"))
				driverPath = "c:\\windows\\chromedriver.exe";
			else
				throw new IllegalArgumentException("Browser dosn't exist for this OS");
			System.setProperty("webdriver.chrome.driver", driverPath);
			System.setProperty("webdriver.chrome.silentOutput", "true"); // Chrome
			ChromeOptions option = new ChromeOptions(); // Chrome
			option.addArguments("disable-infobars"); // Chrome
			option.addArguments("--disable-notifications"); // Chrome

			driver = new ChromeDriver();
			break;
		}
		
		case "edge": {
			if (getOS().toUpperCase().contains("MAC"))
				driverPath = "/usr/local/bin/msedgedriver.sh";
			else if (getOS().toUpperCase().contains("WINDOWS"))
				driverPath = "c:\\windows\\msedgedriver.exe";
			else
				throw new IllegalArgumentException("Browser dosn't exist for this OS");
			System.setProperty("webdriver.edge.driver", driverPath);
			
			driver = new EdgeDriver();
			break;
		}
		
		case "firefox": {
			if (getOS().toUpperCase().contains("MAC")
					|| getOS().toUpperCase().contains("LINUX"))
				driverPath = "/usr/local/bin/geckodriver.sh";
			else if (getOS().toUpperCase().contains("WINDOWS"))
				driverPath = "c:\\windows\\geckodriver.exe";
			else
				throw new IllegalArgumentException("Browser dosn't exist for this OS");
			System.setProperty("webdriver.gecko.driver", driverPath);
			
			driver = new FirefoxDriver();
			break;
		}
		
		case "safari": {
			if (!getOS().toUpperCase().contains("MAC"))
				throw new IllegalArgumentException("Browser dosn't exist for this OS");

			driver = new SafariDriver();
			break;
		}
		
		default: throw new WebDriverException("Unknown WebDriver");
		
		}
	}
	
	static void open(String browser, String url) throws IOException {
		getWebDriver(browser); 
		driver.get(url);
		driver.manage().window().setSize(new Dimension(1400,900));
	}

	static boolean isElementPresent(By by) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver.findElements(by).size() == 1;
	}

	static String getSize(By by) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return isElementPresent(by) && driver.findElement(by).isDisplayed()
				? driver.findElement(by).getRect().getDimension().toString().replace(", ", "x")
				: "null";
	}

	static String getLocation(By by) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return isElementPresent(by) && driver.findElement(by).isDisplayed()
				? driver.findElement(by).getRect().getPoint().toString().replace(", ", "x")
				: "null";
	}

	static void setValue(By by, String value) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (isElementPresent(by) && driver.findElement(by).isDisplayed())
			driver.findElement(by).sendKeys(value);
	}

	static String getValue(By by) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return isElementPresent(by) && driver.findElement(by).isDisplayed()
				&& !driver.findElement(by).getText().matches("") && driver.findElement(by).getText().length() < 100
						? driver.findElement(by).getText().trim()
						: "null";
	}

	static void submit(By by) {
		if (isElementPresent(by))
			driver.findElement(by).submit();
	}

	static String getOS() {
		return System.getProperty("os.name").toUpperCase();
	}

	static String getBrowser() {
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName();
		return browserName.substring(0, 1).toUpperCase() + browserName.substring(1);
	}

	static String getFileName() {
		String file = driver.getCurrentUrl().toString().trim(); 
		return file.substring(file.lastIndexOf('/') + 1);
	}

	static void waitTitlePage(String title) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.titleIs(title));
	}

	static void writeReportHeader(Writer report) throws IOException {
		report.write("#," + Common.getBrowser() + ",Page,Field,isPresent,Value, Size, Location");
		report.write(ls);
	}

	static void writeReportLine(String index, String fieldName, By by, Writer report) throws IOException {
		report.write(index + "," + Common.getBrowser() + "," + Common.getFileName() + "," + fieldName + ","
				+ Common.isElementPresent(by) + "," + Common.getValue(by) + "," + Common.getSize(by) + ","
				+ Common.getLocation(by) + "\n");
	}

	static void quit() throws IOException {
		driver.quit();
	}

}
