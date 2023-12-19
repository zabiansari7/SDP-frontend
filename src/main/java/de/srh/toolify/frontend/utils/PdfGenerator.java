package de.srh.toolify.frontend.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.nimbusds.jose.shaded.gson.JsonObject;

public class PdfGenerator {
	
//	public static void main(String[] args) throws BadElementException, IOException {
//		final String DEST = "toolify_invoice.pdf";
//		final String imgSrc = "src/main/resources/images/toolify_logo.jpeg";
//
//		Image img = Image.getInstance(imgSrc);
//		img.setAbsolutePosition(100f, 650f); // Adjust position as needed
//       // image.scaleAbsolute(100, 100); // Adjust size as needed
//
//        // Add image to the document
//        //document.add(image);
//	}
//	
//	
//	public static byte[] generatePdf(String jsonData) throws IOException {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        try {
//            Document document = new Document();
//            PdfWriter.getInstance(document, outputStream);
//            document.open();
//
//            // Parse JSON and add content to the PDF
//            //addContentFromJson(document, jsonData);
//
//            document.close();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//
//        return outputStream.toByteArray();
//    }
//
//    private static void addContentFromJson(Document document, JsonNode jsonData) throws DocumentException {
//        // Parse JSON and add content to the document
//        // Adjust this based on your actual JSON structure and document layout
//    	
//    	document.add(null);
//    }
   
}
