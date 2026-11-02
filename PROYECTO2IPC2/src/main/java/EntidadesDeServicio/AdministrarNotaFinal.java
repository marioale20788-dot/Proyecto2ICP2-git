/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDeServicio;

import Entidades.Curso;
import Entidades.NotaFinal;
import EntidadesDAO.CursoDB;
import EntidadesDAO.NotaFinalDb;
import EntidadesDAO.NotasDb;
import EntidadesDto.AdministrarCurso.NotaFinalResponse;
import EntidadesDto.AdministrarCurso.NuevanotaFinalRequest;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import java.util.ArrayList;

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
    
    public ArrayList<NotaFinalResponse>  obtenerNotaFinal(String idUsuario, int ciclo ) throws  ElementoNoEncotradoExcepcion{
        NotaFinalDb notaFb = new NotaFinalDb();
        
        CursoDB cursoDb = new CursoDB();
       ArrayList<NotaFinal> notasFinales= notaFb.obtenerNotasFinales(idUsuario, ciclo);
       if(notasFinales.isEmpty()){
           throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUNA NOTA REGISTRADA PARA ESTE CICLO");
       }
       
       ArrayList<NotaFinalResponse> notas = new ArrayList<>();
        for (NotaFinal notasFinale : notasFinales) {
            ArrayList<Curso> nombreCurso = cursoDb.bucarCursoPorNombreCodigo(notasFinale.getCodigoCurso());
           NotaFinalResponse finalRespones = new NotaFinalResponse(notasFinale.getIdNota(), 
                   notasFinale.getNotaFinal(), 
                   idUsuario, 
                   notasFinale.getCodigoCurso(), 
                   ciclo, 
                   nombreCurso.getFirst().getNombreCurso());
           
           notas.add(finalRespones);
            
        }
        return notas;
        
      
    }
    
    
    
}
