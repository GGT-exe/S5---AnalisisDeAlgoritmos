package Funcion6Parametros;

public class Holi {
    
    
    public static void calcularHonorariosCompleto(
        double valorContrato,
        int diasTrabajados,
        int mesesContrato,
        double horasFacturadas,
        int proyectosCompletados,
        int nivelRiesgoARL
    ) {
        
        // ──── ୨୧ ──── CALCULO BASE SEGURIDAD SOCIAL ──── ୨୧ ──── O(1)
        
        double baseSeguridad = valorContrato * 0.40; // 40% del contrato
        
        // ──── ୨୧ ──── PORCENTAJE ARL SEGUN NIVEL DE RIESGO ──── ୨୧ ──── O(1)
        
        double porcentajeARL = 0.00522; // Riesgo 1 por defecto
        
        if (nivelRiesgoARL == 2) {
            porcentajeARL = 0.01044;
        } else if (nivelRiesgoARL == 3) {
            porcentajeARL = 0.02436;
        } else if (nivelRiesgoARL == 4) {
            porcentajeARL = 0.04350;
        } else if (nivelRiesgoARL == 5) {
            porcentajeARL = 0.06960;
        }
        
        // ──── ୨୧ ──── DESCUENTOS SEGURIDAD SOCIAL ──── ୨୧ ──── O(1)
        
        double descuentoSalud = baseSeguridad * 0.125;      // 12.5%
        double descuentoPension = baseSeguridad * 0.16;     // 16%
        double descuentoARL = baseSeguridad * porcentajeARL;
        
        double totalSeguridadSocial = descuentoSalud + descuentoPension + descuentoARL;
        
        // ──── ୨୧ ──── VALOR NETO PARCIAL (SIN BONOS) ──── ୨୧ ──── O(1)
        
        double valorNetoParcial = valorContrato - totalSeguridadSocial;
        
        // ──── ୨୧ ──── BUSQUEDA BINARIA - BONO POR DURACION CONTRATO ──── ୨୧ ──── O(log n)
        
        int[] tablaMesesContrato = {1, 3, 6, 9, 12, 18, 24, 36};
        double[] tablaPorcentajeBonos = {0.0, 1.5, 3.0, 4.5, 6.0, 8.0, 10.0, 12.0};
        
        int inicio = 0;
        int fin = tablaMesesContrato.length - 1;
        double porcentajeBonoDuracion = 0.0;
        
        // Búsqueda binaria con ciclo while para encontrar el porcentaje de bono según meses de contrato
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
        
        // Si no se encontró exactamente, tomar el rango anterior
        if (porcentajeBonoDuracion == 0.0) {
            if (fin < 0) {
                porcentajeBonoDuracion = tablaPorcentajeBonos[0];
            } else {
                porcentajeBonoDuracion = tablaPorcentajeBonos[fin];
            }
        }
        
        double bonoDuracion = valorContrato * (porcentajeBonoDuracion / 100);
        
        // ──── ୨୧ ──── BUSQUEDA BINARIA - BONO POR DIAS TRABAJADOS ──── ୨୧ ──── O(log n)
        
        int[] tablaDiasTrabajados = {15, 20, 25, 28, 30};
        double[] tablaPorcentajeDias = {0.0, 0.5, 1.0, 1.5, 2.0};
        
        inicio = 0;
        fin = tablaDiasTrabajados.length - 1;
        double porcentajeBonoDias = 0.0;
        
        // Búsqueda binaria con ciclo while
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
        
        if (porcentajeBonoDias == 0.0) {
            if (fin < 0) {
                porcentajeBonoDias = tablaPorcentajeDias[0];
            } else {
                porcentajeBonoDias = tablaPorcentajeDias[fin];
            }
        }
        
        double bonoDias = valorContrato * (porcentajeBonoDias / 100);
        
        
        // ──── ୨୧ ──── COMPARACION BONOS VS METAS ──── ୨୧ ──── O(n^2)
        
        double[] bonosMensuales = {180000, 220000, 200000, 250000, 190000, 210000};
        double[] metasRendimiento = {180000, 200000, 220000, 240000};
        
        double bonoExtraRendimiento = 0;
        
        for (int i = 0; i < bonosMensuales.length; i++) {
            for (int j = 0; j < metasRendimiento.length; j++) {
                if (bonosMensuales[i] >= metasRendimiento[j]) {
                    bonoExtraRendimiento += bonosMensuales[i] * 0.05;
                }
            }
        }
        
        // ──── ୨୧ ──── BONO POR PROYECTOS COMPLETADOS (CADA 3 PROYECTOS) ──── ୨୧ ──── O(1)
        
        int gruposDe3Proyectos = proyectosCompletados / 3; // División entera
        double bonoProyectos = gruposDe3Proyectos * 150000; // $150,000 por cada 3 proyectos
        int proyectosRestantes = proyectosCompletados % 3; // Proyectos que no completan grupo de 3
        
        // ──── ୨୧ ──── TOTAL CON BONOS ──── ୨୧ ──── O(1)
        
        double totalBonos = bonoDuracion + bonoDias + 
                           bonoExtraRendimiento + bonoProyectos;
        double valorTotalHonorarios = valorContrato + totalBonos;
        double valorNetoFinal = valorTotalHonorarios - totalSeguridadSocial;
        
        // ──── ୨୧ ──── ANALISIS ──── ୨୧ ──── O(1)

        double incrementoNetoConBonos = valorNetoFinal - valorNetoParcial;
        
        // ──── ୨୧ ──── IMPRESION DE RESULTADOS ──── ୨୧ ────
        
        System.out.println("\n﹌﹌﹌﹌﹌﹌﹌");
        System.out.println("    LIQUIDACION CONTRATO PRESTACION SERVICIOS   ");
        System.out.println("================================================\n");
        
        System.out.println("HONORARIOS:");
        System.out.printf("   Valor contrato mensual:   $%,15.2f\n", valorContrato);
        System.out.printf("   Dias trabajados mes:      %d dias\n", diasTrabajados);
        System.out.printf("   Duracion contrato:        %d meses\n", mesesContrato);
        System.out.printf("   Horas facturadas:         %.0f horas\n", horasFacturadas);
        System.out.printf("   Proyectos completados:    %d proyectos\n", proyectosCompletados);
        System.out.printf("   Nivel riesgo ARL:         Nivel %d\n", nivelRiesgoARL);
        System.out.printf("   ----------------------------------------------\n");
        System.out.printf("   Base seguridad social:    $%,15.2f (40%%)\n\n", baseSeguridad);
        
        System.out.println("BONOS ADICIONALES:");
        System.out.printf("   Bono duracion (%.1f%%):     $%,15.2f\n", porcentajeBonoDuracion, bonoDuracion);
        System.out.printf("   Bono dias (%.1f%%):         $%,15.2f\n", porcentajeBonoDias, bonoDias);
        System.out.printf("   Bono proyectos:           $%,15.2f (%d grupos x $150,000)\n", 
                         bonoProyectos, gruposDe3Proyectos);
        if (proyectosRestantes > 0) {
            System.out.printf("   (Faltan %d proyecto(s) para siguiente bono)\n", 
                             3 - proyectosRestantes);
        }
        System.out.printf("   Bono rendimiento:         $%,15.2f\n", bonoExtraRendimiento);
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

        System.out.println("\n🚨 ADVERTENCIA LEGAL:");
        System.out.println("Si existe subordinacion, horario fijo, cumplimiento");
        System.out.println("de ordenes o jefe directo, este contrato podria");
        System.out.println("ser reclasificado como LABORAL por el Ministerio");
        System.out.println("del Trabajo, con obligacion de pagar prestaciones.");
        System.out.println("================================================\n");
    }
    
}