/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Reportes;

/**
 *
 * @author mario
 */
public class GetReporteAcademico {
    
    
    private String codigoCurso;
    private String nombreCurso;
    private Double promedioDeNotas;

    private String dificultad;
    private int notasFinalesPromedio;

    public GetReporteAcademico(String codigoCurso, String nombreCurso, Double promedioDeNotas, String dificultad, int notasFinalesPromedio) {
        this.codigoCurso = codigoCurso;
        this.nombreCurso = nombreCurso;
        this.promedioDeNotas = promedioDeNotas;
 
        this.dificultad = dificultad;
        this.notasFinalesPromedio = notasFinalesPromedio;
    }
   
    

    public int getNotasFinalesPromedio() {
        return notasFinalesPromedio;
    }

    public void setNotasFinalesPromedio(int notasFinalesPromedio) {
        this.notasFinalesPromedio = notasFinalesPromedio;
    }
 
    


    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
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

    public Double getPromedioDeNotas() {
        return promedioDeNotas;
    }

    public void setPromedioDeNotas(Double promedioDeNotas) {
        this.promedioDeNotas = promedioDeNotas;
    }
    
    
}

