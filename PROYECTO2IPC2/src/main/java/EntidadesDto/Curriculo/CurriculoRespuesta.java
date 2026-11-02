/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Curriculo;

import Entidades.Curriculo;
import Entidades.Grado;

/**
 *
 * @author mario
 */
public class CurriculoRespuesta {
    private String idCurriculo;
    private String idGrado;
    private String codigoCurso;
    private String nombreCurso;
    private Integer cicloLectivo;


    public CurriculoRespuesta(Curriculo curriculo,String nombreCurso ) {
        this.idCurriculo = curriculo.getIdCurriculo();
        this.idGrado = curriculo.getIdGrado();
        this.codigoCurso =curriculo.getCodigoCurso();
        this.cicloLectivo = curriculo.getCicloLectivo();
        this.nombreCurso=nombreCurso;
        
     
       
    }
    public CurriculoRespuesta(){
        
    }

   
    public String getIdCurriculo() {
        return idCurriculo;
    }

    public void setIdCurriculo(String IdCurriculo) {
        this.idCurriculo = IdCurriculo;
    }

    public String getIdGrado() {
        return idGrado;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public void setIdGrado(String idGrado) {
        this.idGrado = idGrado;
    }

    public String getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(String codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    public Integer getCicloLectivo() {
        return cicloLectivo;
    }

    public void setCicloLectivo(Integer cicloLectivo) {
        this.cicloLectivo = cicloLectivo;
    }

    
    
    
    
}
