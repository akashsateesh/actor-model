package samplePackage.loadBalancer;

import com.intuit.graphql.rpas.AssignRole;
import com.intuit.rpas.client.RPASClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class Controller {

    @Autowired
    private RPASClient rpasClient;

    private TokenBucket tokenBucket = new TokenBucket();

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    @GetMapping("/test")
    public void test(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization","Intuit_IAM_Authentication intuit_appid=Intuit.identity.authz.rpastestclient,intuit_app_secret=preprdyKPnQJCBcqpBi5amhBwZjK0QBj29Y1e183,intuit_token=V1-49-X38bzb4jv9wrb9pmdh1grp,intuit_userid=9130346920313776,intuit_token_type=IAM-Ticket");
        AssignRole assignRole = new AssignRole();
        assignRole.setAccountId("-1");
        assignRole.setProfileId("132163232233232");
        assignRole.setRoles(new ArrayList<String>(){
            {
                add("Blog.Moderator");
            }
        });
        if(tokenBucket.isActive("persona","offering",1000L,10)) {
            executorService.submit(() -> {
                rpasClient.assignRole(assignRole, httpHeaders);
            });
        }
    }
}
