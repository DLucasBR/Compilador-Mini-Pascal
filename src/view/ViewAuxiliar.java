/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author daluc
 */
abstract public class ViewAuxiliar {
    
    public static void imprimeTextArea(javax.swing.JTextArea textArea, ArrayList<Character> texto){
 
        for(Character c : texto)
                textArea.append(c.toString());
    }
    
    public static void imprimeTextArea(javax.swing.JTextArea textArea, String texto){
        
        textArea.append(texto);
    
    }
   
    public static void imprimeTextField(javax.swing.JTextField textField, String texto)
    {
    
        textField.setText(texto);
    
    }
    
    public static void limpaTextField(javax.swing.JTextArea textField){
        
        textField.setText("");
    
    }
    
    public static JFileChooser instanciaFileChooser (){
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione arquivo de codigo fonte");
        
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); 
        /*fileChooser aceita apenas arquivos (não diretorios)*/
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "txt");
        /*Por enquanto apenas .arrayDeCaracteresCodigoFonte, iremos criar uma extensão propria do mini-pascal no futuro?*/
        
        fileChooser.setFileFilter(filter);
        
        return fileChooser;
    }
    
    
    
    
}
