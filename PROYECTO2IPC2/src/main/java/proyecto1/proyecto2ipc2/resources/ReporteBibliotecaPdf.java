/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package proyecto1.proyecto2ipc2.resources;

import Entidades.Pagos;
import EntidadesDeServicio.CrudPago;
import EntidadesDeServicio.Reportes;
import EntidadesDto.Pagos.GetPrestamosConMoraEstudiante;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author mario
 */
@WebServlet(name = "ReporteBibliotecaPdf", urlPatterns = {"/ReporteBibliotecaPdf"})
public class ReporteBibliotecaPdf extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String reportPath = "/WEB-INF/reportes/ModeloReporteBiblioteca.jrxml";

        try (InputStream jrxmlInput = getServletContext().getResourceAsStream(reportPath)) {
            if (jrxmlInput == null) {
                throw new RuntimeException("EL JRXML NO SE ENCONTRO EN : " + reportPath);

             
            }
            Reportes report = new Reportes();

                List<GetPrestamosConMoraEstudiante> estudianteCostos = report.prestamosConMora()
                        .stream()
                        .map(GetPrestamosConMoraEstudiante::new)
                        .toList();

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(estudianteCostos);

            JasperDesign disenio = JRXmlLoader.load(jrxmlInput);
            JasperReport reporte = JasperCompileManager.compileReport(disenio);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("TITULO_REPORTE", "REPORTE DE BIBLIOTECA");
            parametros.put("SUBTITULO_REPORTE", "USUARIOS CON MORA EN BIBLIOTECA");

            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, dataSource);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=ReportePago.pdf");

            try (OutputStream out = response.getOutputStream()) {
                JasperExportManager.exportReportToPdfStream(jasperPrint, out);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/plain");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error al generar el PDF: " + e.getMessage());
        }
    }
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

}
