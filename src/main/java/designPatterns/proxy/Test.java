package designPatterns.proxy;

import lombok.Data;

@Data
public class Test implements TestInterface {
    private String test;

    @Override
    public void setTest(Test test) {
        this.test = test.getTest();
    }
}
