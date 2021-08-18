package designPatterns.proxy;

import java.lang.reflect.Proxy;
import java.util.TreeMap;

public class Application {
    public static void main(String[] args){
        TestInterface test = new Test();
        ObjectHandler objectHandler = new ObjectHandler(test);
        test = (TestInterface) Proxy.newProxyInstance(test.getClass().getClassLoader(),
                test.getClass().getInterfaces(),objectHandler);
        
        
        
        TreeMap<Integer,Integer> treeMap = new TreeMap<>();
        treeMap.ceilingKey(2);
        treeMap.floorKey(2);

    }
}
