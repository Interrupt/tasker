package com.interrupt.twilio;

import org.junit.Test;

// Integration test for Twilio
public class TwilioClientIT {
    @Test
    public void shouldSendTextMessage() {
        TwilioClient client = new TwilioClient();
        client.sendText("7017213796", "Sup Dude");
    }
}