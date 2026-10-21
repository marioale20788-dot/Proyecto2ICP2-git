/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import EntidadesDto.Estudiante.UpdateEstudiante;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mario
 */
public class DatosMedicosEstudiante {
      private String idEstudiante;
    private String alergias;
    private String enfermedades;
    private String tipoDeSangre;
    private String medicamentos;
    private int contactoDeEmergencia;

    public DatosMedicosEstudiante(String idEstudiante, String alergias, String enfermedades, String tipoDeSangre, String medicamentos, int contactoDeEmergencia) {
        this.idEstudiante = idEstudiante;
        this.alergias = alergias;
        this.enfermedades = enfermedades;
        this.tipoDeSangre = tipoDeSangre;
        this.medicamentos = medicamentos;
        this.contactoDeEmergencia = contactoDeEmergencia;
    }
    

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
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
    public void updateDatosMedicos(UpdateEstudiante update){
        if(update.getTipoDeSangre()!=null){
            this.tipoDeSangre = update.getTipoDeSangre();
        }
        if(update.getAlergias()!=null){
            this.alergias = update.getAlergias();
        }
        if(update.getEnfermedades()!=null){
            this.enfermedades = update.getEnfermedades();
        }
        if(update.getMedicamentos()!=null){
            this.medicamentos = update.getMedicamentos();
        }
        if(update.getContactoDeEmergencia()!=null){
            this.contactoDeEmergencia = update.getContactoDeEmergencia();
        }
    }
    
    public boolean esValido(){
         return StringUtils.isNotBlank(idEstudiante)
                && StringUtils.isNotBlank(tipoDeSangre)
                && contactoDeEmergencia>0;
    }    
}
