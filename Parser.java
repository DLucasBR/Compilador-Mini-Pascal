
package compilador;


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
       switch(currentToken.kind){
           case token.PROGRAM: 
            {
              acceptIt();
              accept(token.IDENTIFIER);
              accept(token.SEMI_COLON);
              
              switch(currentToken.kind){
                 case token.PROGRAM:{
                    parseCorpo();
                    accept(token.SINGLE_DOT);
                 }
               }  
           }
           break;
           
           default:
               println("erro");
           }
    }
    
    
    private void parseCorpo(){
      parseDeclaracoes();
      parseComandoComposto();
    }
    
    
    private void parseDeclaracoes(){
     parseDeclaracao();
     accept(token.SEMI_COLON);
     
     while(currentToken.kind==(token.VAR || token.FUNTION || token.PROCEDURE){
       parseDeclaracao();
       accept(token.SEMI_COLON);
     }
    }
    
    
    
    private void parseDeclaracao(){
     switch(currentToken.kind){
         case token.VAR:
         {
             acceptIt();
             accept(token.IDENTIFIER);
             while(currentToken.kind == token.COMMA ){
              acceptIt();
              accept(token.IDENTIFIER);
             }
             
             accept(token.VERTICAL_TWO_DOTS);
             parseTipo();
         }
         break;
        
         
         case token.FUNCTION:
         {
           acceptIt();
           accept(token.IDENTIFIER);
           accept(token.OPEN_PARENTESES);
           if(currentToken.kind== (token.VAR ||token.IDENTIFIER)){
             parseParametros();
             while(currentToken.kind==token.SEMI_COLON){
                acceptIt();
                parseParametros();
             }
            }
           accept(token.CLOSE_PARENTESES);
           accept(token.VERTICAL_TWO_DOTS);
         
          if(currentToken.kind==(token.INTEGER || token.REAL || token.BOOLEAN)){
            acceptIt();
          }else{/*retornar erro*/}
          
          accept(token.SEMI_COLON);
          parseCorpo();
         }
         break;
        
         
        case token.PROCEDURE:
        {
         acceptIt();
         accept(token.ID);
         accept(token.OPEN_PARENTESES);
         if(currentToken.kind== (token.VAR ||token.IDENTIFIER)){
           parseParametros();
           while(currentToken.kind==token.SEMI_COLON){
             acceptIt();
             parseParametros();
            }
         
         }
         accept(token.CLOSE_PARENTESES);
         accept(token.SEMI_COLON);
         parseCorpo();
         }
        break;
         
         default:
             //erro
     }
    }
    
    
    private void  parseComandoComposto(){
     
      accept(token.BEGIN);
      if(currentToken.kind==(token.IF|| token.WHILE ||token.BEGIN || token.IDENTIFIER)){
         parseComando();
         while(currentToken.kind==token.SEMI_COLON){
           acceptIt();
           parseComando();
         }
      
      }
      accept(token.END);
    }
    
    
   private void parseComando(){
    switch(currentToken.kind){
        case token.WHILE:{
          acceptIt();
          parseExpressao();
          accept(token.DO);
          parseComando();
        }
        break;
        
        case token.IF:
        {
          acceptIt();
          parseExpressao();
          accept(token.THEN);
          parseComando();
          if(currentToken.kind==token.ELSE)
             parseComando();
        }
        break;
        
        
        case token.IDENTIFIER:
        {
            acceptIt();
            if(currentToken.kind==token.OPEN_PARENTESES)
                parseChamProcedimento();
            else if(currentToken.kind==token.OPEN_COCHETE){
                    acceptIt();
                    parseExpressao();
                    accept(token.CLOSE_COCHETE);
                    while(currentToken.kind==token.OPEN_COCHETE)
                    {
                    acceptIt();
                    parseExpressao();
                    accept(token.CLOSE_COCHETE);
                    }
                    
                    accept(token.VERTICAL_TWO_DOTS);
                    accept(token.ATTRIBUTION);
                    }
            else {/*retornar erro*/}
        }
        break;
    
        
        default:
           //erro
     }
    }
    
   
    
    private void parseChamProcedimento(){
      accept(token.IDENTIFIER);
      accept(token.OPEN_PARENTESES);
      if(currentTerminal.kind==(token.IDENTIFIER||(token.INT_LITERAL||token.FLOAT_LITERAL||token.BOOL_LITERAL)||token.OPEN_PARENTESES)
        {
           parseExpressao();
           if(currentToken.kind== (token.VAR ||token.IDENTIFIER)){
             parseParametros();
             while(currentToken.kind==token.COMMA){
              acceptIt();
              parseExpressao();
              }
            }
        }
        accept(token.CLOSE_PARENTESES);
    }
    
    
    
    public void parse(){
     currentToken=scanner.scan();
     parsePrograma();
        if(currentToken.kind != token.EOT)
          {
             //reportar error
          }
       }
    
    }
    

