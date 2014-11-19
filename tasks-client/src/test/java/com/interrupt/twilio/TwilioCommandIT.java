package com.interrupt.twilio;

import com.interrupt.twilio.commands.SendTextCommand;
import org.junit.Test;

// Integration test for Twilio
public class TwilioCommandIT {
    @Test
    public void shouldSendTextMessage() {
        SendTextCommand command = new SendTextCommand("7017213796", "Sup Dude");
        command.execute();

        // now go look at your phone
    }
}