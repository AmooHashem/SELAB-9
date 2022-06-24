package parser.action;

import parser.ParseTable;
import parser.Rule;
import scanner.lexicalAnalyzer;
import scanner.token.Token;

import java.util.ArrayList;
import java.util.Stack;

public class AcceptAction extends Action {
    public AcceptAction(int number) {
        super(number);
    }

    @Override
    public boolean parse(Stack<Integer> parsStack, Token lookAhead, lexicalAnalyzer lexicalAnalyzer, ArrayList<Rule> rules, ParseTable parseTable) {
        return true;
    }

    @Override
    public String toString() {
        return "acc";
    }
}
