package Service;

import static org.mockito.ArgumentMatchers.isNotNull;

import DAO.SocialMediaDAO;
import Model.Account;
import Model.Message;

import java.util.List;

public class SocialMediaService {
    SocialMediaDAO appDAO;

    public SocialMediaService(){
        appDAO = new SocialMediaDAO();
    }

    public SocialMediaService(SocialMediaDAO appDAO){
        this.appDAO = appDAO;
    }

    public Account createAccount(Account account) {
        if (appDAO.getAccountByUsername(account.getUsername()) != null ||
            account.getUsername().isBlank() ||
            account.getPassword().length() < 4)
            return null;
        
        return appDAO.createAccount(account);
    }

    public Account userLogin(Account account) {
        Account acc = appDAO.getAccountByUsername(account.getUsername());

        if (acc == null || account.getPassword().compareTo(acc.getPassword()) != 0) {
            System.out.println("invalid credentials");
            return null;
        }
            
        return acc;
    }

    public Message newMessage(Message message) {
        if (message.getMessage_text().length() >= 255) {
            System.out.println("character limit exceeded");
            return null;
        }
        if (message.getMessage_text().isBlank()) {
            System.out.println("message cannot be blank");
            return null;
        }
        if (appDAO.getAccountByID(message.getPosted_by()) == null) {
            System.out.println("posting account does not exist");
            return null;
        }
        
        return appDAO.createMessage(message);
    }

    public List<Message> getAllMessages() {
        return appDAO.getAllMessages();
    }

    public Message getMessageById(int id) {
        return appDAO.getMessageById(id);
    }
    
    public Message deleteMessageById(int id) {
        Message message = appDAO.getMessageById(id);
        if (appDAO.deleteMessageById(id) == 0)
            return null;

        return message;
    }

    public Message updateMessageById(int id, String message_text) {
        if (appDAO.getMessageById(id) == null) {
            System.out.println("invalid message id");
            return null;
        }
        if (message_text.isBlank()) {
            System.out.println("message cannot be blank");
            return null;
        }
        if (message_text.length() >= 255) {
            System.out.println("character limit exceeded");
            return null;
        }

        appDAO.updateMessageById(id, message_text);

        return appDAO.getMessageById(id);
    }
} 

