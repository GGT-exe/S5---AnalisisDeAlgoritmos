package Funcion6Parametros;
import static Funcion6Parametros.Holi.calcularHonorariosCompleto;

public class Main {
    
    /**
     * Función principal que calcula los honorarios y descuentos
     * para contrato de prestación de servicios
     * 
     * @param valorContrato - Valor mensual del contrato
     * @param diasTrabajados - Días trabajados en el mes
     * @param mesesContrato - Meses de duración del contrato
     * @param horasFacturadas - Horas facturadas en el mes (para bonos)
     * @param proyectosCompletados - Número de proyectos completados
     * @param nivelRiesgoARL - Nivel de riesgo ARL (1-5)
     */

    
    public static void main(String[] args) {
        System.out.println("================================================");
        System.out.println("   SISTEMA DE CALCULO - PRESTACION DE SERVICIOS ");
        System.out.println("              Normativa Colombia                ");
        System.out.println("================================================\n");
        
        System.out.print("Valor mensual del contrato: $");
        double valorContrato = Double.parseDouble(System.console().readLine());
        
        System.out.print("Dias trabajados en el mes (max 30): ");
        int diasTrabajados = Integer.parseInt(System.console().readLine());
        
        System.out.print("Duracion del contrato en meses: ");
        int mesesContrato = Integer.parseInt(System.console().readLine());
        
        System.out.print("Horas facturadas en el mes: ");
        double horasFacturadas = Double.parseDouble(System.console().readLine());
        
        System.out.print("Proyectos completados este mes: ");
        int proyectosCompletados = Integer.parseInt(System.console().readLine());
        
        System.out.print("Nivel de riesgo ARL (1-5): ");
        int nivelRiesgoARL = Integer.parseInt(System.console().readLine());
        
        calcularHonorariosCompleto(
            valorContrato,
            diasTrabajados,
            mesesContrato,
            horasFacturadas,
            proyectosCompletados,
            nivelRiesgoARL
        );
    }
}
