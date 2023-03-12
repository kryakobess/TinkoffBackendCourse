package Parsers;

import java.util.Stack;

public enum HandlerOrder {
    GitHub{
        @Override
        public AbstractHandler makeHandler(AbstractHandler next){
            return new GithubHandler(next);
        }
    },
    StackOverflow{
        @Override
        public AbstractHandler makeHandler(AbstractHandler next){
            return new StackOverflowHandler(next);
        }
    };

    public abstract AbstractHandler makeHandler(AbstractHandler next);
    public static AbstractHandler buildChain(){
        Stack<AbstractHandler> stack = new Stack<>();
        for (int i = values().length-1; i >= 0; --i){
            var type =  HandlerOrder.values()[i];
            stack.push(type.makeHandler(stack.isEmpty() ? null : stack.pop()));
        }
        return stack.pop();
    }
}
