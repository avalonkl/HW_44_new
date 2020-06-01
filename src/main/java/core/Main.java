package core;

public class Main {
	
	static String browser;
	
	public static void main(String[] args) throws Exception { 
//		browser = "chrome";
//		java -cp ./target/HW_44_new-1.0.jar -Dbrowser="firefox" core.Main; open ./report_firefox.csv
		browser = System.getProperty("browser"); // -Dbrowser="firefox" 
		if(browser == null) {
			System.err.println("Please provide browser: -Dbrowser=firefox"); 
			System.exit(0);
			}
		else if(!browser.equalsIgnoreCase("chrome") && !browser.equalsIgnoreCase("firefox") && !browser.equalsIgnoreCase("safari") && !browser.equalsIgnoreCase("edge")) {
			System.err.println("Framework supports only: Chrome, Firefox, Safari or Edge"); 
			System.exit(0);
			}
		SignUp.open(browser); 
		SignUp.validate(); 
		Confirmation.validate(); 
		Common.quit();
		}
	}