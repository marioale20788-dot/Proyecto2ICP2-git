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
    private String IdCurriculo;
    private String idGrado;
    private String coidgoCurso;
    private String nombreCurso;
    private Integer cilcloLectivo;


    public CurriculoRespuesta(Curriculo curriculo,String nombreCurso ) {
        this.IdCurriculo = curriculo.getIdCurriculo();
        this.idGrado = curriculo.getIdGrado();
        this.coidgoCurso =curriculo.getCodigoCurso();
        this.cilcloLectivo = curriculo.getCicloLectivo();
        this.nombreCurso=nombreCurso;
        
     
       
    }

   
    public String getIdCurriculo() {
        return IdCurriculo;
    }

    public void setIdCurriculo(String IdCurriculo) {
        this.IdCurriculo = IdCurriculo;
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

    public String getCoidgoCurso() {
        return coidgoCurso;
    }

    public void setCoidgoCurso(String coidgoCurso) {
        this.coidgoCurso = coidgoCurso;
    }

    public Integer getCilcloLectivo() {
        return cilcloLectivo;
    }

    public void setCilcloLectivo(Integer cilcloLectivo) {
        this.cilcloLectivo = cilcloLectivo;
    }
    
    
    
    
    
}
