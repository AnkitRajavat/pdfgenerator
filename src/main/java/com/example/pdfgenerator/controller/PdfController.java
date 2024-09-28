package com.example.pdfgenerator.controller;


import com.example.pdfgenerator.model.BillPdf;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.IOException;
import java.util.*;

@RestController
public class PdfController {

    @Autowired
    private BillPdf billPdf;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @GetMapping("/bill")
    public void generateTransactionPdf(HttpServletResponse response) throws IOException, DocumentException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=transaction.pdf");

        Random random = new Random();
    BillPdf transaction = new BillPdf();

        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setDate(new Date());
        transaction.setModeofpayment(generateRandomModeOfPayment(random));
        transaction.setBbpsTranscationId(UUID.randomUUID().toString());
        transaction.setOpId("OP" + random.nextInt(10000));
        transaction.setConnectionNo("CN" + random.nextInt(1000000));
        transaction.setStatus(generateRandomStatus(random));
        transaction.setService(generateRandomService(random));
        transaction.setCcf("CCF" + random.nextInt(10000));
        transaction.setAmount(random.nextInt(100000) + 100);

    // Prepare the data model for Thymeleaf
    Map<String, Object> data = new HashMap<>();
        data.put("transaction", transaction);

    // Generate HTML from Thymeleaf template
    String htmlContent = renderHtml(data);

    // Generate PDF using Flying Saucer (ITextRenderer)
    ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(response.getOutputStream(), false);
        renderer.finishPDF();
}
    private String generateRandomModeOfPayment(Random random) {
        String[] modes = {"Credit Card", "Debit Card", "Net Banking", "UPI"};
        return modes[random.nextInt(modes.length)];
    }

    private String generateRandomStatus(Random random) {
        String[] statuses = {"Success", "Pending", "Failed"};
        return statuses[random.nextInt(statuses.length)];
    }

    private String generateRandomService(Random random) {
        String[] services = {"Electricity", "Water", "Gas", "Internet"};
        return services[random.nextInt(services.length)];
    }
private String renderHtml(Map<String, Object> data) {
    Context context = new Context();
    context.setVariables(data);
    return templateEngine.process("BillPayment", context);


}

}
