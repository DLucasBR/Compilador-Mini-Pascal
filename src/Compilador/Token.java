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
        
        if(kind == ID){
            for(byte k = 0 ; k < spellings.length; k++){
                if (spelling.equals(spellings[k]))
                    this.kind = k;
            }
        }
    }
    
    public static final byte ID = 0, OPERATOR = 1, INT_LITERAL = 2, FLOAT_LITERAL = 3, BOOL_LITERAL = 4, 
            BEGIN = 5, END = 6, IF = 7, THEN = 8, ELSE = 9, FUNCTION = 10, OPEN_PARENTESES = 11,
            CLOSE_PARENTESES = 12, VERTICAL_TWO_DOTS = 13, SEMI_COLON = 14, PROCEDURE = 15, VAR = 16, 
            WHILE = 17, DO = 18,COMMA = 19, OPEN_COCHETE = 20, CLOSE_COCHETE = 21, ARRAY = 22, 
            HORIZONTAL_TWO_DOTS = 23, OF = 24, INTEGER = 25, REAL = 26, BOOLEAN = 27, ATTRIBUITION = 28;  
    
   /*public apenas para imprimir, deveria ser private*/
    public final static String[] spellings = { "<id>", "<operator>", "<int_literal>", "<float_literal>","<bool_literal>",
        "begin","end","if","then","else","function","(",")",":",";","procedure","var","while","do",",","[","]",
        "array","..","of","int","float","boolean","atribuition"};
}
