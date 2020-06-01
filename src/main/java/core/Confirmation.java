package core;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Properties;
import org.openqa.selenium.By;

class Confirmation {
	static Properties p = new Properties();
	static Writer report;

	static void validate() throws IOException {
		p.load(new FileInputStream("./input.properties"));
		report = new FileWriter("./report_" + Common.getBrowser().toString().toLowerCase() + ".csv", true); 
		
		// 05 :: First Name
		Common.writeReportLine("05", "First Name", By.id(p.getProperty("fname_id")), report); 
		// 06 :: Last Name
		Common.writeReportLine("06", "Last Name", By.id(p.getProperty("lname_id")), report); 
		// 07 :: Email
		Common.writeReportLine("07", "Email", By.id(p.getProperty("email_id")), report); 
		// 08 :: Phone
		Common.writeReportLine("08", "Phone", By.id(p.getProperty("phone_id")), report);
		
		report.flush();
		report.close();
	}
}