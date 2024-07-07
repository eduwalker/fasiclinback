package com.example.enfermagemapirest.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class PdfLinkGenerator {

    public static String generatePdfLink(Long codProf, Long anamneseId, Date dataAnamnese) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmm").format(dataAnamnese);
        return codProf + anamneseId + timestamp + ".pdf";
    }
}
