package parser;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;

import Log.Log;
import facade.CodeGeneratorFacade;
import errorHandler.ErrorHandler;
import parser.action.AcceptAction;
import parser.action.Action;
import parser.action.ReduceAction;
import parser.action.ShiftAction;
import scanner.lexicalAnalyzer;
import scanner.token.Token;


public class Parser {
    private ArrayList<Rule> rules;
    private Stack<Integer> parsStack;
    private ParseTable parseTable;
    private lexicalAnalyzer lexicalAnalyzer;

    public Parser() {
        parsStack = new Stack<Integer>();
        parsStack.push(0);
        try {
            parseTable = new ParseTable(Files.readAllLines(Paths.get("src/main/resources/parseTable")).get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        rules = new ArrayList<Rule>();
        try {
            for (String stringRule : Files.readAllLines(Paths.get("src/main/resources/Rules"))) {
                rules.add(new Rule(stringRule));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startParse(java.util.Scanner sc) {
        lexicalAnalyzer = new lexicalAnalyzer(sc);
        Token lookAhead = lexicalAnalyzer.getNextToken();
        boolean finish = false;
        while (!finish) {
            Action currentAction = parseTable.getActionTable(parsStack.peek(), lookAhead);
            finish = currentAction.parse(parsStack, lookAhead, lexicalAnalyzer, rules, parseTable);
        }
        if (!ErrorHandler.hasError) {
            CodeGeneratorFacade.getInstance().printMemory();
        }
    }

}
