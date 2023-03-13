import Parsers.AbstractHandler;
import Parsers.GithubHandler;
import Parsers.StackOverflowHandler;

import java.util.Stack;

public class ChainBuilder {

    private static final AbstractHandler[] handlerOrder = {
            new GithubHandler(),
            new StackOverflowHandler()
    };

    public static AbstractHandler buildChain(){
        Stack<AbstractHandler> stack = new Stack<>();
        for (int i = handlerOrder.length-1; i >= 0; --i){
            handlerOrder[i].setNextHandler(stack.isEmpty() ? null : stack.pop());
            stack.push(handlerOrder[i]);
        }
        return stack.pop();
    }

}
