/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compilador;

/**
 *
 * @author daluc
 */
public class Scanner {
    
    private char[] codigoFonte;
    public int posicaoDeLeitura = 0;
    
    public char currentChar;    
    private byte currentKind;
    private StringBuffer currentSpelling;
    
    
    
    public Scanner(char[] codigoFonte){
        this.codigoFonte = codigoFonte;
        currentChar = codigoFonte[0];         
    }
    
    private void take(char expectedChar){
        if(currentChar == expectedChar){
            posicaoDeLeitura++;
            currentChar = codigoFonte[posicaoDeLeitura];
        }
        else {
            //Report a lexical error
        }
    }
    
    private void takeIt(){
        currentSpelling.append(currentChar);
        posicaoDeLeitura++;
        currentChar = codigoFonte[posicaoDeLeitura];        
    }
    
    /*Da forma como estava no livro nao fazia sentido! Elementos 
    separadores eram adicionados ao buffer de spelling sendo que 
    este seria atribuido ao spelling do token no final.
    
    Outro ponto e o codigo da forma como esta no livro nao compila,
    pois o metodo scanSeparator chama o takeIt() e take() que por 
    sua vez tentam adicionar algo na variavel currentSpelling, mas 
    essa variavel somente e inicializada apos o fim da invocacao 
    de scanSeparator, LOL*/
    
    private void takeItSeparator(){
            posicaoDeLeitura++;
            currentChar = codigoFonte[posicaoDeLeitura];
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
            case 'j':case 'k':case 'l':case 'm':case 'n':case 'p':case 'q':case 'r':case 's':
            case 't':case 'u':case 'v':case 'x':case 'y':case 'z':
                return true;
            default:
                return false;            
        }
    }
    
    private boolean isGraphic(char currentChar){
        switch(currentChar){
            case 'a':case 'b':case 'c':case 'd':case 'e':case 'f':case 'g':case 'h':case 'i':
            case 'j':case 'k':case 'l':case 'm':case 'n':case 'p':case 'q':case 'r':case 's':
            case 't':case 'u':case 'v':case 'x':case 'y':case 'z':case '0':case '1':case '2':
            case '3':case '4':case '5':case '6':case '7':case '8':case '9':case '+':case '-':
            case '*':case '/':case '=':case '.':case '(':case ')':case '[':case ']':case '<':
            case '>':case '!':case '@':case '$':case '%':case '{':case '}':case ' ': case '\\':
                return true;
            default:
                return false;
        }
    }
    
    private byte scanToken(){
        
        switch(currentChar){
        
            case 'a':case 'b':case 'c':case 'd':case 'e':case 'f':case 'g':case 'h':case 'i':
            case 'j':case 'k':case 'l':case 'm':case 'n':case 'p':case 'q':case 'r':case 's':
            case 't':case 'u':case 'v':case 'x':case 'y':case 'z':
                takeIt();
                while(isLetter(currentChar) || isDigit(currentChar))
                    takeIt();
                return Token.ID;
            
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
                while(isDigit(currentChar))
                    takeIt();
                return Token.FLOAT_LITERAL;
                
            case '+':case '-':case '*':case '/':case '=':case '\\':
                takeIt();
                return Token.OPERATOR;
            
            case '<':
                takeIt();
                switch(currentChar){
                    case '=':
                        takeIt();
                        return Token.OPERATOR;
                    case '>':
                        takeIt();
                        return Token.OPERATOR;
                    default:
                        return Token.OPERATOR;
                }   
           
            case '>':
                takeIt();
                switch(currentChar){
                    case '=':
                        takeIt();
                        return Token.OPERATOR;
                    case '<':
                        takeIt();
                        return Token.OPERATOR;
                    default:
                        return Token.OPERATOR;
                }          
               
            case ';':
                takeIt();
                return Token.SEMI_COLON;
            
            case ':':
                takeIt();
                if(currentChar == '='){
                    takeIt();
                    return Token.ATTRIBUITION;
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
                takeIt();
                return Token.END;
            
            default:
                //report a lexical error
        }
        return 0; 
    }
    
    private void scanSeparator(){
        switch(currentChar){
            case '!':
                takeItSeparator();
                while(isGraphic(currentChar))
                    takeItSeparator();
                take('\n');
                break;
            case ' ':case '\n':
                takeItSeparator();
                break;     
        }
    }
    
    public Token scan(){      
        while(currentChar == '!' || currentChar == ' ' || currentChar == '\n'){
            scanSeparator();}

        currentSpelling = new StringBuffer("");
        currentKind = scanToken();     
        
        return( new Token(currentKind,currentSpelling.toString()));
    }
}
