package samplePackage.controllers;

import com.intuit.rpas.RPASClientSDKConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({RPASClientSDKConfiguration.class})
public class SampleControllerApp {

    public static void main(String[] args){
        SpringApplication.run(SampleControllerApp.class,args);
    }

}
