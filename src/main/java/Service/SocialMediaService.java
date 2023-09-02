package Service;

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

    public Account createAccount(Account account){
        return appDAO.createAccount(account);
    }
} 

