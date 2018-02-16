/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.front_end;

import java.util.ArrayList;

/**
 *
 * @author daluc
 */
public class Scanner {
    
    private ArrayList<Character> codigoFonte;
    private int posicaoDeLeitura = 0;
    
    /*Resgitra posição para poder exibir localização de erros lexico/sintaticos*/
    private int posicaoNaLinha = 0;
    private int numeroDeLinha = 0;
    
    public char currentChar;    
    private byte currentKind;
    private StringBuffer currentSpelling;
    
    
    
    public Scanner(ArrayList<Character> codigoFonte){
        this.codigoFonte = codigoFonte;
        /*while(!codigoFonte.isEmpty()){
            System.out.println(codigoFonte.remove(0));
        }*/
        this.currentChar = codigoFonte.get(0);
    }
    
    private void take(char expectedChar){
        if(currentChar == expectedChar){
            posicaoDeLeitura++;
            posicaoNaLinha++;
            currentChar = codigoFonte.get(posicaoDeLeitura);
        }
        else {
            //Report a lexical error
        }
    }
    
    private void takeIt(){
        currentSpelling.append(currentChar);
        posicaoDeLeitura++;
        posicaoNaLinha++;
        currentChar = codigoFonte.get(posicaoDeLeitura); 
    }

    private void takeItSeparator(){
            posicaoDeLeitura++;
            posicaoNaLinha++;
            currentChar = codigoFonte.get(posicaoDeLeitura);
                
    /*Da forma como estava no livro nao fazia sentido! Elementos 
    separadores eram adicionados ao buffer de spelling sendo que 
    este seria atribuido ao spelling do token no final.
    
    Outro ponto e o codigo da forma como esta no livro nao compila,
    pois o metodo scanSeparator chama o takeIt() e take() que por 
    sua vez tentam adicionar algo na variavel currentSpelling, mas 
    essa variavel somente e inicializada apos o fim da invocacao 
    de scanSeparator, LOL*/
    }

    private boolean isDigit(char currentChar){
        switch(currentChar){
            case '0':case '1':case '2':case '3':case '4':case '5':case '6':case '7':case '8':case '9':
                return true;
            default:
                return false;                
        }
    }
    
    private boolean isLetter(char currentChar){
        switch(currentChar){
            case 'a':case 'b':case 'c':case 'd':case 'e':case 'f':case 'g':case 'h':case 'i':
            case 'j':case 'k':case 'l':case 'm':case 'n':case 'o':case 'p':case 'q':case 'r':
            case 's':case 't':case 'u':case 'v':case 'x':case 'y':case 'z':
                
            case 'A':case 'B':case 'C':case 'D':case 'E':case 'F':case 'G':case 'H':case 'I':
            case 'J':case 'K':case 'L':case 'M':case 'N':case 'O':case 'P':case 'Q':case 'R':
            case 'S':case 'T':case 'U':case 'V':case 'X':case 'Y':case 'Z':    
                
                return true;
            default:
                return false;            
        }
    }
    
    private boolean isGraphic(char currentChar){
      
        return ((int)currentChar >= 32) && ( ((int)currentChar) <= 126);
            /*32 a 126 sao os printable characters da ASCII*/
    
    }
    
    private byte scanToken(){
        
        switch(currentChar){
        
            case 'a':case 'b':case 'c':case 'd':case 'e':case 'f':case 'g':case 'h':case 'i':
            case 'j':case 'k':case 'l':case 'm':case 'n':case 'o':case 'p':case 'q':case 'r':
            case 's':case 't':case 'u':case 'v':case 'x':case 'y':case 'z':
                
            case 'A':case 'B':case 'C':case 'D':case 'E':case 'F':case 'G':case 'H':case 'I':
            case 'J':case 'K':case 'L':case 'M':case 'N':case 'O':case 'P':case 'Q':case 'R':
            case 'S':case 'T':case 'U':case 'V':case 'X':case 'Y':case 'Z': 
                
                takeIt();
                while(isLetter(currentChar) || isDigit(currentChar))
                    takeIt();
                return Token.IDENTIFIER;
            
            case '0':case '1':case '2':case '3':case '4':case '5':case '6':case '7':case '8':
            case '9':
                takeIt();
                while(isDigit(currentChar))
                    takeIt();
                if(currentChar == '.'){
                    takeIt();
                    while(isDigit(currentChar))
                        takeIt();
                    return Token.FLOAT_LITERAL; 
                }
                return Token.INT_LITERAL;
            
            case '.':
                takeIt();
                if(currentChar == '.'){
                    takeIt();
                    return Token.HORIZONTAL_TWO_DOTS;
                }
                if(isDigit(currentChar)){
                    takeIt();
                    while(isDigit(currentChar))
                        takeIt();
                    return Token.FLOAT_LITERAL;
                }
                return Token.SINGLE_DOT;
                
            case '+':case '-':
                takeIt();
                return Token.OP_AD;
            
            case '*':case '/':
                takeIt();
                return Token.OP_MUL;
                
            case '=':
                takeIt();
                return Token.OP_REL;
                
            case '<':
                takeIt();
                switch(currentChar){
                    case '=':
                        takeIt();
                        return Token.OP_REL;
                    case '>':
                        takeIt();
                        return Token.OP_REL;
                    default:
                        return Token.OP_REL;
                }   
           
            case '>':
                takeIt();
                switch(currentChar){
                    case '=':
                        takeIt();
                        return Token.OP_REL;
                    default:
                        return Token.OP_REL;
                }          
               
            case ';':
                takeIt();
                return Token.SEMI_COLON;
            
            case ':':
                takeIt();
                if(currentChar == '='){
                    takeIt();
                    return Token.ATTRIBUTION;
                }
                return Token.VERTICAL_TWO_DOTS;
            
            case '(':
                takeIt();
                return Token.OPEN_PARENTESES;
            
            case ')':
                takeIt();
                return Token.CLOSE_PARENTESES;
                
            case '[':
                takeIt();
                return Token.OPEN_COCHETE;
            
            case ']':
                takeIt();
                return Token.CLOSE_COCHETE;
                
            case '\000':     
                return Token.EOT;
                
            case ',':
                takeIt();
                System.out.println("VÍRGULA");
                return Token.COMMA;
            
            default:
                 System.out.print("Numero da linha = " + (numeroDeLinha + 1) +"\t");
                 System.out.println("Posicao na linha = " + posicaoNaLinha);
                 takeIt();
                 return Token.OUTROS;
        }
    }
    
    private void scanSeparator(){
        
        switch(currentChar){
            case '$':
                takeItSeparator();
                while(isGraphic(currentChar))
                    takeItSeparator();
                take('\n');
                posicaoNaLinha = 0;
                numeroDeLinha++;
                break;
            case ' ':case '\r':
                takeItSeparator();
                break;
            case '\n':
                takeItSeparator();
                posicaoNaLinha = 0;
                numeroDeLinha++;
                break;
        }
    }
    
    public Token scan(){ 
        while(currentChar == '$' || currentChar == ' ' || currentChar == '\n' || currentChar == '\r'){
            scanSeparator();}
        
        /* No livro tem apenas \n, mas adicionei \r pois alguns editores de texto no Windows 
        usam \r\n para quebrar a liha ao passo que nos sistemas Unix a maioria usa so \n*/
                
        currentSpelling = new StringBuffer("");
        currentKind = scanToken();  
        
        return( new Token(currentKind,currentSpelling.toString()));
    }
}


/*
    07/02/2018 DLucas

Falta:
        -> tratar o que acontece na orcorrencia de erro lexico no take()
*/