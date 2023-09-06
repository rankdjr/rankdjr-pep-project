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
        app.post("localhost:8080/login", this::postAccountCreationHandler);

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

}