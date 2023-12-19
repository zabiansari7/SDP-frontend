package de.srh.toolify.frontend.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.xml.xmp.PdfAXmpWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class PDFGen {
//    public static final String DEST = "results/invoice.pdf";
//
//    protected Font font12;
//    protected Font font12b;
//    protected Font font14;
//
//    public static void main(String[] args) throws IOException, DocumentException {
//        File file = new File(DEST);
//        file.getParentFile().mkdirs();
//        PDFGen app = new PDFGen();
//
//        // Assuming you have some way to obtain the purchase history, you can create a dummy one for testing
//        PurchaseHistory purchaseHistory = createDummyPurchaseHistory();
//
//        // Generate PDF for the PurchaseHistory
//        app.createPdf(purchaseHistory);
//    }
//
//    private static PurchaseHistory createDummyPurchaseHistory() {
//        // Create and return a dummy PurchaseHistory for testing
//        // You can replace this with your actual logic to obtain purchase history data
//        // For simplicity, I'm creating a dummy one here
//        PurchaseHistory purchaseHistory = new PurchaseHistory();
//        purchaseHistory.setPurchaseId(1L);
//        purchaseHistory.setUser(new User("John Doe"));
//        purchaseHistory.setDate(Instant.now());
//        purchaseHistory.setTotalPrice(BigDecimal.valueOf(500.00));
//        purchaseHistory.setInvoice(12345);
//        purchaseHistory.setAddress(new Address("123 Main St", "Cityville", "12345"));
//
//        // Adding dummy PurchaseHistoryItem
//        PuchaseHistoryItem item = new PuchaseHistoryItem();
//        item.setPurchaseItemsId(1L);
//        item.setQuantity(2);
//        item.setPurchasePrice(BigDecimal.valueOf(100.00));
//        item.setProductEntity(new Product("Product A"));
//
//        purchaseHistory.setPurchaseItemsEntities(List.of(item));
//
//        return purchaseHistory;
//    }
//
//    public PDFGen() throws DocumentException, IOException {
//        BaseFont bf = BaseFont.createFont("resources/fonts/OpenSans-Regular.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
//        BaseFont bfb = BaseFont.createFont("resources/fonts/OpenSans-Bold.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
//        font12 = new Font(bf, 12);
//        font12b = new Font(bfb, 12);
//        font14 = new Font(bf, 14);
//    }
//
//    public void createPdf(PurchaseHistory purchaseHistory) throws IOException, DocumentException {
//        Document document = new Document();
//        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
//        writer.createXmpMetadata();
//        writer.getXmpWriter().setProperty(PdfAXmpWriter.zugferdSchemaNS, PdfAXmpWriter.zugferdDocumentFileName, "ZUGFeRD-invoice.xml");
//        document.open();
//
//        // Add content to the PDF based on PurchaseHistory
//        addContent(document, purchaseHistory);
//
//        document.close();
//    }
//
//    private void addContent(Document document, PurchaseHistory purchaseHistory) throws DocumentException {
//        // Add content to the PDF based on PurchaseHistory
//        // You need to adjust this based on your actual structure
//        document.add(new Paragraph("Invoice ID: " + purchaseHistory.getInvoice(), font14));
//        document.add(new Paragraph("Date: " + purchaseHistory.getDate(), font12));
//        document.add(new Paragraph("User: " + purchaseHistory.getUser().getUsername(), font12));
//        document.add(new Paragraph("Address: " + purchaseHistory.getAddress().getFullAddress(), font12));
//
//        // Line items
//        PdfPTable table = new PdfPTable(4);
//        table.setWidthPercentage(100);
//        table.addCell(getCell("Item", Element.ALIGN_LEFT, font12b));
//        table.addCell(getCell("Quantity", Element.ALIGN_LEFT, font12b));
//        table.addCell(getCell("Unit Price", Element.ALIGN_LEFT, font12b));
//        table.addCell(getCell("Total", Element.ALIGN_LEFT, font12b));
//
//        for (PuchaseHistoryItem item : purchaseHistory.getPurchaseItemsEntities()) {
//            table.addCell(getCell(item.getProductEntity().getProductName(), Element.ALIGN_LEFT, font12));
//            table.addCell(getCell(String.valueOf(item.getQuantity()), Element.ALIGN_LEFT, font12));
//            table.addCell(getCell(item.getPurchasePrice().toString(), Element.ALIGN_LEFT, font12));
//            table.addCell(getCell(item.getPurchasePrice().multiply(BigDecimal.valueOf(item.getQuantity())).toString(), Element.ALIGN_LEFT, font12));
//        }
//
//        document.add(table);
//
//        // Total Price
//        document.add(new Paragraph("Total Price: " + purchaseHistory.getTotalPrice(), font12));
//    }
//
//    private PdfPCell getCell(String value, int alignment, Font font) {
//        PdfPCell cell = new PdfPCell();
//        cell.setUseAscender(true);
//        cell.setUseDescender(true);
//        Paragraph p = new Paragraph(value, font);
//        p.setAlignment(alignment);
//        cell.addElement(p);
//        return cell;
//    }
}
