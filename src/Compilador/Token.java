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
public class Token {
    
    
    public byte kind;
    public String spelling;
    
    public Token(byte kind, String spelling) {
        
        this.kind = kind;
        this.spelling = spelling;     
        
        if(kind == IDENTIFIER)
            verificaPalavraReservada(this.spelling);        
    }
    
    private void verificaPalavraReservada(String spelling){
        
        switch(spelling){
            case "or":
                this.kind = OP_AD;
                break;
            case "and":
                this.kind = OP_MUL;
                break;
            case "true":case"false":
                this.kind = BOOL_LITERAL;
                break;
            default:
                for(byte k = 0 ; k < spellings.length; k++){
                    if (spelling.equals(spellings[k]))
                        this.kind = k;
                }break;
                
        }
        
        
    }
    
    public static final byte IDENTIFIER = 0, OP_AD = 1, OP_MUL = 2, OP_REL = 3, INT_LITERAL = 4, 
            FLOAT_LITERAL = 5, BOOL_LITERAL = 6, BEGIN = 7, END = 8, IF = 9, THEN = 10, ELSE = 11, 
            FUNCTION = 12, OPEN_PARENTESES = 13, CLOSE_PARENTESES = 14, VERTICAL_TWO_DOTS = 15, 
            SEMI_COLON = 16, PROCEDURE = 17, VAR = 18, WHILE = 19, DO = 20,COMMA = 21,OPEN_COCHETE = 22, 
            CLOSE_COCHETE = 23, ARRAY = 24, HORIZONTAL_TWO_DOTS = 25, OF = 26, INTEGER = 27, REAL = 28, 
            BOOLEAN = 29, ATTRIBUTION = 30, EOT = 31;  
    
   /*public apenas para imprimir, deveria ser private*/
    private final static String[] spellings = { "<id>", "<op_ad>", "<op_mul>", "<op_rel>", "<int_literal>", 
        "<float_literal>", "<bool_literal>", "begin", "end", "if", "then", "else", "function", "(", ")", ":",
        ";", "procedure", "var", "while", "do", ",", "[", "]", "array", "..", "of", "int", "float", "boolean",
        ":=", "<eot>"};
    
    public static String getSpellings(int index){
        if (( index < spellings.length ) && ( index >= 0 ))
            return (spellings[index]);
        return null;
    }
    
}
