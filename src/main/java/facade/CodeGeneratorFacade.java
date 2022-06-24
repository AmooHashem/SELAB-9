package facade;

import Log.Log;
import codeGenerator.CodeGenerator;
import scanner.token.Token;

public class CodeGeneratorFacade {
    private final CodeGenerator cg = new CodeGenerator();
    private static CodeGeneratorFacade instance = null;

    private CodeGeneratorFacade() {
    }

    public static CodeGeneratorFacade getInstance() {
        if (instance == null) {
            instance = new CodeGeneratorFacade();
        }
        return instance;
    }

    public void calcSemantic(int semanticAction, Token lookAhead) {
        try {
            cg.semanticFunction(semanticAction, lookAhead);
        } catch (Exception e) {
            Log.print("Code Generator Error");
        }
    }

    public void printMemory() {
        cg.printMemory();
    }
}
