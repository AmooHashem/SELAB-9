package parser.action;

import parser.ParseTable;
import parser.Rule;
import scanner.lexicalAnalyzer;
import scanner.token.Token;

import java.util.ArrayList;
import java.util.Stack;

public abstract class Action {
    public int number;

    public Action(int number) {
        this.number = number;
    }

    public abstract boolean parse(Stack<Integer> parsStack, Token lookAhead, lexicalAnalyzer lexicalAnalyzer, ArrayList<Rule> rules, ParseTable parseTable);

    public abstract String toString();
}
