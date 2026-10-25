/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDeServicio;

import Entidades.NotaFinal;
import EntidadesDAO.NotaFinalDb;
import EntidadesDAO.NotasDb;
import EntidadesDto.AdministrarCurso.NuevanotaFinalRequest;
import Excepciones.DatosNoValidosExcepcion;

/**
 *
 * @author mario
 */
public class AdministrarNotaFinal {
    
    
    public void insertarNotaFinal(NuevanotaFinalRequest notaFinal) throws DatosNoValidosExcepcion{
        NotaFinal nota = new NotaFinal(null, notaFinal.getNotaFinal(), notaFinal.getIdEstudiante(), notaFinal.getCodigoCurso(), notaFinal.getCicloLectivo());
        if(nota.esValido()==false){
            throw new DatosNoValidosExcepcion("datos invalidos");
        }
      nota.crearId();
        NotaFinalDb notaFinalDb = new NotaFinalDb();
        notaFinalDb.insertarNota(nota);
        
        
    }
    
    
}
