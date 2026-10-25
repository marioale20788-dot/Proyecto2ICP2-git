/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Maestro;

/**
 *
 * @author mario
 */
public class getAsignacionesRequest {
    
    
    
    private String idSeccion;
      private String nombreSeccion;
    private String idCurriculo;
    private String idEmpleado;
    private int ciclo;
    private String codigoCurso;
    private String nombreCurso;
    private String idGrado; 
    private String nombreGrado;
    private String nivelGrado;
    private String idAsignacion;

    public getAsignacionesRequest(String idSeccion, String nombreSeccion, String idCurriculo, String idEmpleado, int ciclo, String codigoCurso, String nombreCurso, String idGrado, String nombreGrado,String nivelGrado,String idAsignacion) {
        this.idSeccion = idSeccion;
        this.nombreSeccion = nombreSeccion;
        this.idCurriculo = idCurriculo;
        this.idEmpleado = idEmpleado;
        this.ciclo = ciclo;
        this.codigoCurso = codigoCurso;
        this.nombreCurso = nombreCurso;
        this.idGrado = idGrado;
        this.nombreGrado = nombreGrado;
        this.nivelGrado = nivelGrado;
        this.idAsignacion =idAsignacion;
    }

    public String getNivelGrado() {
        return nivelGrado;
    }

    public void setNivelGrado(String nivelGrado) {
        this.nivelGrado = nivelGrado;
    }

    public String getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(String idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    
    
    
    
    public String getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(String idSeccion) {
        this.idSeccion = idSeccion;
    }

    public String getNombreSeccion() {
        return nombreSeccion;
    }

    public void setNombreSeccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }

    public String getIdCurriculo() {
        return idCurriculo;
    }

    public void setIdCurriculo(String idCurriculo) {
        this.idCurriculo = idCurriculo;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public String getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(String codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public String getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(String idGrado) {
        this.idGrado = idGrado;
    }

    public String getNombreGrado() {
        return nombreGrado;
    }

    public void setNombreGrado(String nombreGrado) {
        this.nombreGrado = nombreGrado;
    }
    
    
    
    
    
    
}
