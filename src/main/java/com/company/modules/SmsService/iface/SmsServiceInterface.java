package com.company.modules.SmsService.iface;

import com.company.modules.SmsService.SmsEvent;

/**
 * Created by fim on 11.05.17.
 */
public interface SmsServiceInterface {
    void setupSMS();
    void sendSMS(SmsEvent e);
    void propagateEvent(SmsEvent e);


}
