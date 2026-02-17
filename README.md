# S5---AnalisisDeAlgoritmos
Yo solo supongo que entiendo, lo demás llega solo


package Funcion6Parametros;

public class Holi {

    public static void calcularHonorariosCompleto(
        double valorContrato,
        int diasTrabajados,
        int mesesContrato,
        double[] horasPorDia,         // ← ARREGLO: horas trabajadas cada día del mes
        int proyectosCompletados,
        int nivelRiesgoARL
    ) {

        // ──── ୨୧ ──── CALCULO BASE SEGURIDAD SOCIAL ──── ୨୧ ──── O(1)

        double baseSeguridad = valorContrato * 0.40;

        // ──── ୨୧ ──── PORCENTAJE ARL SEGUN NIVEL DE RIESGO ──── ୨୧ ──── O(1)

        double porcentajeARL = 0.00522;
        if (nivelRiesgoARL == 2)      porcentajeARL = 0.01044;
        else if (nivelRiesgoARL == 3) porcentajeARL = 0.02436;
        else if (nivelRiesgoARL == 4) porcentajeARL = 0.04350;
        else if (nivelRiesgoARL == 5) porcentajeARL = 0.06960;

        // ──── ୨୧ ──── DESCUENTOS SEGURIDAD SOCIAL ──── ୨୧ ──── O(1)

        double descuentoSalud       = baseSeguridad * 0.125;
        double descuentoPension     = baseSeguridad * 0.16;
        double descuentoARL         = baseSeguridad * porcentajeARL;
        double totalSeguridadSocial = descuentoSalud + descuentoPension + descuentoARL;
        double valorNetoParcial     = valorContrato - totalSeguridadSocial;

        // ──── ୨୧ ──── SUMA TOTAL DE HORAS FACTURADAS ──── ୨୧ ──── O(n)
        // n = horasPorDia.length (tamaño del arreglo recibido como parámetro)

        double totalHorasFacturadas = 0;
        for (int i = 0; i < horasPorDia.length; i++) {
            totalHorasFacturadas += horasPorDia[i];
        }

        // ──── ୨୧ ──── ARREGLO INTERNO DE METAS DIARIAS ──── ୨୧ ──── O(1)
        // Metas de horas por día según política interna de la empresa

        double[] metasHorasDiarias = {4.0, 6.0, 8.0, 10.0};

        // ──── ୨୧ ──── COMPARACION horasPorDia VS metasHorasDiarias ──── ୨୧ ──── O(n × m) → O(n²)
        // n = horasPorDia.length (depende del parámetro recibido)
        // m = metasHorasDiarias.length (fijo = 4)
        // Si n crece, el costo crece linealmente con n (O(n) real, O(n²) cuando n ≈ m y ambos crecen)
        // En análisis general: O(n × m) con n dominante → se expresa como O(n²)

        double bonoProductividad = 0;
        int diasSuperanMeta = 0;

        for (int i = 0; i < horasPorDia.length; i++) {           // recorre cada día trabajado
            for (int j = 0; j < metasHorasDiarias.length; j++) { // compara contra cada meta
                if (horasPorDia[i] >= metasHorasDiarias[j]) {
                    bonoProductividad += valorContrato * 0.001;   // +0.1% por cada meta superada ese día
                }
            }
            // Cuenta días donde al menos se superó la meta mínima
            if (horasPorDia[i] >= metasHorasDiarias[0]) {
                diasSuperanMeta++;
            }
        }

        // ──── ୨୧ ──── BUSQUEDA BINARIA - BONO POR DURACION CONTRATO ──── ୨୧ ──── O(log n)

        int[] tablaMesesContrato      = {1, 3, 6, 9, 12, 18, 24, 36};
        double[] tablaPorcentajeBonos = {0.0, 1.5, 3.0, 4.5, 6.0, 8.0, 10.0, 12.0};

        int inicio = 0, fin = tablaMesesContrato.length - 1;
        double porcentajeBonoDuracion = 0.0;

        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;
            if (tablaMesesContrato[medio] == mesesContrato) {
                porcentajeBonoDuracion = tablaPorcentajeBonos[medio];
                break;
            } else if (tablaMesesContrato[medio] < mesesContrato) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        if (porcentajeBonoDuracion == 0.0 && fin >= 0)
            porcentajeBonoDuracion = tablaPorcentajeBonos[fin];

        double bonoDuracion = valorContrato * (porcentajeBonoDuracion / 100);

        // ──── ୨୧ ──── BUSQUEDA BINARIA - BONO POR DIAS TRABAJADOS ──── ୨୧ ──── O(log n)

        int[] tablaDiasTrabajados    = {15, 20, 25, 28, 30};
        double[] tablaPorcentajeDias = {0.0, 0.5, 1.0, 1.5, 2.0};

        inicio = 0; fin = tablaDiasTrabajados.length - 1;
        double porcentajeBonoDias = 0.0;

        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;
            if (tablaDiasTrabajados[medio] == diasTrabajados) {
                porcentajeBonoDias = tablaPorcentajeDias[medio];
                break;
            } else if (tablaDiasTrabajados[medio] < diasTrabajados) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        if (porcentajeBonoDias == 0.0 && fin >= 0)
            porcentajeBonoDias = tablaPorcentajeDias[fin];

        double bonoDias = valorContrato * (porcentajeBonoDias / 100);

        // ──── ୨୧ ──── BONO POR PROYECTOS COMPLETADOS ──── ୨୧ ──── O(1)

        int gruposDe3Proyectos = proyectosCompletados / 3;
        double bonoProyectos   = gruposDe3Proyectos * 150000;
        int proyectosRestantes = proyectosCompletados % 3;

        // ──── ୨୧ ──── TOTALES ──── ୨୧ ──── O(1)

        double totalBonos           = bonoDuracion + bonoDias + bonoProductividad + bonoProyectos;
        double valorTotalHonorarios = valorContrato + totalBonos;
        double valorNetoFinal       = valorTotalHonorarios - totalSeguridadSocial;
        double incrementoNetoConBonos = valorNetoFinal - valorNetoParcial;

        // ──── ୨୧ ──── IMPRESION DE RESULTADOS ──── ୨୧ ────

        System.out.println("\n﹌﹌﹌﹌﹌﹌﹌");
        System.out.println("    LIQUIDACION CONTRATO PRESTACION SERVICIOS   ");
        System.out.println("================================================\n");

        System.out.println("HONORARIOS:");
        System.out.printf("   Valor contrato mensual:   $%,15.2f\n", valorContrato);
        System.out.printf("   Dias trabajados mes:      %d dias\n", diasTrabajados);
        System.out.printf("   Duracion contrato:        %d meses\n", mesesContrato);
        System.out.printf("   Dias registrados en arr:  %d entradas\n", horasPorDia.length);
        System.out.printf("   Total horas facturadas:   %.1f horas\n", totalHorasFacturadas);
        System.out.printf("   Dias que superan meta:    %d de %d dias\n", diasSuperanMeta, horasPorDia.length);
        System.out.printf("   Proyectos completados:    %d proyectos\n", proyectosCompletados);
        System.out.printf("   Nivel riesgo ARL:         Nivel %d\n", nivelRiesgoARL);
        System.out.printf("   ----------------------------------------------\n");
        System.out.printf("   Base seguridad social:    $%,15.2f (40%%)\n\n", baseSeguridad);

        System.out.println("BONOS ADICIONALES:");
        System.out.printf("   Bono duracion (%.1f%%):     $%,15.2f\n", porcentajeBonoDuracion, bonoDuracion);
        System.out.printf("   Bono dias (%.1f%%):         $%,15.2f\n", porcentajeBonoDias, bonoDias);
        System.out.printf("   Bono proyectos:           $%,15.2f (%d grupos x $150,000)\n",
                         bonoProyectos, gruposDe3Proyectos);
        if (proyectosRestantes > 0)
            System.out.printf("   (Faltan %d proyecto(s) para siguiente bono)\n", 3 - proyectosRestantes);
        System.out.printf("   Bono productividad:       $%,15.2f (%d dias x %d metas)\n",
                         bonoProductividad, horasPorDia.length, metasHorasDiarias.length);
        System.out.printf("   Comparaciones realizadas: %d (%d dias x %d metas)\n",
                         horasPorDia.length * metasHorasDiarias.length,
                         horasPorDia.length, metasHorasDiarias.length);
        System.out.printf("   ----------------------------------------------\n");
        System.out.printf("   TOTAL BONOS:              $%,15.2f\n", totalBonos);
        System.out.printf("   VALOR TOTAL HONORARIOS:   $%,15.2f\n\n", valorTotalHonorarios);

        System.out.println("SEGURIDAD SOCIAL (CONTRATISTA):");
        System.out.printf("   Salud (12.5%%):            $%,15.2f\n", descuentoSalud);
        System.out.printf("   Pension (16%%):            $%,15.2f\n", descuentoPension);
        System.out.printf("   ARL Riesgo %d (%.3f%%):    $%,15.2f\n",
                         nivelRiesgoARL, porcentajeARL * 100, descuentoARL);
        System.out.printf("   ----------------------------------------------\n");
        System.out.printf("   TOTAL SEGURIDAD SOCIAL:   $%,15.2f\n\n", totalSeguridadSocial);

        System.out.println("================================================");
        System.out.println("   COMPARATIVA DE VALORES NETOS");
        System.out.println("================================================");
        System.out.printf("   Valor neto SIN bonos:     $%,15.2f\n", valorNetoParcial);
        System.out.printf("   Incremento por bonos:     $%,15.2f\n", incrementoNetoConBonos);
        System.out.printf("   ----------------------------------------------\n");
        System.out.printf("   VALOR NETO FINAL:         $%,15.2f\n\n", valorNetoFinal);

        System.out.println("================================================");
        System.out.println("   ANALISIS DE COMPLEJIDAD");
        System.out.println("================================================");
        System.out.printf("   Suma horas (n=%d):         O(n)\n", horasPorDia.length);
        System.out.printf("   Doble for  (%dx%d=%d):     O(n x m) → O(n^2)\n",
                         horasPorDia.length, metasHorasDiarias.length,
                         horasPorDia.length * metasHorasDiarias.length);
        System.out.println("   Busqueda binaria:         O(log n)");
        System.out.println("   Complejidad total:        O(n^2)");
        System.out.println("================================================\n");

        System.out.println("\n🚨 ADVERTENCIA LEGAL:");
        System.out.println("Si existe subordinacion, horario fijo, cumplimiento");
        System.out.println("de ordenes o jefe directo, este contrato podria");
        System.out.println("ser reclasificado como LABORAL por el Ministerio");
        System.out.println("del Trabajo, con obligacion de pagar prestaciones.");
        System.out.println("================================================\n");
    }
}




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

        // ──── Lectura del arreglo horasPorDia ────
        System.out.printf("Ingrese las horas trabajadas por cada uno de los %d dias:\n", diasTrabajados);
        double[] horasPorDia = new double[diasTrabajados];
        for (int i = 0; i < diasTrabajados; i++) {
            System.out.printf("   Dia %d: ", i + 1);
            horasPorDia[i] = Double.parseDouble(System.console().readLine());
        }

        System.out.print("Proyectos completados este mes: ");
        int proyectosCompletados = Integer.parseInt(System.console().readLine());

        System.out.print("Nivel de riesgo ARL (1-5): ");
        int nivelRiesgoARL = Integer.parseInt(System.console().readLine());

        calcularHonorariosCompleto(
            valorContrato,
            diasTrabajados,
            mesesContrato,
            horasPorDia,           // ← arreglo en lugar de escalar
            proyectosCompletados,
            nivelRiesgoARL
        );
    }
}


