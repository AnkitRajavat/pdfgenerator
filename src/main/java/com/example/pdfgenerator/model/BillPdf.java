package com.example.pdfgenerator.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Component;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class BillPdf {


    private String TransactionId;
    private Date date;
    private String Modeofpayment;
    private String BbpsTranscationId;
    private String opId;
    private String status;
    private String connectionNo;
    private String service;
    private String ccf;
    private long amount;


}
