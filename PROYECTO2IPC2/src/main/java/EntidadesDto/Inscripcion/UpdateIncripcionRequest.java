/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Inscripcion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;

/**
 *
 * @author mario
 */
public class UpdateIncripcionRequest {
  
    private String idSeccion;


    public String getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(String idSeccion) {
        this.idSeccion = idSeccion;
    }
    
    
}
