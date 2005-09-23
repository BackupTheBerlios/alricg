package org.d3s.alricg;

import org.d3s.alricg.controller.MessageListener;
import org.d3s.alricg.controller.Messenger;

public class MessengerMock extends Messenger {

    public void register(MessageListener listener) {
    }

    public void unregister(MessageListener listener) {
    }

    public void sendMessage(String titel, Level level, String text) {
    }

    public void sendFehler(String text) {
    }

    public void sendInfo(String text) {
    }

    public int showMessage(Level level, String text) {
        return 0;
    }
}
