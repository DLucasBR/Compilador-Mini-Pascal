package compiler.front_end;

import java.util.ArrayList;

/*ola*/

public class Parser {
    
    private Token currentToken;
    private ArrayList<Character> codigoFonte;
    private Scanner scanner;
    
    public Parser(ArrayList<Character> codigoFonte){
    this.codigoFonte=codigoFonte;
    }
    
    
    private void accept (byte expectedKind){
        if(currentToken.kind==expectedKind){
            System.out.printf("\nToken aceito");
            System.out.println();
            currentToken=scanner.scan();}
        else {System.out.printf("\nToken rejeitado");
              System.out.println(" ");
              currentToken=scanner.scan();
             }
      }
    
    
    private void acceptIt(){
       currentToken=scanner.scan();
       System.out.printf("\nToken aceito");
       System.out.println(" ");
      }
    
    
    private void parseProgram()
    {
        
        System.out.printf("\n Em ParseProgram");
        
              
              System.out.printf("\ntoken corrente:%d",currentToken.kind);
              System.out.printf("\nToken esperado:%d", Token.PROGRAM);
              accept(Token.PROGRAM);
              
              System.out.printf("\ntoken corrente: %d",currentToken.kind);
              System.out.printf("\nToken esperado:%d", Token.IDENTIFIER );
              accept(Token.IDENTIFIER);
                
              System.out.printf("\ntoken corrente: %d",currentToken.kind);
              System.out.printf("\nToken esperado:%d", Token.SEMI_COLON);
              accept(Token.SEMI_COLON);
              
              
              parseCorpo();
              
              System.out.printf("\ntoken corrente: %d",currentToken.kind);
              System.out.printf("\nToken esperado:%d", Token.SINGLE_DOT);
              accept(Token.SINGLE_DOT);
              
    }
    
    
    private void parseCorpo(){
        System.out.printf("\n Em ParseCorpo");
        System.out.printf("\ntoken corrente: %d",currentToken.kind);
      parseDeclaracoes();
      parseComandoComposto();
    }
    
    
    private void parseDeclaracoes(){
        System.out.printf("\n Em ParseDeclaracoes");
        
    
    System.out.printf("\ntoken corrente: %d",currentToken.kind);
    System.out.printf("\nToken opcional esperado:%d ou %d ou %d", Token.VAR, Token.FUNCTION,Token.PROCEDURE );
    while((currentToken.kind==Token.VAR) || (currentToken.kind==Token.FUNCTION) || (currentToken.kind==Token.PROCEDURE)){
       parseDeclaracao();
       
       System.out.printf("\ntoken corrente: %d",currentToken.kind);
       System.out.printf("\nToken esperado:%d", Token.SEMI_COLON );
       accept(Token.SEMI_COLON);
       }
    }
    
    
    
    private void parseDeclaracao(){
        
        System.out.printf("\n Em ParseDeclaracao");
        System.out.printf("\ntoken corrente: %d",currentToken.kind);
     switch(currentToken.kind){
         case Token.VAR:
            
             System.out.printf("\ntoken corrente: %d",currentToken.kind);
             System.out.printf("\nToken esperado:%d", Token.VAR);
             acceptIt();
             
             System.out.printf("\ntoken corrente: %d",currentToken.kind);
             System.out.printf("\nToken esperado:%d", Token.IDENTIFIER);
             accept(Token.IDENTIFIER);
             
             System.out.printf("\ntoken corrente: %d",currentToken.kind);
             
             while(currentToken.kind == Token.COMMA ){
                 
             System.out.printf("\ntoken corrente: %d",currentToken.kind);
             System.out.printf("\nToken opcional esperado:%d", Token.COMMA );
             acceptIt();
             System.out.printf("\ntoken corrente: %d",currentToken.kind);
             System.out.printf("\nToken esperado:%d", Token.IDENTIFIER);
             accept(Token.IDENTIFIER);
             }
             
             System.out.printf("\ntoken corrente: %d",currentToken.kind);
             System.out.printf("\nToken esperado:%d", Token.VERTICAL_TWO_DOTS);
             accept(Token.VERTICAL_TWO_DOTS);
             
             parseTipo();
             
             break;
         
         case Token.FUNCTION:
         
           acceptIt();
           System.out.printf("\ntoken corrente: %d",currentToken.kind);
           System.out.printf("\nToken esperado:%d", Token.IDENTIFIER);
           accept(Token.IDENTIFIER);
           
           System.out.printf("\ntoken corrente: %d",currentToken.kind);
           System.out.printf("\nToken esperado:%d", Token.OPEN_PARENTESES);
           accept(Token.OPEN_PARENTESES);
           
           System.out.printf("\ntoken corrente: %d",currentToken.kind);
           System.out.printf("\nToken opcional esperado:%d ou %d", Token.VAR, Token.IDENTIFIER);
           if((currentToken.kind== Token.VAR) ||(currentToken.kind==Token.IDENTIFIER)){
             parseParametros();
             
             System.out.printf("\ntoken corrente: %d",currentToken.kind);
             
             while(currentToken.kind==Token.SEMI_COLON){
                 System.out.printf("\ntoken corrente: %d",currentToken.kind);
                 System.out.printf("\nToken opcional esperado:%d", Token.SEMI_COLON);
                acceptIt();
                parseParametros();
             }
            }
           
           System.out.printf("\ntoken corrente: %d",currentToken.kind);
           System.out.printf("\nToken esperado:%d", Token.CLOSE_PARENTESES);
           accept(Token.CLOSE_PARENTESES);
           
           System.out.printf("\ntoken corrente: %d",currentToken.kind);
           System.out.printf("\nToken esperado:%d", Token.VERTICAL_TWO_DOTS);
           accept(Token.VERTICAL_TWO_DOTS);
           
           
          System.out.printf("\ntoken corrente: %d",currentToken.kind);
          System.out.printf("\nToken esperado:%d %d %d", Token.INTEGER, Token.REAL, Token.BOOLEAN);
          if((currentToken.kind==Token.INTEGER) ||( currentToken.kind==Token.REAL) || (currentToken.kind==Token.BOOLEAN)){
           acceptIt();
          }else{System.out.printf("\nToken rejeitado");
                System.out.println("");
                currentToken=scanner.scan();
               }
          
          System.out.printf("\ntoken corrente: %d",currentToken.kind);
          System.out.printf("\nToken esperado:%d", Token.SEMI_COLON);
          accept(Token.SEMI_COLON);
          parseCorpo();
          break;
        
         
        case Token.PROCEDURE:
         acceptIt();
         
         System.out.printf("\ntoken corrente: %d",currentToken.kind);
         System.out.printf("\nToken esperado:%d", Token.IDENTIFIER);
         accept(Token.IDENTIFIER);
         
         System.out.printf("\ntoken corrente: %d",currentToken.kind);
         System.out.printf("\nToken esperado:%d", Token.OPEN_PARENTESES);
         accept(Token.OPEN_PARENTESES);
         
         System.out.printf("\ntoken corrente: %d",currentToken.kind);
         System.out.printf("\nToken opcional esperado:%d ou %d", Token.VAR, Token.IDENTIFIER);
         if((currentToken.kind==Token.VAR) ||(currentToken.kind==Token.IDENTIFIER)){
           parseParametros();
           
           System.out.printf("\ntoken corrente: %d",currentToken.kind);
           
           while(currentToken.kind==Token.SEMI_COLON){
             System.out.printf("\ntoken corrente: %d",currentToken.kind);
             System.out.printf("\nToken opcional esperado:%d", Token.SEMI_COLON);
             acceptIt();
             parseParametros();
            }
         
         }
         
         System.out.printf("\ntoken corrente: %d",currentToken.kind);
         System.out.printf("\nToken esperado:%d", Token.CLOSE_PARENTESES);
         accept(Token.CLOSE_PARENTESES);
         
         System.out.printf("\ntoken corrente: %d",currentToken.kind);
         System.out.printf("\nToken esperado:%d", Token.SEMI_COLON);
         accept(Token.SEMI_COLON);
         parseCorpo();
         break;
         
         default:
             System.out.println("Erro: Missing starting declaration");
     }
    }
    
    
    private void  parseComandoComposto(){
      System.out.printf("\n Em ParseComandoComposto");
        
     
      System.out.printf("\ntoken corrente: %d",currentToken.kind);
      System.out.printf("\nToken esperado:%d", Token.BEGIN);  
      accept(Token.BEGIN);
        
      System.out.printf("\ntoken corrente: %d",currentToken.kind);
      System.out.printf("\nToken opcional esperado:%d ou %d ou %d ou %d", Token.BEGIN, Token.IF, Token.WHILE, Token.IDENTIFIER);  
        while((currentToken.kind==Token.IF)|| (currentToken.kind==Token.WHILE) ||(currentToken.kind==Token.BEGIN) || (currentToken.kind==Token.IDENTIFIER)){
         parseComando();
         
         System.out.printf("\ntoken corrente: %d",currentToken.kind);
         System.out.printf("\nToken esperado:%d", Token.SEMI_COLON);  
         accept(Token.SEMI_COLON);
        }
      System.out.printf("\ntoken corrente: %d",currentToken.kind);
      System.out.printf("\nToken esperado:%d", Token.END);  
      accept(Token.END);
    } 
    
    
   private void parseComando(){
       
       System.out.printf("\n Em ParseComando");
       System.out.printf("\ntoken corrente: %d",currentToken.kind);
    switch(currentToken.kind){
        case Token.WHILE:
          System.out.printf("\ntoken corrente: %d",currentToken.kind);
          System.out.printf("\nToken esperado:%d", Token.WHILE);  
          acceptIt();
          
          parseExpressao();
          
          System.out.printf("\ntoken corrente: %d",currentToken.kind);
          System.out.printf("\nToken esperado:%d", Token.DO);
          accept(Token.DO);
          
          parseComando();
          break;
        
        case Token.IF:
          System.out.printf("\ntoken corrente: %d",currentToken.kind);
          System.out.printf("\nToken esperado:%d", Token.IF);  
          acceptIt();
          
          parseExpressao();
          
          System.out.printf("\ntoken corrente: %d",currentToken.kind);
          System.out.printf("\nToken esperado:%d", Token.THEN);  
          accept(Token.THEN);
          parseComando();
          
          System.out.printf("\ntoken corrente: %d",currentToken.kind);
          System.out.printf("\nToken opcional esperado:%d", Token.ELSE);
          if(currentToken.kind==Token.ELSE){
            acceptIt();
            parseComando();}
          break;
        
        
        case Token.IDENTIFIER:
            System.out.printf("\ntoken corrente: %d",currentToken.kind);
            System.out.printf("\nToken esperado:%d", Token.IDENTIFIER);  
            acceptIt();
            
            System.out.printf("\ntoken corrente: %d",currentToken.kind);
              
            if(currentToken.kind==Token.OPEN_PARENTESES)
                parseChamProcedimento();
            else {
                
                   System.out.printf("\ntoken corrente: %d",currentToken.kind);
                   
                   while(currentToken.kind==Token.OPEN_COCHETE)
                    {
                    System.out.printf("\ntoken corrente: %d",currentToken.kind);
                    System.out.printf("\nToken opcional esperado:%d", Token.OPEN_COCHETE ); 
                    acceptIt();
                    parseExpressao();
                    
                    System.out.printf("\ntoken corrente: %d",currentToken.kind);
                    System.out.printf("\nToken esperado:%d", Token.CLOSE_COCHETE);  
                    accept(Token.CLOSE_COCHETE);
                    }
                   
                    System.out.printf("\ntoken corrente: %d",currentToken.kind);
                    System.out.printf("\nToken esperado:%d", Token.ATTRIBUTION);  
                    accept(Token.ATTRIBUTION);
                    parseExpressao();
            }
            break;
            
            
        case Token.BEGIN:
             System.out.printf("\ntoken corrente: %d",currentToken.kind);
             System.out.printf("\nToken esperado:%d", Token.ATTRIBUTION); 
             parseComandoComposto();
             break;
        default:
           System.out.println("Erro: missing start command symbol ");
     }
    }
    
   
    
    private void parseChamProcedimento(){
        System.out.printf("\n Em ParseChamProcedimento");
        
        
      System.out.printf("\ntoken corrente: %d",currentToken.kind);
      System.out.printf("\nToken esperado:%d", Token.OPEN_PARENTESES); 
      accept(Token.OPEN_PARENTESES);
      
      
      if(currentToken.kind!=Token.CLOSE_PARENTESES){
             parseExpressao();
             
             System.out.printf("\ntoken corrente: %d",currentToken.kind);
             
             while(currentToken.kind==Token.COMMA){
              System.out.printf("\ntoken corrente: %d",currentToken.kind);
              System.out.printf("\nToken opcional esperado:%d", Token.COMMA);
              acceptIt();
              parseExpressao();
              }
      }
      
       System.out.printf("\ntoken corrente: %d",currentToken.kind);
       System.out.printf("\nToken esperado:%d", Token.CLOSE_PARENTESES); 
       accept(Token.CLOSE_PARENTESES);
     }
    
    
    
    private void parseExpressao(){
        System.out.printf("\n Em ParseEpressao");
        System.out.printf("\ntoken corrente: %d",currentToken.kind);
     
               parseTermo();
               
               System.out.printf("\ntoken corrente: %d",currentToken.kind);
               
               while(currentToken.kind==Token.OP_AD){
                   
                  System.out.printf("\ntoken corrente: %d",currentToken.kind);
                  System.out.printf("\nToken opcional esperado:%d", Token.OP_AD);
                  acceptIt();
                  parseTermo();
               }
      
      System.out.printf("\ntoken corrente: %d",currentToken.kind);
      System.out.printf("\nToken opcional esperado:%d", Token.OP_REL);
      if(currentToken.kind==Token.OP_REL){
             acceptIt();
             parseTermo();
             
             
             System.out.printf("\ntoken corrente: %d",currentToken.kind);
             while(currentToken.kind==Token.OP_AD){
             System.out.printf("\ntoken corrente: %d",currentToken.kind);
             System.out.printf("\nToken opcional esperado:%d", Token.OP_AD);
             acceptIt();
             parseTermo();
             }
         }
    }
    
    
    private void parseTermo(){
        
        System.out.printf("\n Em ParseTermo");
        System.out.printf("\ntoken corrente: %d",currentToken.kind);
        parseFator();
        
        System.out.printf("\ntoken corrente: %d",currentToken.kind);
        while(currentToken.kind==Token.OP_MUL){
            
           System.out.printf("\ntoken corrente: %d",currentToken.kind);
           System.out.printf("\nToken esperado:%d", Token.OP_MUL);
           acceptIt();
           parseFator();
        }
    
    }
    
    
    private void parseFator(){
        System.out.printf("\n Em ParseFator");
        System.out.printf("\ntoken corrente: %d",currentToken.kind);
      switch(currentToken.kind){
          case Token.INT_LITERAL: case Token.FLOAT_LITERAL: case Token.BOOL_LITERAL:
              
          System.out.printf("\ntoken corrente: %d",currentToken.kind);
          System.out.printf("\nToken esperado:%d ou %d ou %d", Token.INT_LITERAL,Token.FLOAT_LITERAL,Token.BOOL_LITERAL );
          acceptIt();
          break;
          
          case Token.IDENTIFIER:
              
              
             System.out.printf("\ntoken corrente: %d",currentToken.kind);
             System.out.printf("\nToken esperado:%d", Token.IDENTIFIER ); 
             acceptIt();
             
             System.out.printf("\ntoken corrente: %d",currentToken.kind);
             if(currentToken.kind==Token.OPEN_PARENTESES){
                 acceptIt();
                if(currentToken.kind!=Token.CLOSE_PARENTESES){
                    parseExpressao();
             
                    System.out.printf("\ntoken corrente: %d",currentToken.kind);
             
                    while(currentToken.kind==Token.COMMA){
                    System.out.printf("\ntoken corrente: %d",currentToken.kind);
                    System.out.printf("\nToken opcional esperado:%d", Token.COMMA);
                    acceptIt();
                    parseExpressao();
                      }
                   }
             System.out.printf("\ntoken corrente: %d",currentToken.kind);
             System.out.printf("\nToken esperado:%d", Token.CLOSE_PARENTESES ); 
             accept(Token.CLOSE_PARENTESES );
              }
              else {
                   System.out.printf("\ntoken corrente: %d",currentToken.kind);
                   while(currentToken.kind==Token.OPEN_COCHETE){
                   System.out.printf("\ntoken corrente: %d",currentToken.kind);
                   System.out.printf("\nToken esperado:%d", Token.OPEN_COCHETE ); 
                   acceptIt();
                   parseExpressao();
                   
                   System.out.printf("\ntoken corrente: %d",currentToken.kind);
                   System.out.printf("\nToken esperado:%d", Token.CLOSE_COCHETE ); 
                   accept(Token.CLOSE_COCHETE);
                   }
                  }
             
             
             
             
              break;
          
          
          case Token.OPEN_PARENTESES:
              
          System.out.printf("\ntoken corrente: %d",currentToken.kind);
          System.out.printf("\nToken esperado:%d", Token.OPEN_PARENTESES ); 
           acceptIt();
           parseExpressao();
           
           System.out.printf("\ntoken corrente: %d",currentToken.kind);
           System.out.printf("\nToken esperado:%d", Token.CLOSE_PARENTESES );
           accept(Token.CLOSE_PARENTESES);
           break;
          
          default: 
              System.out.println("Erro: fator inválido");
        }
    }
    
    
    
   private void parseParametros(){
    System.out.printf("\n Em ParseParametros");
    System.out.printf("\ntoken corrente: %d",currentToken.kind);
    switch(currentToken.kind){
        
        case Token.VAR:
            
          System.out.printf("\ntoken corrente: %d",currentToken.kind);
          System.out.printf("\nToken esperado:%d", Token.VAR );  
          acceptIt();
          
          System.out.printf("\ntoken corrente: %d",currentToken.kind);
          System.out.printf("\nToken esperado:%d", Token.IDENTIFIER );
          accept(Token.IDENTIFIER);
          
          System.out.printf("\ntoken corrente: %d",currentToken.kind);
          while(currentToken.kind==Token.COMMA){
            System.out.printf("\ntoken corrente: %d",currentToken.kind);
            acceptIt();
            
            System.out.printf("\ntoken corrente: %d",currentToken.kind);
            System.out.printf("\nToken esperado:%d", Token.IDENTIFIER );
            accept(Token.IDENTIFIER);
          }
          break;
          
        case Token.IDENTIFIER:
            
            System.out.printf("\ntoken corrente: %d",currentToken.kind);
            System.out.printf("\nToken esperado:%d", Token.IDENTIFIER );
            acceptIt();
            
            System.out.printf("\ntoken corrente: %d",currentToken.kind);
            while(currentToken.kind==Token.COMMA){
                
               System.out.printf("\ntoken corrente: %d",currentToken.kind);
               System.out.printf("\nToken esperado:%d", Token.COMMA );
               acceptIt();
               
               System.out.printf("\ntoken corrente: %d",currentToken.kind);
               System.out.printf("\nToken esperado:%d", Token.IDENTIFIER );
               accept(Token.IDENTIFIER);
             }
             break;
        
        default: 
            System.out.println("Erro");
     }
    
    System.out.printf("\ntoken corrente: %d",currentToken.kind);
    System.out.printf("\nToken esperado:%d", Token.VERTICAL_TWO_DOTS );
    accept(Token.VERTICAL_TWO_DOTS);
    
    System.out.printf("\ntoken corrente: %d",currentToken.kind);
    System.out.printf("\nToken esperado:%d ou %d ou %d", Token.INTEGER,Token.REAL,Token.BOOLEAN );
    if((currentToken.kind==Token.INTEGER) ||( currentToken.kind==Token.REAL) || (currentToken.kind==Token.BOOLEAN)){
            acceptIt();
    }else{System.out.println("Erro");}
 }
   
   
   private void parseTipo(){
       System.out.printf("\n Em ParseTipo");
       System.out.printf("\ntoken corrente: %d",currentToken.kind);
    
    switch(currentToken.kind){
        case Token.VAR:
            
        System.out.printf("\ntoken corrente: %d",currentToken.kind);
        System.out.printf("\nToken esperado:%d", Token.VAR );
        acceptIt();
        
        System.out.printf("\ntoken corrente: %d",currentToken.kind);
        System.out.printf("\nToken esperado:%d", Token.OPEN_COCHETE );
        accept(Token.OPEN_COCHETE);
        
        System.out.printf("\ntoken corrente: %d",currentToken.kind);
        switch(currentToken.kind){
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
        
        System.out.printf("\ntoken corrente: %d",currentToken.kind);
        System.out.printf("\nToken esperado:%d", Token.HORIZONTAL_TWO_DOTS );
        accept(Token.HORIZONTAL_TWO_DOTS);
        
        System.out.printf("\ntoken corrente: %d",currentToken.kind);
        switch(currentToken.kind){
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
        System.out.printf("\ntoken corrente: %d",currentToken.kind);
        System.out.printf("\nToken esperado:%d", Token.HORIZONTAL_TWO_DOTS );
        accept(Token.OF);
        parseTipo();
        break;
        
        case Token.INTEGER : case Token.BOOLEAN : case Token.REAL:
            System.out.printf("\ntoken corrente: %d",currentToken.kind);
            System.out.printf("\nToken esperado:%d", Token.INTEGER, Token.BOOLEAN,Token.REAL );
            acceptIt();
            
            break;
            
            default:
            System.out.println("Erro");
        
        }
      }
   
   
   
    
     public void parse(){
       
       scanner = new Scanner(codigoFonte);
       currentToken=scanner.scan();
      
       
       parseProgram();
       System.out.println("Cabou");
        if(currentToken.kind != Token.EOT)
          {
             System.out.println("Erro");
          }
       }
    
     
}
    

