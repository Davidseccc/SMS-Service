package SMSServiceServer.SmsService.utils;


import com.company.ComPortSendReceive;
import com.company.modules.SmsService.iface.CommInterface;
import com.company.modules.SmsService.model.PDU;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fim on 11.07.17.
 */
public class RXTX_Factory implements CommInterface{

    private String port;
    ComPortSendReceive comPortSendReceive;


    public RXTX_Factory(String port) {
        this.port = port;
        comPortSendReceive = ComPortSendReceive.getInstance();
        comPortSendReceive.setPort(port);
    }

    public boolean openPort(){
        return comPortSendReceive.open(port);
    }
    public boolean closePort(){
        return comPortSendReceive.close();
    }

    public List<PDU> getSMS(int index){
        List<PDU> list = new ArrayList<PDU>();
        String out = comPortSendReceive.writeAndRead("AT+CMGF=0");
        System.out.println(out);
        out = comPortSendReceive.writeAndRead("AT+CMGL="+ index);
        //System.out.println(out);
        if(validateResult(out)) {
            String[] messages = out.split("\n");
            if (messages.length > 2) {
                messages = Arrays.copyOfRange(messages, 1, messages.length - 2);
                int c = 0;
                for (int i = 1; i < messages.length; i += 2) {
                    PDU p = new PDU(messages[i].trim());
                    list.add(p);
                    c++;
                }
                return list;
            }
        }
        return null;
    }

    public List<PDU> getAllSMS(){
        List<PDU> list = new ArrayList<PDU>();
        String out = comPortSendReceive.writeAndRead("AT+CMGF=0");
        System.out.println(out);
        out = comPortSendReceive.writeAndRead("AT+CMGL=4");
        if(validateResult(out)) {
            String[] messages = out.split("\n");
            if (messages.length > 2) {
                messages = Arrays.copyOfRange(messages, 1, messages.length - 2);
                int c = 0;
                for (int i = 1; i < messages.length; i += 2) {
                    PDU p = new PDU(messages[i].trim());
                    list.add(p);
                    c++;
                }
                return list;
            }
        }
        return null;
    }

    public boolean sendSMS(String number, String message){
        String out = comPortSendReceive.writeAndRead("AT+CMGF=1");
        System.out.println(out);
        out = comPortSendReceive.writeAndRead("AT+CMGS="+"\"+420607223970\"");
        System.out.println(out);
        out = comPortSendReceive.writeAndRead("KOLIK KREDIT");
        System.out.println(out);
        out = comPortSendReceive.writeAndRead("\u001A");
        System.out.println(out);
        return true;
    }

    public String getSignalStrenght(){
        String out = comPortSendReceive.writeAndRead("AT+CSQ");
        //+CSQ: 24,99
        System.out.println(out);
        if(validateResult(out)){
            out = out.trim().substring(15,20);
            return out;
        }
        return "0,0";

    }

    private boolean validateResult(String out) {
        out = out.trim();
        if(out.substring(out.length()-2, out.length()).equals("OK")){
            return true;
        }
        else{
            System.out.println("Could not found \"OK\" in:" + out );
            return false;
        }
    }

}
