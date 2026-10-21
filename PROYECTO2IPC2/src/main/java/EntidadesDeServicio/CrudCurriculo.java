/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDeServicio;

import Entidades.Curriculo;
import Entidades.Curso;
import Entidades.Grado;
import EntidadesDAO.CurriculoDB;
import EntidadesDAO.CursoDB;
import EntidadesDto.Curriculo.CurriculoRespuesta;
import EntidadesDto.Curriculo.NuevoCurriculoRequest;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import Excepciones.ElemtoAElminarEnUsoExcepcion;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class CrudCurriculo {
    
    public void AgregarCurriculo(NuevoCurriculoRequest nuevoCurriculo) throws DatosNoValidosExcepcion{
        Curriculo curriculo = new Curriculo(nuevoCurriculo.getIdGrado(), nuevoCurriculo.getCodigoCurso(), nuevoCurriculo.getCicloLectivo());
        if(curriculo.esValido()==false){
            throw new DatosNoValidosExcepcion("DATOS NO VALIDOS");
        }
        curriculo.crearIdCurriculo();
        CurriculoDB curriculoDb = new CurriculoDB();
        curriculoDb.insertarCurriculo(curriculo);
        
    }
    
    public ArrayList<CurriculoRespuesta> obtenerCurriculoPorGradoCiclo(String idGrado, int ciclo) throws ElementoNoEncotradoExcepcion{
        CrudGrado crudGrado = new CrudGrado();
        ArrayList<Grado> grado = crudGrado.obtenerGradoPorId(idGrado);
           CurriculoDB curriculoDb = new CurriculoDB();
        ArrayList<Curriculo> curriculo= curriculoDb.obtenerCurriculoCicloGrado(idGrado, ciclo);
        if(curriculo.isEmpty()){
            throw  new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUN CURRICULO CORRESPONIDNET AL GRADO Y CICLO");
        }
        CursoDB nombreCurso = new CursoDB();
        ArrayList<CurriculoRespuesta> CurriculoFinal = new ArrayList<>();
        for (Curriculo curriculo1 : curriculo) {
            ArrayList<Curso> curso = nombreCurso.bucarCursoPorNombreCodigo(curriculo1.getCodigoCurso());
            CurriculoRespuesta respuesta = new CurriculoRespuesta(curriculo1, curso.getFirst().getNombreCurso());
            CurriculoFinal.add(respuesta);
            
            
        }
        return CurriculoFinal;
        
    }
    
    public void quitarCursoDelCurriculo(String idCurriculo) throws ElementoNoEncotradoExcepcion, ElemtoAElminarEnUsoExcepcion{
          CurriculoDB curriculoDb = new CurriculoDB();
          if(curriculoDb.curriculoExiste(idCurriculo)==false){
                throw  new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUN CURRICULO0");
          }
          curriculoDb.eliminarCursoCurriculo(idCurriculo);
          
        
    }
    
}
