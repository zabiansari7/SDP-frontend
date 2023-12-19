package de.srh.toolify.frontend.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.xmp.XMPException;
import de.srh.toolify.frontend.client.RestClient;
import de.srh.toolify.frontend.data.PuchaseHistoryItem;
import de.srh.toolify.frontend.data.PurchaseHistory;
import de.srh.toolify.frontend.data.ResponseData;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

public class PDFGen {
    public static final String DEST = "results/invoice.pdf";

    protected Font font12;
    protected Font font12b;
    protected Font font14;

/*    public static void main(String[] args) throws IOException, DocumentException, XMPException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        PDFGen app = new PDFGen();

        // Assuming you have some way to obtain the purchase history, you can create a dummy one for testing
        PurchaseHistory purchaseHistory = getPurchaseByInvoice(67653);

        // Generate PDF for the PurchaseHistory
        app.createPdf(purchaseHistory, DEST);
    }*/

    public PDFGen() throws DocumentException, IOException {
        BaseFont bf = BaseFont.createFont("src/main/resources/fonts/OpenSans-Regular.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
        BaseFont bfb = BaseFont.createFont("src/main/resources/fonts/OpenSans-Bold.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
        font12 = new Font(bf, 12);
        font12b = new Font(bfb, 12);
        font14 = new Font(bf, 14);
    }

    public void createPdf(PurchaseHistory purchaseHistory, String destinationPath) throws IOException, DocumentException, XMPException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(destinationPath));
        document.open();
        // Add content to the PDF based on PurchaseHistory
        addContent(document, purchaseHistory);
        document.close();
    }

    private void addContent(Document document, PurchaseHistory purchaseHistory) throws DocumentException {
        // Add content to the PDF based on PurchaseHistory
        // You need to adjust this based on your actual structure
        document.add(new Paragraph("Invoice ID: " + purchaseHistory.getInvoice(), font14));
        document.add(new Paragraph("Date: " + purchaseHistory.getDate().toString().replace("T", " Time: ").replace("Z", ""), font12));
        document.add(new Paragraph("User: " + purchaseHistory.getUser().getEmail(), font12));
        document.add(new Paragraph("Address: " + purchaseHistory.getAddress().getCityName(), font12));

        // Line items
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.addCell(getCell("Item", Element.ALIGN_LEFT, font12b));
        table.addCell(getCell("Quantity", Element.ALIGN_LEFT, font12b));
        table.addCell(getCell("Unit Price", Element.ALIGN_LEFT, font12b));
        table.addCell(getCell("Total", Element.ALIGN_LEFT, font12b));

        for (PuchaseHistoryItem item : purchaseHistory.getPurchaseItemsEntities()) {
            table.addCell(getCell(item.getProductEntity().getName(), Element.ALIGN_LEFT, font12));
            table.addCell(getCell(String.valueOf(item.getQuantity()), Element.ALIGN_LEFT, font12));
            table.addCell(getCell("€" + item.getProductEntity().getPrice(), Element.ALIGN_LEFT, font12));
            table.addCell(getCell("€" + String.valueOf(item.getProductEntity().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))), Element.ALIGN_LEFT, font12));
        }

        document.add(table);

        // Total Price
        document.add(new Paragraph("Total Price: " + "€" + purchaseHistory.getTotalPrice(), font12));
    }

    private PdfPCell getCell(String value, int alignment, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        Paragraph p = new Paragraph(value, font);
        p.setAlignment(alignment);
        cell.addElement(p);
        return cell;
    }

    private static PurchaseHistory getPurchaseByInvoice(int invoice) {
        RestClient client = new RestClient();
        ObjectMapper mapper = HelperUtil.getObjectMapper();
        ResponseData data =client.requestHttp("GET", "http://localhost:8080/private/purchase/history/" + invoice, null, null);
        JsonNode purchaseNode = data.getNode();
        PurchaseHistory purchaseHistory = mapper.convertValue(purchaseNode, PurchaseHistory.class);
        return purchaseHistory;
    }
}
