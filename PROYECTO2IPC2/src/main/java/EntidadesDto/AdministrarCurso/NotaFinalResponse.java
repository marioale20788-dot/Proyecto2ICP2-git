/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.AdministrarCurso;

/**
 *
 * @author mario
 */
public class NotaFinalResponse {
    private String idNota;
    private Double notaFinal;
    private String idEstudiante;
    private String codigoCurso;
    private Integer ciclo;
    private String nombreCurso;

    public NotaFinalResponse(String idNota, Double notaFinal, String idEstudiante, String codigoCurso, Integer ciclo, String nombreCurso) {
        this.idNota = idNota;
        this.notaFinal = notaFinal;
        this.idEstudiante = idEstudiante;
        this.codigoCurso = codigoCurso;
        this.ciclo = ciclo;
        this.nombreCurso = nombreCurso;
    }

    
    
    
    public String getIdNota() {
        return idNota;
    }

    public void setIdNota(String idNota) {
        this.idNota = idNota;
    }

    public Double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Double notaFinal) {
        this.notaFinal = notaFinal;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(String codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    public Integer getCiclo() {
        return ciclo;
    }

    public void setCiclo(Integer ciclo) {
        this.ciclo = ciclo;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }
    

    
    
}
