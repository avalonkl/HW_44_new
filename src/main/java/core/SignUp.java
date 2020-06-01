package core;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Properties;
import org.openqa.selenium.By;

class SignUp {
	static Properties p = new Properties();
	static Writer report;

	static void open(String browser) throws IOException {
		p.load(new FileInputStream("./input.properties"));
		Common.open(browser, p.getProperty("url"));
	}

	static void validate() throws IOException {
		p.load(new FileInputStream("./input.properties"));
		report = new FileWriter("./report_" + Common.getBrowser().toString().toLowerCase() + ".csv", false);
		Common.writeReportHeader(report);
		
        // 01 :: First Name
		Common.setValue(By.id(p.getProperty("fname_id")), p.getProperty("fname_value"));
		Common.writeReportLine("01", "First Name", By.id(p.getProperty("fname_id")), report); 
		
		// 02 :: Last Name
		Common.setValue(By.id(p.getProperty("lname_id")), p.getProperty("lname_value"));
		Common.writeReportLine("02", "Last Name", By.id(p.getProperty("lname_id")), report);
		
		// 03 :: Email
		Common.setValue(By.id(p.getProperty("email_id")), p.getProperty("email_value")); 
		Common.writeReportLine("03", "Email", By.id(p.getProperty("email_id")), report); 
		
		// 04 :: Phone
		Common.setValue(By.id(p.getProperty("phone_id")), p.getProperty("phone_value")); 
		Common.writeReportLine("04", "Phone", By.id(p.getProperty("phone_id")), report);
		
		// SUBMIT
		Common.submit(By.id(p.getProperty("submit_id"))); 
		Common.waitTitlePage("Confirmation");
		
		report.flush(); 
		report.close();
	}
}