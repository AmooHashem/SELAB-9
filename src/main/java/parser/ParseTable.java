package parser;

import parser.action.AcceptAction;
import parser.action.Action;
import parser.action.ReduceAction;
import parser.action.ShiftAction;
import scanner.token.Token;

import java.util.*;

/**
 * Created by mohammad hosein on 6/25/2015.
 */
public class ParseTable {
    private final ArrayList<Map<Token, Action>> actionTable = new ArrayList<>();
    private final ArrayList<Map<NonTerminal, Integer>> gotoTable = new ArrayList<>();

    public ParseTable(String jsonTable) throws Exception {
        jsonTable = jsonTable.substring(2, jsonTable.length() - 2);
        String[] rows = jsonTable.split("],\\[");
        Map<Integer, Token> terminals = new HashMap<>();
        Map<Integer, NonTerminal> nonTerminals = new HashMap<>();
        rows[0] = rows[0].substring(1, rows[0].length() - 1);
        calcTokens(rows[0].split("\",\""), terminals, nonTerminals);
        calcTables(rows, terminals, nonTerminals);
    }

    private void calcTokens(String[] cols, Map<Integer, Token> terminals, Map<Integer, NonTerminal> nonTerminals) {
        for (int i = 1; i < cols.length; i++) {
            if (cols[i].startsWith("Goto")) {
                String temp = cols[i].substring(5);
                try {
                    nonTerminals.put(i, NonTerminal.valueOf(temp));
                } catch (Exception ignored) {
                }
            } else {
                terminals.put(i, new Token(Token.getTypeFormString(cols[i]), cols[i]));
            }
        }
    }

    private void calcTables(String[] rows, Map<Integer, Token> terminals, Map<Integer, NonTerminal> nonTerminals) throws Exception {
        for (int i = 1; i < rows.length; i++) {
            rows[i] = rows[i].substring(1, rows[i].length() - 1);
            String[] cols = rows[i].split("\",\"");
            actionTable.add(new HashMap<Token, Action>());
            gotoTable.add(new HashMap<>());
            for (int j = 1; j < cols.length; j++) {
                if (!cols[j].equals("")) {
                    if (cols[j].equals("acc")) {
                        actionTable.get(actionTable.size() - 1).put(terminals.get(j), new AcceptAction(0));
                    } else if (terminals.containsKey(j)) {
                        Token t = terminals.get(j);
                        Action a = cols[j].charAt(0) == 'r' ? new ReduceAction(Integer.parseInt(cols[j].substring(1))) :
                                new ShiftAction(Integer.parseInt(cols[j].substring(1)));
                        actionTable.get(actionTable.size() - 1).put(t, a);
                    } else if (nonTerminals.containsKey(j)) {
                        gotoTable.get(gotoTable.size() - 1).put(nonTerminals.get(j), Integer.parseInt(cols[j]));
                    } else {
                        throw new Exception();
                    }
                }
            }
        }
    }

    public int getGotoTable(int currentState, NonTerminal variable) {
        return gotoTable.get(currentState).get(variable);
    }

    public Action getActionTable(int currentState, Token terminal) {
        return actionTable.get(currentState).get(terminal);
    }

}

