/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Estudiante;

/**
 *
 * @author mario
 */
public class RegistroEstudianteCompleto {
    private NuevoEstudianteRequest estudiante;
    private NuevoEstudianteEncargadoRequest encargado;
    private NuevoEstudianteDatosMedicosRequest datosMedicos;

    public NuevoEstudianteRequest getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(NuevoEstudianteRequest estudiante) {
        this.estudiante = estudiante;
    }

    public NuevoEstudianteEncargadoRequest getEncargado() {
        return encargado;
    }

    public void setEncargado(NuevoEstudianteEncargadoRequest encargado) {
        this.encargado = encargado;
    }

    public NuevoEstudianteDatosMedicosRequest getDatosMedicos() {
        return datosMedicos;
    }

    public void setDatosMedicos(NuevoEstudianteDatosMedicosRequest datosMedicos) {
        this.datosMedicos = datosMedicos;
    }

   
  
}


