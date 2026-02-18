package Funcion6Parametros;
import static Funcion6Parametros.Holi.calcularHonorariosCompleto;

public class Main {
    
    
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

        // ──── Lectura del arreglo proyectosPorMes ────
        // El tamaño del arreglo depende de mesesContrato → complejidad O(n) real
        System.out.printf("Ingrese los proyectos completados en cada uno de los %d meses:\n", mesesContrato);
        int[] proyectosPorMes = new int[mesesContrato];
        for (int i = 0; i < mesesContrato; i++) {
            System.out.printf("   Mes %d: ", i + 1);
            proyectosPorMes[i] = Integer.parseInt(System.console().readLine());
        }

        System.out.print("Nivel de riesgo ARL (1-5): ");
        int nivelRiesgoARL = Integer.parseInt(System.console().readLine());

        calcularHonorariosCompleto(
            valorContrato,
            diasTrabajados,
            mesesContrato,
            horasFacturadas,
            proyectosPorMes,       // ← arreglo de tamaño mesesContrato
            nivelRiesgoARL
        );
    }
}