package parser.action;

import Log.Log;
import facade.CodeGeneratorFacade;
import parser.ParseTable;
import parser.Rule;
import scanner.lexicalAnalyzer;
import scanner.token.Token;

import java.util.ArrayList;
import java.util.Stack;

public class ReduceAction extends Action {
    public ReduceAction(int number) {
        super(number);
    }

    @Override
    public boolean parse(Stack<Integer> parsStack, Token lookAhead, lexicalAnalyzer lexicalAnalyzer, ArrayList<Rule> rules, ParseTable parseTable) {
        Rule rule = rules.get(number);
        for (int i = 0; i < rule.RHS.size(); i++) {
            parsStack.pop();
        }
        Log.print(/*"state : " +*/ parsStack.peek() + "\t" + rule.LHS);
        parsStack.push(parseTable.getGotoTable(parsStack.peek(), rule.LHS));
        Log.print(/*"new State : " + */parsStack.peek() + "");
        CodeGeneratorFacade.getInstance().calcSemantic(rule.semanticAction, lookAhead);
        return false;
    }

    @Override
    public String toString() {
        return "r" + number;
    }
}
