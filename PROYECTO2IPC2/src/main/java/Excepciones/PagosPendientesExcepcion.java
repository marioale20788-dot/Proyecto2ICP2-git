/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *
 * @author mario
 */
public class PagosPendientesExcepcion extends Exception{
   
    public PagosPendientesExcepcion(){
        
    }
     public PagosPendientesExcepcion(String mensaje){
        super(mensaje);
    }
}
