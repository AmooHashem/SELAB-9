package parser.action;

import parser.ParseTable;
import parser.Rule;
import scanner.lexicalAnalyzer;
import scanner.token.Token;

import java.util.ArrayList;
import java.util.Stack;

public class ShiftAction extends Action {
    public ShiftAction(int number) {
        super(number);
    }

    @Override
    public boolean parse(Stack<Integer> parsStack, Token lookAhead, lexicalAnalyzer lexicalAnalyzer, ArrayList<Rule> rules, ParseTable parseTable) {
        parsStack.push(number);
        Token nextToken = lexicalAnalyzer.getNextToken();
        lookAhead.setType(nextToken.getType());
        lookAhead.setValue(nextToken.getValue());
        return false;
    }

    @Override
    public String toString() {
        return "s" + number;
    }
}
