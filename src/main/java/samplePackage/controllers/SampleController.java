package samplePackage.controllers;

import com.intuit.graphql.rpas.*;
import com.intuit.rpas.client.RPASClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@Component
public class SampleController {

    @Autowired
    private RPASClient rpasClient;

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    @GetMapping("/")
    public void test(){

        for(int i=1;i<200;i++) {
            executorService.submit(() -> {
                RoleFilter roleFilter = new RoleFilter();
                roleFilter.setProfileId("9130348646069526");
                roleFilter.setAccountId("50000003");
                RoleResponseProjection roleResponseProjection = new RoleResponseProjection().permissions(new PermissionResponseProjection().canonicalName());
                try {
                    rpasClient.getRoles(roleFilter,roleResponseProjection, new HttpHeaders());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
