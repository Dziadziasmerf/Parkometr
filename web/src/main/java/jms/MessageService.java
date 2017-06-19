package jms;

import java.util.List;

public interface MessageService {

    void saveMessage(String message);

    List<Message> getAllMessages();
}
