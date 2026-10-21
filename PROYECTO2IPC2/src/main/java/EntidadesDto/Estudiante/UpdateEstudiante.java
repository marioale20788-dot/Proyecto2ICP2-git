/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Estudiante;

/**
 *
 * @author mario
 */
public class UpdateEstudiante {
     private String correoElectronico;
     private Boolean estado;
     
    private String nombreEstudiante;
    
    private String nombreEncargado;
    private String direccion;
    
      private String alergias;
    private String enfermedades;
    private String tipoDeSangre;
    private String medicamentos;
    private Integer contactoDeEmergencia;
    

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getNombreEncargado() {
        return nombreEncargado;
    }

    public void setNombreEncargado(String nombreEncargado) {
        this.nombreEncargado = nombreEncargado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public Integer getContactoDeEmergencia() {
        return contactoDeEmergencia;
    }

    public void setContactoDeEmergencia(Integer contactoDeEmergencia) {
        this.contactoDeEmergencia = contactoDeEmergencia;
    }
    public boolean editarUsuario(){
        return correoElectronico!=null||estado!=null;
    }
    
    public boolean editarEstudiante(){
        return nombreEstudiante!=null;
    }
    
    public boolean editarEncargado(){
        return nombreEncargado!=null||direccion!=null;
    }
    public boolean editarDatosMedicos(){
         return alergias!=null||enfermedades!=null ||tipoDeSangre!=null||medicamentos!=null||contactoDeEmergencia!=null;
    }
    
    
    
    
}
