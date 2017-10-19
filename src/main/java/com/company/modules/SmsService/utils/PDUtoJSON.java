package com.company.modules.SmsService.utils;

import com.company.modules.SmsService.model.PDU;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fim on 17.07.17.
 */
public class PDUtoJSON {

    public static String pduToJson(PDU pdu){

        JSONObject obj = new JSONObject();
        obj.put("raw",  pdu.getHEX());
        obj.put("smsc", pdu.getSmsc());
        obj.put("sender", pdu.getSender());
        obj.put("timestamp", pdu.getTimeStamp());
        obj.put("TP_DSC", pdu.getTP_DSC());
        obj.put("TP_DSC_DESC", pdu.getTP_DSC_DESC());
        obj.put("copression", pdu.getCopression());
        obj.put("cls", pdu.getCls());
        obj.put("alphabet", pdu.getAlphabet());
        obj.put("userdata", pdu.getUserData());
        return obj.toJSONString();
    }

    public static String pduListToJson(List<PDU> list){
        return JSONArray.toJSONString(list);
    }

    public static List<PDU> JsonToPduList(String json){
        List<PDU> list = new ArrayList<PDU>();

        return list;
    }

    public static PDU JsonToPdu(String json){
        PDU pdu = null;

        return pdu;
    }
}
