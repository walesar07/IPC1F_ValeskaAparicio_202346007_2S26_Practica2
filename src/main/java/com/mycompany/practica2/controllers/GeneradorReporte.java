
package com.mycompany.practica2.controllers;

import com.mycompany.practica2.models.Partida;
 
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
 
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
 


public class GeneradorReporte {
    
    private static final int ANCHO_GRAFICA = 600;
    private static final int ALTO_GRAFICA = 400;
    private static final String NOMBRE_IMAGEN = "top_puntajes.png";
    private static final String NOMBRE_HTML = "reporte_top_puntajes.html";
 
    /*
      Genera la imagen de la gráfica y el archivo HTML dentro de la
      carpeta indicada (la crea si no existe).
     
      @return la ruta absoluta del archivo HTML generado.
     */
    public String generarReporte(Partida[] top, File carpetaDestino) throws IOException {
        if (!carpetaDestino.exists()) {
            carpetaDestino.mkdirs();
        }
 
        File archivoImagen = new File(carpetaDestino, NOMBRE_IMAGEN);
        generarGrafica(top, archivoImagen);
 
        File archivoHtml = new File(carpetaDestino, NOMBRE_HTML);
        generarHtml(top, archivoHtml);
 
        return archivoHtml.getAbsolutePath();
    }
 
    /*
      Crea una gráfica de barras (piloto/nave vs. puntaje) usando
      JFreeChart y la guarda como imagen PNG (vía ImageIO, por debajo
      de ChartUtils.saveChartAsPNG).
     */
    private void generarGrafica(Partida[] top, File archivoImagen) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
 
        for (int i = 0; i < top.length; i++) {
            Partida partida = top[i];
            String etiqueta = partida.getNombrePiloto() + " (" + partida.getNivel().getNombreNave() + ")";
            dataset.addValue(partida.getPuntajeObtenido(), "Puntaje", etiqueta);
        }
 
        JFreeChart grafica = ChartFactory.createBarChart(
                "Top de Puntajes - Quetzal Space Defender",
                "Piloto",
                "Puntaje",
                dataset,
                PlotOrientation.VERTICAL,
                false, // sin leyenda (solo hay una serie: "Puntaje")
                true,  // tooltips
                false  // urls
        );
 
        ChartUtils.saveChartAsPNG(archivoImagen, grafica, ANCHO_GRAFICA, ALTO_GRAFICA);
    }
 
    /*
      Arma el HTML del reporte: incrusta la imagen de la gráfica y
      agrega una tabla con el detalle de cada partida del top.
     */
    private void generarHtml(Partida[] top, File archivoHtml) throws IOException {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(archivoHtml))) {
            escritor.println("<html>");
            escritor.println("<head>");
            escritor.println("<meta charset='UTF-8'>");
            escritor.println("<title>Reporte de Puntajes</title>");
            escritor.println("<style>");
            escritor.println("body { font-family: Arial, sans-serif; margin: 40px; }");
            escritor.println("table { border-collapse: collapse; width: 100%; margin-top: 20px; }");
            escritor.println("th, td { border: 1px solid #999; padding: 8px; text-align: left; }");
            escritor.println("th { background-color: #222; color: white; }");
            escritor.println("</style>");
            escritor.println("</head>");
            escritor.println("<body>");
            escritor.println("<h1>Quetzal Space Defender - Reporte de Puntajes</h1>");
            escritor.println("<img src='" + NOMBRE_IMAGEN + "' alt='Gráfica de puntajes' width='" + ANCHO_GRAFICA + "'>");
 
            escritor.println("<table>");
            escritor.println("<tr><th>#</th><th>Piloto</th><th>Nave</th><th>Puntaje</th></tr>");
 
            for (int i = 0; i < top.length; i++) {
                Partida partida = top[i];
                escritor.println("<tr>"
                        + "<td>" + (i + 1) + "</td>"
                        + "<td>" + partida.getNombrePiloto() + "</td>"
                        + "<td>" + partida.getNivel().getNombreNave() + "</td>"
                        + "<td>" + partida.getPuntajeObtenido() + "</td>"
                        + "</tr>");
            }
 
            escritor.println("</table>");
            escritor.println("</body>");
            escritor.println("</html>");
        }
    }
}
    
