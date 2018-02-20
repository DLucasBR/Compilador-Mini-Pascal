package compiler.front_end;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.*;

public class Parser {

    /**************************************************************************/
    private Token currentToken;
    private ArrayList<Character> codigoFonte;
    private Scanner scanner;
    public String log = "";
    /**************************************************************************/
    
    public Parser(String diretorioArquivo) {

        codigoFonte = new ArrayList<>();

        try {
            BufferedReader leitorDeCaracteres = new BufferedReader(new FileReader(diretorioArquivo));
            int aux;

            while ((aux = leitorDeCaracteres.read()) != -1) {
                codigoFonte.add((char) aux);
            }

            codigoFonte.add((char) '\000');
            leitorDeCaracteres.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TelaAnaliseLexica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TelaAnaliseLexica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void accept(byte expectedKind) {

        log += "Token esperado: " 
               + Token.getSpellings(expectedKind)
               + "\tToken atual: "
               + Token.getSpellings(currentToken.kind);
        
        if (currentToken.kind == expectedKind) {
            currentToken = scanner.scan();
            log += "\tToken Aceito!\n\n";
        } else {
            currentToken = scanner.scan();
            log += "\tToken rejeitado!\n\n";
        }
    }

    private void acceptIt() {
        log += "Token: " 
               + Token.getSpellings(currentToken.kind)
               + "\tAceito!";

        currentToken = scanner.scan();
    }

    private void parseProgram() {
        
        log += "\t\tIniciando Analise..."
                + "\n\n\n\tANALISE SINTATICA -> PROGRAM\n"; 

        accept(Token.PROGRAM);
        accept(Token.IDENTIFIER);
        accept(Token.SEMI_COLON);

        parseCorpo();

        accept(Token.SINGLE_DOT);
    }

    private void parseCorpo() {
        log += "\n\n\n\tANALISE SINTATICA -> CORPO\n";
        parseDeclaracoes();
        parseComandoComposto();
    }

    private void parseDeclaracoes() {
        log += "\n\n\n\tANALISE SINTATICA -> DECLARACOES\n";
        
        while ((currentToken.kind == Token.VAR) || (currentToken.kind == Token.FUNCTION) || (currentToken.kind == Token.PROCEDURE)) {
            parseDeclaracao();
            accept(Token.SEMI_COLON);
        }
    }

    private void parseDeclaracao() {
        log += "\n\n\n\tANALISE SINTATICA -> DECLARACAO\n";
       
        switch (currentToken.kind) {
            case Token.VAR:

                acceptIt();
                accept(Token.IDENTIFIER);

                while (currentToken.kind == Token.COMMA) {
                    acceptIt();
                    accept(Token.IDENTIFIER);
                }

                accept(Token.VERTICAL_TWO_DOTS);
                parseTipo();
                break;

            case Token.FUNCTION:

                acceptIt();
                accept(Token.IDENTIFIER);
                accept(Token.OPEN_PARENTESES);

                if ((currentToken.kind == Token.VAR) || (currentToken.kind == Token.IDENTIFIER)) {
                    parseParametros();

                    while (currentToken.kind == Token.SEMI_COLON) {
                        acceptIt();
                        parseParametros();
                    }
                }

                accept(Token.CLOSE_PARENTESES);
                accept(Token.VERTICAL_TWO_DOTS);

                if ((currentToken.kind == Token.INTEGER) || (currentToken.kind == Token.REAL) || (currentToken.kind == Token.BOOLEAN)) {
                    acceptIt();
                } else {
                    currentToken = scanner.scan();
                }

                accept(Token.SEMI_COLON);
                parseCorpo();
                break;

            case Token.PROCEDURE:
                acceptIt();
                accept(Token.IDENTIFIER);
                accept(Token.OPEN_PARENTESES);

                if ((currentToken.kind == Token.VAR) || (currentToken.kind == Token.IDENTIFIER)) {
                    parseParametros();

                    while (currentToken.kind == Token.SEMI_COLON) {
                        acceptIt();
                        parseParametros();
                    }
                }

                accept(Token.CLOSE_PARENTESES);
                accept(Token.SEMI_COLON);
                parseCorpo();
                break;

            default:
                System.out.println("Erro: Missing starting declaration");
        }
    }

    private void parseComandoComposto() {
        log += "\n\n\n\tANALISE SINTATICA -> COMANDO_COMPOSTO\n";

        accept(Token.BEGIN);

        while ((currentToken.kind == Token.IF) || (currentToken.kind == Token.WHILE) || (currentToken.kind == Token.BEGIN) || (currentToken.kind == Token.IDENTIFIER)) {
            parseComando();
            accept(Token.SEMI_COLON);
        }

        accept(Token.END);
    }

    private void parseComando() {
        log += "\n\n\n\tANALISE SINTATICA -> COMANDO\n";
        
        switch (currentToken.kind) {
            case Token.WHILE:
                acceptIt();
                parseExpressao();
                accept(Token.DO);
                parseComando();
                break;

            case Token.IF:
                acceptIt();
                parseExpressao();
                accept(Token.THEN);
                parseComando();
                if (currentToken.kind == Token.ELSE) {
                    acceptIt();
                    parseComando();
                }
                break;

            case Token.IDENTIFIER:
                acceptIt();

                if (currentToken.kind == Token.OPEN_PARENTESES) {
                    parseChamProcedimento();
                } else {
                    while (currentToken.kind == Token.OPEN_COCHETE) {
                        acceptIt();
                        parseExpressao();
                        accept(Token.CLOSE_COCHETE);
                    }
                    accept(Token.ATTRIBUTION);
                    parseExpressao();
                }
                break;

            case Token.BEGIN:
                parseComandoComposto();
                break;
            default:
                System.out.println("Erro: missing start command symbol ");
        }
    }

    private void parseChamProcedimento() {
        log += "\n\n\n\tANALISE SINTATICA -> CHAMADA_DE_PROCEDIMENTO\n";

        accept(Token.OPEN_PARENTESES);

        if (currentToken.kind != Token.CLOSE_PARENTESES) {
            parseExpressao();

            while (currentToken.kind == Token.COMMA) {
                acceptIt();
                parseExpressao();
            }
        }
        accept(Token.CLOSE_PARENTESES);
    }

    private void parseExpressao() {
        log += "\n\n\n\tANALISE SINTATICA -> EXPRESSAO\n";
        
        parseTermo();

        while (currentToken.kind == Token.OP_AD) {
            acceptIt();
            parseTermo();
        }

        if (currentToken.kind == Token.OP_REL) {
            acceptIt();
            parseTermo();
            while (currentToken.kind == Token.OP_AD) {
                acceptIt();
                parseTermo();
            }
        }
    }

    private void parseTermo() {
        log += "\n\n\n\tANALISE SINTATICA -> TERMO\n";

        parseFator();

        while (currentToken.kind == Token.OP_MUL) {
            acceptIt();
            parseFator();
        }
    }

    private void parseFator() {
        log += "\n\n\n\tANALISE SINTATICA -> = FATOR\n";

        switch (currentToken.kind) {
            case Token.INT_LITERAL:case Token.FLOAT_LITERAL:case Token.BOOL_LITERAL:
                acceptIt();
                break;

            case Token.IDENTIFIER:
                acceptIt();
                if (currentToken.kind == Token.OPEN_PARENTESES) {
                    acceptIt();
                    if (currentToken.kind != Token.CLOSE_PARENTESES) {
                        parseExpressao();
                        while (currentToken.kind == Token.COMMA) {
                            acceptIt();
                            parseExpressao();
                        }
                    }
                    accept(Token.CLOSE_PARENTESES);
                } else {
                    while (currentToken.kind == Token.OPEN_COCHETE) {
                        acceptIt();
                        parseExpressao();
                        accept(Token.CLOSE_COCHETE);
                    }
                }
                break;

            case Token.OPEN_PARENTESES:

                acceptIt();
                parseExpressao();
                accept(Token.CLOSE_PARENTESES);
                break;

            default:
                System.out.println("Erro: fator inválido");
        }
    }

    private void parseParametros() {
        log += "\n\n\n\tANALISE SINTATICA -> PARAMETROS\n";

        switch (currentToken.kind) {
            case Token.VAR:
                acceptIt();
                accept(Token.IDENTIFIER);
                while (currentToken.kind == Token.COMMA) {
                    acceptIt();
                    accept(Token.IDENTIFIER);
                }
                break;

            case Token.IDENTIFIER:
                acceptIt();
                while (currentToken.kind == Token.COMMA) {
                    acceptIt();
                    accept(Token.IDENTIFIER);
                }
                break;

            default:
                System.out.println("Erro");
        }

        accept(Token.VERTICAL_TWO_DOTS);

        if ((currentToken.kind == Token.INTEGER) || (currentToken.kind == Token.REAL) || (currentToken.kind == Token.BOOLEAN)) {
            acceptIt();
        } else {
            System.out.println("Erro");
        }
    }

    private void parseTipo() {
        log += "\n\n\n\tANALISE SINTATICA -> TIPO\n";

        switch (currentToken.kind) {
            case Token.VAR:
                acceptIt();
                accept(Token.OPEN_COCHETE);
                switch (currentToken.kind) {
                    case Token.INT_LITERAL:
                        acceptIt();
                        break;
                    case Token.FLOAT_LITERAL:
                        acceptIt();
                        break;
                    case Token.BOOL_LITERAL:
                        acceptIt();
                        break;
                    default:
                        System.out.println("Erro");
                }

                accept(Token.HORIZONTAL_TWO_DOTS);

                switch (currentToken.kind) {
                    case Token.INT_LITERAL:
                        acceptIt();
                        break;
                    case Token.FLOAT_LITERAL:
                        acceptIt();
                        break;
                    case Token.BOOL_LITERAL:
                        acceptIt();
                        break;
                    default:
                        System.out.println("Erro");
                }

                accept(Token.OF);
                parseTipo();
                break;

            case Token.INTEGER:case Token.BOOLEAN:case Token.REAL:
                acceptIt();
                break;

            default:
                System.out.println("Erro");
        }
    }

    public String parse() {

        scanner = new Scanner(codigoFonte);
        currentToken = scanner.scan();

        parseProgram();

        System.out.println("Cabou");
        if (currentToken.kind != Token.EOT) {
            System.out.println("Erro");
        }
        return log;
    }
}
