package jms;

import javax.enterprise.context.ApplicationScoped;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class MessageServiceImpl implements MessageService {

    private List<Message> messages = new LinkedList<>();

    @Override
    public void saveMessage(String message) {
        messages.add(new Message(message,new Date()));
    }

    @Override
    public List<Message> getAllMessages() {
        return messages;
    }
}
