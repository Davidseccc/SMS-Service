package com.company.modules.SmsService.utils;

import com.company.modules.SmsService.model.PDU;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by fim on 11.07.17.
 */
public class PDUDecoder {
    PDU pdu;

    public static String decode(PDU enc){
         HEXSmsData.getPDUMetaInfo(enc);
        return null;
    }

}
