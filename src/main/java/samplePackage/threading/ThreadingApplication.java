package samplePackage.threading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ThreadingApplication {
    
    public static void main(String[] args){
        
        final String s1 = "abc";
        
        String s2 = s1 + "def";
        
        String s3 = "abc" + "def";
        
        System.out.println(s2 == s3);
        
        String s4 = new StringBuilder("abc").toString();
        
        System.out.println(s1 == s4);
        
         
    }
    
}
