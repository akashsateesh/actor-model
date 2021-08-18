package designPatterns.proxy;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectHandler implements InvocationHandler {

    private Test test;

    public ObjectHandler(Object object){
        test = (Test) object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try{
            if(method.getName().equals("setTest")){
                System.out.println("Alert! Test object is being set");
            }
            method.invoke(test,args);
        }
        catch (InvocationTargetException e){
            e.printStackTrace();
        }
        return null;
    }
}
