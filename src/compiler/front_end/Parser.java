package compiler.front_end;


public class Parser {
    
    private Token currentToken;
    
    
    private void accept (byte expectedKind){
        if(currentToken==expectedKind)
           currentToken=scanner.scan();
        else {/*retornar erro*/}
      }
    
    
    private void acceptIt(){
        currentToken=scanner.scan();
      }
    
    
    private void parseProgram()
    {
              accept(Token.PROGRAM);
              accept(Token.IDENTIFIER);
              accept(Token.SEMI_COLON);
              parseCorpo();
              accept(Token.SINGLE_DOT);
    }
    
    
    private void parseCorpo(){
      parseDeclaracoes();
      parseComandoComposto();
    }
    
    
    private void parseDeclaracoes(){
     
     while(currentToken.kind!=Token.BEGIN)
     {
        parseDeclaracao();
        accept(Token.SEMI_COLON);
     }
     
    }
    
    
    
    private void parseDeclaracao(){
     switch(currentToken.kind){
         case Token.VAR:
         
             acceptIt();
             accept(Token.IDENTIFIER);
             while(currentToken.kind == Token.COMMA ){
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
           if((currentToken.kind== Token.VAR) ||(currentToken.kind==Token.IDENTIFIER)){
             parseParametros();
             while(currentToken.kind==Token.SEMI_COLON){
                acceptIt();
                parseParametros();
             }
            }
           accept(Token.CLOSE_PARENTESES);
           accept(Token.VERTICAL_TWO_DOTS);
         
          if((currentToken.kind==Token.INTEGER) ||( currentToken.kind==Token.REAL) || (currentToken.kind==Token.BOOLEAN)){
            acceptIt();
          }else{/*retornar erro*/}
          
          accept(Token.SEMI_COLON);
          parseCorpo();
          break;
        
         
        case Token.PROCEDURE:
         acceptIt();
         accept(Token.IDENTIFIER);
         accept(Token.OPEN_PARENTESES);
         if((currentToken.kind==Token.VAR) ||(currentToken.kind==Token.IDENTIFIER)){
           parseParametros();
           while(currentToken.kind==Token.SEMI_COLON){
             acceptIt();
             parseParametros();
            }
         
         }
         accept(Token.CLOSE_PARENTESES);
         accept(Token.SEMI_COLON);
         parseCorpo();
         break;
         
         default:
             //erro
     }
    }
    
    
    private void  parseComandoComposto(){
     
      accept(Token.BEGIN);
       
      while(currentToken.kind!=Token.END){
         parseComando();
         while(currentToken.kind==Token.SEMI_COLON){
           acceptIt();
           parseComando();
         }
        }
      accept(Token.END);
    } 
    
    
   private void parseComando(){
    switch(currentToken.kind){
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
          if(currentToken.kind==Token.ELSE)
             parseComando();
          break;
        
        
        case Token.IDENTIFIER:
        
            acceptIt();
            if(currentToken.kind==Token.OPEN_PARENTESES)
                parseChamProcedimento();
            else {while(currentToken.kind==Token.OPEN_COCHETE)
                    {
                    acceptIt();
                    parseExpressao();
                    accept(Token.CLOSE_COCHETE);
                    }
                    accept(Token.ATTRIBUTION);
                    parseExpressao();
            }
            break;
            
            
        case Token.BEGIN:
             acceptIt();
             while(currentToken.kind != Token.END ){
             parseComando();
             accept(Token.SEMI_COLON);
             }
            accept(Token.END);
            break;
        default:
           //erro
     }
    }
    
   
    
    private void parseChamProcedimento(){
      accept(Token.OPEN_PARENTESES);
      if(currentToken.kind!=Token.CLOSE_PARENTESES){
             parseExpressao();
             while(currentToken.kind==Token.COMMA){
              acceptIt();
              parseExpressao();
              }
      }
       accept(Token.CLOSE_PARENTESES);
     }
    
    
    
    private void parseExpressao(){
     
               parseTermo();
               while(currentToken.kind==Token.OP_AD){
                  acceptIt();
                  parseTermo();
               }
        
      if(currentToken.kind==Token.OP_REL){
             acceptIt();
             parseTermo();
             while(currentToken.kind==Token.OP_AD){
             acceptIt();
             parseTermo();
             }
         }
    }
    
    
    private void parseTermo(){
        parseFator();
        while(currentToken.kind==Token.OP_MUL){
           acceptIt();
           parseFator();
        }
    
    }
    
    
    private void parseFator(){
      switch(currentToken.kind){
          case Token.INT_LITERAL: case Token.FLOAT_LITERAL: case Token.BOOL_LITERAL:
          acceptIt();
          break;
          
          case Token.IDENTIFIER:
             acceptIt();
             if(currentToken.kind==Token.OPEN_PARENTESES){
                parseExpressao();
                while(currentToken.kind==Token.COMMA){
                    acceptIt();
                    parseExpressão();
                  }
              }
              else {
                   while(currentToken.kind==Token.OPEN_COCHETE){
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
              //erro
        }
    }
    
    
    
   private void parseParametros(){
    switch(currentToken.kind){
        case Token.VAR:
        
          acceptIt();
          accept(Token.IDENTIFIER);
          while(currentToken.kind==Token.COMMA){
            acceptIt();
            accept(Token.IDENTIFIER);
          }
          break;
          
        case Token.IDENTIFIER:
            acceptIt();
            while(currentToken.kind==COMMA){
               acceptIt();
               accept(Token.IDENTIFIER);
             }
             break;
        
        default: 
            //erro
     }
    
    accept(Token.VERTICAL_TWO_DOTS);
    if((currentToken.kind==Token.INTEGER) ||( currentToken.kind==Token.REAL) || (currentToken.kind==Token.BOOLEAN)){
            acceptIt();
    }else{/*retornar erro*/}
 }
   
   
   private void parseTipo(){
    switch(currentToken.kind){
        case Token.VAR:
        {
        acceptIt();
        accept(Token.OPEN_COCHETE);
        switch(currentToken.kind){
            case Token.INT_LITERAL:
                acceptIt();
                break;
            case Token.FLOAT_LITERAL:
                acceptIt();
                break;
            case Token.BOOL_LITERAL:
                acceptIt;
                break;
            default:
                //erro
        }
        accept(token.HORIZONTAL_TWO_DOTS);
        switch(currentToken.kind){
            case Token.INT_LITERAL:
                acceptIt();
                break;
            case Token.FLOAT_LITERAL:
                acceptIt();
                break;
            case Token.BOOL_LITERAL:
                acceptIt;
                break;
            default:
                //erro
        }
        parseTipo();
        break;
        
        case Token.Integer: case Token.BOOLEAN: case Token.REAL:
            acceptIt();
            break;
            
            default:
            //erro
        
        }
   }
   
   
    
     public void parse(){
       currentToken=scanner.scan();
       parsePrograma();
        if(currentToken.kind != Token.EOT)
          {
             //reportar error
          }
       }
    
}
    

