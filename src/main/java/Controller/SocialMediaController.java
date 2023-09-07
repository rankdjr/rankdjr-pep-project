package Controller;

import Model.Account;
import Model.Message;
import Service.SocialMediaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    SocialMediaService socialMediaService;
    public SocialMediaController(){
        socialMediaService = new SocialMediaService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountCreationHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postNewMessageHandler);
        app.get("/messages", this::getAllMessageHandler);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessageById);

        return app;
    }

    private void postAccountCreationHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account newAccount = socialMediaService.createAccount(account);
        if(newAccount == null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(newAccount));
        }
    }

    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account verifiedAccount = socialMediaService.userLogin(account);
        if(verifiedAccount == null){
            ctx.status(401);
        }else{
            ctx.json(mapper.writeValueAsString(verifiedAccount));
        }        
    }

    private void postNewMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = socialMediaService.newMessage(msg);
        if(newMessage == null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(newMessage));
        }        
    }

    private void getAllMessageHandler(Context ctx) throws JsonProcessingException {
        ctx.json(socialMediaService.getAllMessages());      
    }

    private void getMessageById(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = socialMediaService.getMessageById(id);
        if (message == null) {
            ctx.status(200);
        }else{
            ctx.json(mapper.writeValueAsString(message)); 
        }
    }

    private void deleteMessageById(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = socialMediaService.deleteMessageById(id);
        if (message == null) {
            ctx.status(200);
        }else{
            ctx.json(mapper.writeValueAsString(message)); 
        }
    }

}