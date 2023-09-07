package Service;

import static org.mockito.ArgumentMatchers.isNotNull;

import DAO.SocialMediaDAO;
import Model.Account;
import Model.Message;

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

        if (acc == null || account.getPassword().compareTo(acc.getPassword()) != 0) 
            return null;
    
        return acc;
    }
        
} 

