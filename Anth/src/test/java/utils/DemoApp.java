package demo;

import org.junit.jupiter.api.Test;

public class DemoApp {
    @Test
    public void hashcodeDemo() {
        String tLength = "7b955246-64aa-4330-a4e1-56c7faea4a7b".hashCode() + "";
        System.out.println("tLength = " + tLength);
    }

    @Test
    public void enterDemo() {
//     \u000d   System.out.println("enterDemo");
    }

    @Test
    public void intCandyDemo() {
        int x=123_45;
        System.out.println("x = " + x);
    }

    @Test
    public void strStartWith() {
        System.out.println("UTL1_XXX".startsWith("UTL1",0));
    }
}
