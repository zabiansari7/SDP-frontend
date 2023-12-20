package de.srh.toolify.frontend.utils;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.xmp.XMPException;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.server.StreamResource;
import de.srh.toolify.frontend.data.PuchaseHistoryItem;
import de.srh.toolify.frontend.data.PurchaseHistory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

public class PDFGen {
    protected Font font9;
    protected Font font10;
    protected Font font12;
    protected Font font12b;
    protected Font font14;
    protected Font font14b;

    public static void main(String[] args) throws IOException, DocumentException, XMPException {

        PDFGen app = new PDFGen();

        // Assuming you have some way to obtain the purchase history, you can create a dummy one for testing
        PurchaseHistory purchaseHistory = HelperUtil.getPurchaseByInvoice(40716);

        // Generate PDF for the PurchaseHistory
        app.createPdf(purchaseHistory);
    }


    public PDFGen() throws DocumentException, IOException {
        BaseFont bf = BaseFont.createFont("src/main/resources/fonts/OpenSans-Regular.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
        BaseFont bfb = BaseFont.createFont("src/main/resources/fonts/OpenSans-Bold.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
        font9 = new Font(bf, 9);
        font10 = new Font(bf, 10);
        font12 = new Font(bf, 12);
        font12b = new Font(bfb, 12);
        font14 = new Font(bf, 14);
        font14b = new Font(bfb, 14);
    }

    public Anchor createPdf(PurchaseHistory purchaseHistory) throws IOException, DocumentException, XMPException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();
        // Add content to the PDF based on PurchaseHistory
        addContent(document, purchaseHistory);
        document.close();

        String pdfFileName = "invoice_"+ purchaseHistory.getInvoice() + ".pdf";
        StreamResource resource = new StreamResource(pdfFileName, () -> new ByteArrayInputStream(baos.toByteArray()));

        Anchor anchor = new Anchor(resource, "");
        //anchor.getElement().setAttribute("download", true);
        anchor.setTarget("_blank");
        return anchor;

    }

    private void addContent(Document document, PurchaseHistory purchaseHistory) throws DocumentException, IOException {

        document.add(new Paragraph("Invoice No: " + purchaseHistory.getInvoice(), font14b));
        document.add(new Paragraph(" "));

        Image logo = Image.getInstance("src/main/resources/images/toolify_logo.jpeg");
        logo.scaleAbsolute(125, 50);

        PdfPTable logoTable = new PdfPTable(1);
        logoTable.setWidthPercentage(100);
        PdfPCell logoCell = new PdfPCell(logo);
        logoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        logoCell.setBorder(Rectangle.NO_BORDER);
        logoTable.addCell(logoCell);

        // Add logo to the document
        document.add(logoTable);

        document.add(new Paragraph("TOOLIFY GmbH", font12b));
        //document.add(new Paragraph("Address:", font10));
        document.add(new Paragraph("Bonhoefferstraße - 13", font10));
        document.add(new Paragraph("69123 - Heidelberg", font10));
        document.add(new Paragraph(" "));

        document.add(new Paragraph(" "));
        document.add(new Paragraph(purchaseHistory.getUser().getFullName(), font12b));
        //document.add(new Paragraph("Address:", font10));
        document.add(new Paragraph(purchaseHistory.getAddress().getAddressLineOne(), font10));
        document.add(new Paragraph(purchaseHistory.getAddress().getAddressLineTwo(), font10));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Date: " + purchaseHistory.getDate().toString().replace("T", " Time: ").replace("Z", ""), font10));
        document.add(new Paragraph(" "));


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
        document.add(new Paragraph("Total Price: " + "€" + purchaseHistory.getTotalPrice(), font12b));
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
}
