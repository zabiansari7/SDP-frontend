package de.srh.toolify.frontend.data;

import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
    	testRegex();
    }
    
    private void name() {
		
	}
    
    private static void testRegex() {
    	 String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9]+\\.[A-Za-z]{2,}$";
         Pattern pattern = Pattern.compile(emailPattern);

         // Test the pattern with some email addresses
         String[] emails = {
             "john.doe@example.com",
             "jane_doe@company.co",
             "invalid_email@com",
             "missing_at_sign.com",
             "asbbb@.gamil.com"
         };

         for (String email : emails) {
             System.out.println(email + " is valid: " + pattern.matcher(email).matches());
         }
	}
}