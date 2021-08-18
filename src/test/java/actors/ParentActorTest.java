package actors;

import akka.actor.testkit.typed.javadsl.BehaviorTestKit;
import org.junit.jupiter.api.Test;
import samplePackage.actors.routing.Parent;

public class ParentActorTest {

    @Test
    void testParentActor() {
        BehaviorTestKit<Object> testKit = BehaviorTestKit.create(Parent.getParent());
        String message = "invoke";
        testKit.run(message);
    }

//    @Test
//    void testPassingOfMessages() {
//        BehaviorTestKit<String> parent = BehaviorTestKit.create(Parent.create(), "parent");
//        parent.run("greet");
//        TestInbox<String> childActor = parent.childInbox("my-child");
//        assertNotNull(childActor);
//        childActor.expectMessage("hi");
//        TestInbox<String> parentInbox = parent.selfInbox();
//        parentInbox.expectMessage("bye"); //Fails
//    }
}
