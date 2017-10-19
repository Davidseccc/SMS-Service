package com.company.modules.SmsService.iface;

import com.company.modules.SmsService.model.PDU;

import java.util.List;

/**
 * Created by fim on 17.07.17.
 */
public interface CommInterface {

        public boolean openPort();
        public boolean closePort();

        public List<PDU> getSMS(int index);

        public List<PDU> getAllSMS();

        public boolean sendSMS(String number, String message);

        public String getSignalStrenght();


}
