/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDeServicio;

import Entidades.CicloLectivo;
import EntidadesDAO.CicloLectivoDB;
import EntidadesDto.CicloLectivo.NewCicloRequest;
import EntidadesDto.CicloLectivo.UpdateCicloRequest;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import Excepciones.ElemtoAElminarEnUsoExcepcion;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class CrudCicloLectivo {

    public CicloLectivo crearCicloLectivo(NewCicloRequest nuevoCiclo) throws DatosNoValidosExcepcion {
        CicloLectivo cicloLectivo = extraerCiclo(nuevoCiclo);
        CicloLectivoDB cicloDB = new CicloLectivoDB();
        return cicloDB.crearCicloLectivo(cicloLectivo);

    }

    public ArrayList<CicloLectivo> obtenerTodosLosCiclos() {
        CicloLectivoDB cicloDB = new CicloLectivoDB();
        ArrayList<CicloLectivo> ciclos = cicloDB.obtenerTodosLosCiclos();
        return ciclos;

    }

    public CicloLectivo obtenerCicloPorAnio(int anio) throws ElementoNoEncotradoExcepcion {
        CicloLectivoDB cicloDB = new CicloLectivoDB();
        ArrayList<CicloLectivo> ciclos = cicloDB.buscarCicloPorAnio(anio);
        if (ciclos.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUN CICLO EN EL ANIO INDICADO");
        }
        return ciclos.getFirst();

    }

    public void ElminiarCiclo(int anio) throws ElementoNoEncotradoExcepcion, ElemtoAElminarEnUsoExcepcion {
        CicloLectivoDB cicloDB = new CicloLectivoDB();
        ArrayList<CicloLectivo> ciclos = cicloDB.buscarCicloPorAnio(anio);
        if (ciclos.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUN CICLO EN EL ANIO INDICADO");
        }
        cicloDB.eliminarCicloSinUso(anio);
    }

    public CicloLectivo updateCiclo(int anio, UpdateCicloRequest datosACamiar) throws ElementoNoEncotradoExcepcion, DatosNoValidosExcepcion {
        CicloLectivoDB cicloDB = new CicloLectivoDB();
        CicloLectivo ciclo = obtenerCicloPorAnio(anio);
        if (datosACamiar.getFechaFinal() != null) {
            ciclo.setFechaFinal(datosACamiar.getFechaFinal());
        } else if (datosACamiar.getFechaInicio() != null) {
            ciclo.setFechaInicio(datosACamiar.getFechaInicio());
        } else {
            ciclo.setEstado(datosACamiar.getEstado());
        }
        if (ciclo.datosValidos() == false) {
            throw new DatosNoValidosExcepcion("LAS FECHAS NO CONCUERDAN ");

        }
        return cicloDB.updateCiclo(anio, ciclo);

    }

    public CicloLectivo extraerCiclo(NewCicloRequest nuevoCiclo) throws DatosNoValidosExcepcion {
        try {
            CicloLectivo ciclo = new CicloLectivo(nuevoCiclo.getAnio(),
                    nuevoCiclo.getFechaInicio(),
                    nuevoCiclo.getFechaFinal(),
                    nuevoCiclo.getEstado());
            if (ciclo.datosValidos() == false) {
                throw new DatosNoValidosExcepcion("LAS FECHAS NO CONCUERDAN ");

            }
            return ciclo;

        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DatosNoValidosExcepcion("Datos enviados incorrectos");
        }

    }
}
