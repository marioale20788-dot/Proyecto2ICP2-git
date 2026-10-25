/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.AdministrarCurso;

/**
 *
 * @author mario
 */
public class AsistenciaRepuesta {
    
    
    private String idEstudiante;
    private int faltas;
    private int llegadas;

    public AsistenciaRepuesta(String idEstudiante, int faltas, int llegadas) {
        this.idEstudiante = idEstudiante;
        this.faltas = faltas;
        this.llegadas = llegadas;
    }

    
    
    
    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public int getLlegadas() {
        return llegadas;
    }

    public void setLlegadas(int llegadas) {
        this.llegadas = llegadas;
    }
    
    
    
    
    
}
