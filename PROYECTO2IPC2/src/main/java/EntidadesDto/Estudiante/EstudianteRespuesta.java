/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Estudiante;

import Entidades.DatosMedicosEstudiante;
import Entidades.EncargadoEstudiante;
import Entidades.Estudiante;
import Entidades.Usuario;

/**
 *
 * @author mario
 */
public class EstudianteRespuesta {

    private String dpiUsuario;
    private String correoElectronico;
    private String nombreEstudiante;
    private String dpiEncargado;
    private String nombreEncargado;
    private String direccionEncargado;
    private String alergias;
    private String enfermedades;
    private String tipoDeSangre;
    private String medicamentos;
    private int contactoDeEmergencia;

    public EstudianteRespuesta(Usuario usuario, Estudiante estudiante, EncargadoEstudiante encargado, DatosMedicosEstudiante datosMedicos) {
        this.dpiUsuario = usuario.getIdUsuario();
        this.correoElectronico = usuario.getCorreoElectronico();
        this.nombreEstudiante = estudiante.getNombreEstudiante();
        this.dpiEncargado = encargado.getDpiEncargado();
        this.nombreEncargado = encargado.getNombreEncargado();
        this.direccionEncargado = encargado.getDireccionEncargado();
        this.alergias = datosMedicos.getAlergias();
        this.enfermedades = datosMedicos.getEnfermedades();
        this.tipoDeSangre = datosMedicos.getTipoDeSangre();
        this.medicamentos = datosMedicos.getMedicamentos();
        this.contactoDeEmergencia = datosMedicos.getContactoDeEmergencia();
    }
    
    

    public String getDpiUsuario() {
        return dpiUsuario;
    }

    public void setDpiUsuario(String dpiUsuario) {
        this.dpiUsuario = dpiUsuario;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getDpiEncargado() {
        return dpiEncargado;
    }

    public void setDpiEncargado(String dpiEncargado) {
        this.dpiEncargado = dpiEncargado;
    }

    public String getNombreEncargado() {
        return nombreEncargado;
    }

    public void setNombreEncargado(String nombreEncargado) {
        this.nombreEncargado = nombreEncargado;
    }

    public String getDireccionEncargado() {
        return direccionEncargado;
    }

    public void setDireccionEncargado(String direccionEncargado) {
        this.direccionEncargado = direccionEncargado;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getEnfermedades() {
        return enfermedades;
    }

    public void setEnfermedades(String enfermedades) {
        this.enfermedades = enfermedades;
    }

    public String getTipoDeSangre() {
        return tipoDeSangre;
    }

    public void setTipoDeSangre(String tipoDeSangre) {
        this.tipoDeSangre = tipoDeSangre;
    }

    public String getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(String medicamentos) {
        this.medicamentos = medicamentos;
    }

    public int getContactoDeEmergencia() {
        return contactoDeEmergencia;
    }

    public void setContactoDeEmergencia(int contactoDeEmergencia) {
        this.contactoDeEmergencia = contactoDeEmergencia;
    }
    
    
    

}
