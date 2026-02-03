package com.plataformas.crud.config;

import com.plataformas.crud.model.Preventiva;
import com.plataformas.crud.repository.PreventivaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(2)
public class PreventivaDataLoader implements CommandLineRunner {

    private final PreventivaRepository preventivaRepository;

    @Override
    public void run(String... args) {
        if (preventivaRepository.count() > 0) {
            log.info("Dados de preventiva já carregados. Total: {}", preventivaRepository.count());
            return;
        }

        log.info("Carregando dados de preventiva...");
        List<Preventiva> preventivas = new ArrayList<>();

        // JLG - Série ES
        addPreventivasJLG(preventivas);
        
        // GENIE
        addPreventivasGENIE(preventivas);
        
        // SKYJACK
        addPreventivaSKYJACK(preventivas);
        
        // HAULOTTE
        addPreventivasHAULOTTE(preventivas);

        preventivaRepository.saveAll(preventivas);
        log.info("Carregados {} registros de preventiva", preventivas.size());
    }

    private void addPreventivasJLG(List<Preventiva> preventivas) {
        // JLG Serie ES - Tesouras Elétricas
        String[][] itensJLG_ES = {
            {"Filtro Hidráulico (retorno)", "7023576", "3004091", "1", "1", "1", "1"},
            {"Filtro de óleo hidráulico (carga)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de óleo (transmissão)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de óleo motor", "-", "-", "-", "-", "-", "-"},
            {"Filtro de ar (primário)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de ar (secundário)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de combustível", "-", "-", "-", "-", "-", "-"},
            {"Filtro de linha (diesel)", "-", "-", "-", "-", "-", "-"},
            {"Filtro separador", "-", "-", "-", "-", "-", "-"},
            {"Óleo hidráulico (litros)", "-", "3012490", "-", "-", "-", "14"},
            {"Óleo de motor (litros)", "-", "-", "-", "-", "-", "-"},
            {"Óleo para HUB (litros)", "-", "-", "-", "-", "-", "-"},
            {"Água de bateria", "M00500001", "-", "5", "5", "5", "5"}
        };
        
        for (String[] item : itensJLG_ES) {
            preventivas.add(createPreventiva("Serie ES", "JLG", item, "4", "4", "4", "5"));
        }

        // JLG Serie LE (4069)
        String[][] itensJLG_LE = {
            {"Filtro Hidráulico (retorno)", "7021616", "3004046", "1", "1", "1", "1"},
            {"Filtro de óleo hidráulico (carga)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de óleo (transmissão)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de óleo motor", "-", "-", "-", "-", "-", "-"},
            {"Filtro de ar (primário)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de ar (secundário)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de combustível", "-", "-", "-", "-", "-", "-"},
            {"Filtro de linha (diesel)", "-", "-", "-", "-", "-", "-"},
            {"Filtro separador", "-", "-", "-", "-", "-", "-"},
            {"Óleo hidráulico (litros)", "-", "3012490", "-", "-", "-", "60"},
            {"Óleo de motor (litros)", "-", "-", "-", "-", "-", "-"},
            {"Óleo para HUB (litros)", "-", "-", "-", "-", "-", "-"},
            {"Água de bateria", "M00500001", "-", "5", "5", "5", "5"}
        };
        
        for (String[] item : itensJLG_LE) {
            preventivas.add(createPreventiva("Serie LE (4069)", "JLG", item, "4", "4", "4", "5"));
        }

        // JLG Serie 10RS
        String[][] itensJLG_10RS = {
            {"Filtro Hidráulico (retorno)", "70003734", "3009825", "1", "1", "1", "1"},
            {"Filtro de óleo hidráulico (carga)", "70003696", "3009823", "1", "1", "1", "1"},
            {"Filtro de óleo (transmissão)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de óleo motor", "-", "-", "-", "-", "-", "-"},
            {"Filtro de ar (primário)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de ar (secundário)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de combustível", "-", "-", "-", "-", "-", "-"},
            {"Filtro de linha (diesel)", "-", "-", "-", "-", "-", "-"},
            {"Filtro separador", "-", "-", "-", "-", "-", "-"},
            {"Óleo hidráulico (litros)", "-", "3012490", "-", "-", "-", "14"},
            {"Óleo de motor (litros)", "-", "-", "-", "-", "-", "-"},
            {"Óleo para HUB (litros)", "-", "-", "-", "-", "-", "-"},
            {"Água de bateria", "M00500001", "-", "5", "5", "5", "5"}
        };
        
        for (String[] item : itensJLG_10RS) {
            preventivas.add(createPreventiva("Serie 10RS", "JLG", item, "4", "4", "4", "5"));
        }

        // JLG 450AJ Deutz Serie > 16
        String[][] itensJLG_450AJ_Deutz = {
            {"Filtro Óleo", "7016331", "3004091", "1", "1", "1", "1"},
            {"Filtro de Ar", "70003783", "3009825", "1", "1", "1", "1"},
            {"Filtro Combustível", "7020023", "3009823", "1", "1", "1", "1"},
            {"Filtro Hidráulico de Retorno", "70001960", "3004046", "1", "1", "1", "1"},
            {"Filtro Bomba Carga", "2120210", "3009825", "1", "1", "1", "1"},
            {"Óleo Lubrificante 15W40 (litros)", "-", "-", "12", "12", "12", "12"},
            {"Óleo Hidráulico (litros)", "-", "-", "-", "-", "-", "110"},
            {"Óleo Engrenagem 80W90 (litros)", "-", "-", "-", "-", "-", "5"}
        };
        
        for (String[] item : itensJLG_450AJ_Deutz) {
            preventivas.add(createPreventiva("450AJ Deutz (Serie > 16)", "JLG", item, "5", "5", "5", "5"));
        }

        // JLG 450AJ Perkins
        String[][] itensJLG_450AJ_Perkins = {
            {"Filtro de Ar", "7012664", "3004091", "1", "1", "1", "1"},
            {"Filtro do Combustível", "70001409", "3009825", "1", "1", "1", "1"},
            {"Filtro de óleo", "7020023", "3009823", "1", "1", "1", "1"},
            {"Filtro Primário Comb", "7027245", "3004046", "1", "1", "1", "1"},
            {"Filtro Lubrificante", "70001403", "3009825", "1", "1", "1", "1"},
            {"Filtro Hidráulico", "70001960", "3004046", "1", "1", "1", "1"},
            {"Filtro Bomba Carga", "2120210", "3009825", "1", "1", "1", "1"},
            {"Óleo Lubrificante 15W40 (litros)", "-", "-", "12", "12", "12", "12"},
            {"Óleo Hidráulico (litros)", "-", "-", "-", "-", "-", "110"},
            {"Óleo Engrenagem 80W90 (litros)", "-", "-", "-", "-", "-", "5"}
        };
        
        for (String[] item : itensJLG_450AJ_Perkins) {
            preventivas.add(createPreventiva("450AJ Perkins", "JLG", item, "8", "8", "8", "8"));
        }

        // JLG E450AJ (Elétrica)
        String[][] itensJLG_E450AJ = {
            {"Filtro Hidráulico", "70024372", "3010093", "1", "1", "1", "1"},
            {"Filtro de Carga", "70024373", "3010094", "1", "1", "1", "1"},
            {"Óleo Hidráulico (litros)", "-", "3012490", "-", "-", "-", "110"},
            {"Óleo Engrenagem 80W90 (litros)", "-", "-", "-", "-", "-", "5"},
            {"Água de bateria", "M00500001", "-", "8", "8", "8", "8"}
        };
        
        for (String[] item : itensJLG_E450AJ) {
            preventivas.add(createPreventiva("E450AJ", "JLG", item, "5", "5", "5", "5"));
        }

        // JLG 860SJ
        String[][] itensJLG_860SJ = {
            {"Filtro Hidráulico (retorno)", "70001960", "3004046", "1", "1", "1", "1"},
            {"Filtro de Carga", "2120210", "3009825", "1", "1", "1", "1"},
            {"Filtro de Ar Primário", "7012664", "3004091", "1", "1", "1", "1"},
            {"Filtro de Ar Secundário", "7012665", "3004092", "1", "1", "1", "1"},
            {"Filtro de Óleo Motor", "7016331", "3004091", "1", "1", "1", "1"},
            {"Filtro de Combustível", "7020023", "3009823", "1", "1", "1", "1"},
            {"Filtro Separador", "7016328", "3004094", "1", "1", "1", "1"},
            {"Óleo Lubrificante 15W40 (litros)", "-", "-", "15", "15", "15", "15"},
            {"Óleo Hidráulico (litros)", "-", "-", "-", "-", "-", "170"},
            {"Óleo Engrenagem 80W90 (litros)", "-", "-", "-", "-", "-", "8"}
        };
        
        for (String[] item : itensJLG_860SJ) {
            preventivas.add(createPreventiva("860SJ", "JLG", item, "6", "6", "6", "8"));
        }

        // JLG 1200SJP / 1350SJP
        String[][] itensJLG_1200SJP = {
            {"Filtro Hidráulico", "M00500008", "3010093", "1", "1", "1", "1"},
            {"Filtro de Carga", "2120210-2", "3010094", "1", "1", "1", "1"},
            {"Filtro de Retorno", "7024375", "3010095", "1", "1", "1", "1"},
            {"Filtro de Ar", "AF409K", "3010096", "1", "1", "1", "1"},
            {"Filtro Lubrificante", "LF3338", "3010097", "1", "1", "1", "1"},
            {"Filtro Separador", "FS1251", "3010098", "1", "1", "1", "1"},
            {"Óleo Lubrificante (litros)", "M00500006", "-", "15", "15", "15", "15"},
            {"Óleo Hidráulico (litros)", "-", "-", "-", "-", "-", "200"}
        };
        
        for (String[] item : itensJLG_1200SJP) {
            preventivas.add(createPreventiva("1200SJP", "JLG", item, "6", "6", "6", "8"));
            preventivas.add(createPreventiva("1350SJP", "JLG", item, "6", "6", "6", "8"));
        }
    }

    private void addPreventivasGENIE(List<Preventiva> preventivas) {
        // GENIE Serie GS DC (Tesouras Elétricas)
        String[][] itensGENIE_GS_DC = {
            {"Filtro Hidráulico", "44788", "3002888", "1", "1", "1", "1"},
            {"Filtro de óleo hidráulico (carga)", "44788", "3002888", "1", "1", "1", "1"},
            {"Filtro de óleo (transmissão)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de óleo motor", "-", "-", "-", "-", "-", "-"},
            {"Filtro de ar (primário)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de ar (secundário)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de combustível", "-", "-", "-", "-", "-", "-"},
            {"Filtro de linha (diesel)", "-", "-", "-", "-", "-", "-"},
            {"Filtro separador", "-", "-", "-", "-", "-", "-"},
            {"Óleo hidráulico (litros)", "-", "3012492", "-", "-", "-", "63"},
            {"Óleo de motor (litros)", "-", "-", "-", "-", "-", "-"},
            {"Óleo para HUB (litros)", "-", "-", "-", "-", "-", "-"},
            {"Água de bateria", "M00500001", "-", "12", "12", "12", "12"}
        };
        
        for (String[] item : itensGENIE_GS_DC) {
            preventivas.add(createPreventiva("Serie GS DC", "GENIE", item, "4", "4", "4", "5"));
        }

        // GENIE GS 2668 RT Perkins
        String[][] itensGENIE_GS2668RT = {
            {"Filtro Hidráulico (Retorno)", "131323", "3000963", "1", "1", "1", "1"},
            {"Filtro de óleo hidráulico (carga)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de óleo (transmissão)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de óleo motor", "94762", "3005128", "1", "1", "1", "1"},
            {"Filtro de ar (primário)", "52867", "3003240", "1", "1", "1", "1"},
            {"Filtro de ar (secundário)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de combustível", "62421", "3003400", "1", "1", "1", "1"},
            {"Filtro de linha (diesel)", "-", "-", "-", "-", "-", "-"},
            {"Filtro separador", "65853", "3003439", "-", "-", "-", "-"},
            {"Óleo hidráulico (litros)", "-", "3012492", "-", "-", "-", "115"},
            {"Óleo de motor (litros)", "-", "3012489", "9", "9", "9", "9"},
            {"Óleo para HUB (litros)", "-", "-", "-", "-", "-", "-"},
            {"Água de bateria", "M00500001", "-", "-", "-", "-", "-"}
        };
        
        for (String[] item : itensGENIE_GS2668RT) {
            preventivas.add(createPreventiva("GS 2668 RT Perkins", "GENIE", item, "4", "4", "4", "5"));
        }

        // GENIE Z-45/25 RT
        String[][] itensGENIE_Z45_25RT = {
            {"Filtro de Ar", "62420", "3003239", "1", "1", "1", "1"},
            {"Filtro Comb/Sep", "62433", "3003401", "1", "1", "1", "1"},
            {"Filtro Combustível", "62421", "3003400", "1", "1", "1", "1"},
            {"Filtro Hidráulico", "60857", "3003100", "-", "-", "-", "1"},
            {"Filtro de Óleo", "94762", "3005128", "1", "1", "1", "1"},
            {"Óleo Hidráulico (litros)", "-", "3012492", "-", "-", "-", "95"},
            {"Óleo de Motor (litros)", "-", "3012489", "9", "9", "9", "9"}
        };
        
        for (String[] item : itensGENIE_Z45_25RT) {
            preventivas.add(createPreventiva("Z-45/25 RT", "GENIE", item, "5", "5", "5", "6"));
        }

        // GENIE Z-34/22 RT
        String[][] itensGENIE_Z34_22RT = {
            {"Filtro de Ar", "52867", "3003240", "1", "1", "1", "1"},
            {"Filtro Comb/Sep", "65853", "3003439", "1", "1", "1", "1"},
            {"Filtro Combustível", "62421", "3003400", "1", "1", "1", "1"},
            {"Filtro Hidráulico", "44207", "3002887", "-", "-", "-", "1"},
            {"Filtro de Óleo", "94762", "3005128", "1", "1", "1", "1"},
            {"Óleo Hidráulico (litros)", "-", "3012492", "-", "-", "-", "75"},
            {"Óleo de Motor (litros)", "-", "3012489", "9", "9", "9", "9"}
        };
        
        for (String[] item : itensGENIE_Z34_22RT) {
            preventivas.add(createPreventiva("Z-34/22 RT", "GENIE", item, "5", "5", "5", "6"));
        }

        // GENIE Z-45/25 J DC (Elétrica)
        String[][] itensGENIE_Z45_25JDC = {
            {"Filtro Hidráulico", "44788", "3002888", "1", "1", "1", "1"},
            {"Óleo Hidráulico (litros)", "-", "3012492", "-", "-", "-", "75"},
            {"Água de bateria", "M00500001", "-", "8", "8", "8", "8"}
        };
        
        for (String[] item : itensGENIE_Z45_25JDC) {
            preventivas.add(createPreventiva("Z-45/25 J DC", "GENIE", item, "4", "4", "4", "5"));
        }

        // GENIE S-65TRAX
        String[][] itensGENIE_S65TRAX = {
            {"Filtro Hidráulico", "77688", "3004500", "1", "1", "1", "1"},
            {"Filtro de Ar Primário", "96587", "3005500", "1", "1", "1", "1"},
            {"Filtro de Ar Secundário", "96588", "3005501", "1", "1", "1", "1"},
            {"Filtro de Óleo Motor", "94762", "3005128", "1", "1", "1", "1"},
            {"Filtro de Combustível", "62421", "3003400", "1", "1", "1", "1"},
            {"Filtro Separador", "65853", "3003439", "1", "1", "1", "1"},
            {"Óleo Hidráulico (litros)", "-", "3012492", "-", "-", "-", "150"},
            {"Óleo de Motor (litros)", "-", "3012489", "12", "12", "12", "12"}
        };
        
        for (String[] item : itensGENIE_S65TRAX) {
            preventivas.add(createPreventiva("S-65TRAX", "GENIE", item, "6", "6", "6", "8"));
        }

        // GENIE SX-180
        String[][] itensGENIE_SX180 = {
            {"Filtro Hidráulico", "139453", "3009000", "1", "1", "1", "1"},
            {"Filtro de Ar Primário", "139454", "3009001", "1", "1", "1", "1"},
            {"Filtro de Ar Secundário", "139455", "3009002", "1", "1", "1", "1"},
            {"Filtro de Óleo Motor", "139456", "3009003", "1", "1", "1", "1"},
            {"Filtro de Combustível", "139457", "3009004", "1", "1", "1", "1"},
            {"Filtro Separador", "139458", "3009005", "1", "1", "1", "1"},
            {"Óleo Hidráulico (litros)", "-", "3012492", "-", "-", "-", "250"},
            {"Óleo de Motor (litros)", "-", "3012489", "20", "20", "20", "20"}
        };
        
        for (String[] item : itensGENIE_SX180) {
            preventivas.add(createPreventiva("SX-180", "GENIE", item, "8", "8", "8", "10"));
        }
    }

    private void addPreventivaSKYJACK(List<Preventiva> preventivas) {
        // SKYJACK SJ8841RT
        String[][] itensSKYJACK_SJ8841RT = {
            {"Filtro Hidráulico (retorno)", "104254", "3008115", "1", "1", "1", "1"},
            {"Filtro de óleo hidráulico (carga)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de óleo (transmissão)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de óleo motor", "119932", "3008196", "1", "1", "1", "1"},
            {"Filtro de ar (primário)", "3008101", "3008114", "1", "1", "1", "1"},
            {"Filtro de ar (secundário)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de combustível", "3008099", "3008859", "1", "1", "1", "1"},
            {"Filtro de linha (diesel)", "-", "-", "-", "-", "-", "-"},
            {"Filtro separador", "-", "-", "-", "-", "-", "-"},
            {"Óleo hidráulico (litros)", "-", "3012491", "-", "-", "-", "120"},
            {"Óleo de motor (litros)", "-", "3012489", "-", "-", "-", "10"},
            {"Óleo para HUB (litros)", "-", "3012496", "-", "-", "-", "3"},
            {"Água de bateria", "-", "-", "-", "-", "-", "-"}
        };
        
        for (String[] item : itensSKYJACK_SJ8841RT) {
            preventivas.add(createPreventiva("SJ8841RT", "SKYJACK", item, "3", "3", "3", "4"));
        }

        // SKYJACK SJ6832RT
        String[][] itensSKYJACK_SJ6832RT = {
            {"Filtro Hidráulico (retorno)", "104254", "3008115", "1", "1", "1", "1"},
            {"Filtro de óleo hidráulico (carga)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de óleo (transmissão)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de óleo motor", "119932", "3008196", "1", "1", "1", "1"},
            {"Filtro de ar (primário)", "3008101", "3008114", "1", "1", "1", "1"},
            {"Filtro de ar (secundário)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de combustível", "3008099", "3008859", "1", "1", "1", "1"},
            {"Filtro de linha (diesel)", "-", "-", "-", "-", "-", "-"},
            {"Filtro separador", "-", "-", "-", "-", "-", "-"},
            {"Óleo hidráulico (litros)", "-", "3012491", "-", "-", "-", "120"},
            {"Óleo de motor (litros)", "-", "3012489", "-", "-", "-", "10"},
            {"Óleo para HUB (litros)", "119932", "3008196", "-", "-", "-", "1"}
        };
        
        for (String[] item : itensSKYJACK_SJ6832RT) {
            preventivas.add(createPreventiva("SJ6832RT", "SKYJACK", item, "3", "3", "3", "4"));
        }

        // SKYJACK SJ9250RT
        String[][] itensSKYJACK_SJ9250RT = {
            {"Filtro Hidráulico (retorno)", "104254", "3008115", "1", "1", "1", "1"},
            {"Filtro de óleo hidráulico (carga)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de óleo (transmissão)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de óleo motor", "119932", "3008196", "1", "1", "1", "1"},
            {"Filtro de ar (primário)", "3008101", "3008114", "1", "1", "1", "1"},
            {"Filtro de ar (secundário)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de combustível", "3008099", "3008859", "1", "1", "1", "1"},
            {"Filtro de linha (diesel)", "-", "-", "-", "-", "-", "-"},
            {"Filtro separador", "-", "-", "-", "-", "-", "-"},
            {"Óleo hidráulico (litros)", "-", "3012491", "-", "-", "-", "150"},
            {"Óleo de motor (litros)", "-", "3012489", "-", "-", "-", "12"},
            {"Óleo para HUB (litros)", "119932", "3008196", "-", "-", "-", "3"}
        };
        
        for (String[] item : itensSKYJACK_SJ9250RT) {
            preventivas.add(createPreventiva("SJ9250RT", "SKYJACK", item, "3", "3", "3", "4"));
        }

        // SKYJACK SJIII 3219 / 3226 / 4626 / 4632 / 4732 (Tesouras Elétricas)
        String[][] itensSKYJACK_SJIII = {
            {"Filtro Hidráulico (retorno)", "108628", "3008102", "1", "1", "1", "1"},
            {"Filtro de óleo hidráulico (carga)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de óleo (transmissão)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de óleo motor", "137850", "3008957", "1", "1", "1", "1"},
            {"Filtro de ar (primário)", "137924", "3008101", "1", "1", "1", "1"},
            {"Filtro de ar (secundário)", "-", "-", "-", "-", "-", "-"},
            {"Filtro de combustível", "137904", "3008099", "1", "1", "1", "1"},
            {"Filtro de linha (diesel)", "-", "-", "-", "-", "-", "-"},
            {"Filtro separador", "-", "-", "-", "-", "-", "-"},
            {"Óleo hidráulico (litros)", "-", "3012491", "-", "-", "-", "120"},
            {"Óleo de motor (litros)", "-", "3012489", "-", "-", "-", "10"},
            {"Óleo para HUB (litros)", "-", "3012496", "-", "-", "-", "3"}
        };
        
        String[] modelosSJIII = {"SJIII 3219", "SJIII 3226", "SJIII 4626", "SJIII 4632", "SJIII 4732"};
        for (String modelo : modelosSJIII) {
            for (String[] item : itensSKYJACK_SJIII) {
                preventivas.add(createPreventiva(modelo, "SKYJACK", item, "3", "3", "3", "4"));
            }
        }

        // SKYJACK SJ45 AJ+ / SJ85 AJ
        String[][] itensSKYJACK_SJ45AJ = {
            {"Filtro Hidráulico (retorno)", "104254", "3008115", "1", "1", "1", "1"},
            {"Filtro de óleo motor", "119932", "3008196", "1", "1", "1", "1"},
            {"Filtro de ar (primário)", "3008101", "3008114", "1", "1", "1", "1"},
            {"Filtro de combustível", "3008099", "3008859", "1", "1", "1", "1"},
            {"Óleo hidráulico (litros)", "-", "3012491", "-", "-", "-", "100"},
            {"Óleo de motor (litros)", "-", "3012489", "-", "-", "-", "10"},
            {"Óleo para HUB (litros)", "119932", "3008196", "-", "-", "-", "2"}
        };
        
        for (String[] item : itensSKYJACK_SJ45AJ) {
            preventivas.add(createPreventiva("SJ45 AJ+", "SKYJACK", item, "4", "4", "4", "5"));
            preventivas.add(createPreventiva("SJ85 AJ", "SKYJACK", item, "4", "4", "4", "5"));
        }
    }

    private void addPreventivasHAULOTTE(List<Preventiva> preventivas) {
        // HAULOTTE C10N / C14N (Compact)
        String[][] itensHAULOTTE_Compact = {
            {"Kit de manutenção preventiva completo (OEM Haulotte)", "2427010430", "3009752", "1", "1", "1", "1"},
            {"Óleo hidráulico (litros)", "-", "-", "-", "-", "-", "-"},
            {"Óleo de motor (litros)", "-", "-", "-", "-", "-", "-"},
            {"Óleo para HUB (litros)", "119932", "3008196", "-", "-", "-", "1"}
        };
        
        for (String[] item : itensHAULOTTE_Compact) {
            preventivas.add(createPreventiva("C10N", "HAULOTTE", item, "3", "3", "3", "4"));
            preventivas.add(createPreventiva("C14N", "HAULOTTE", item, "3", "3", "3", "4"));
        }

        // HAULOTTE STAR8 / STAR10
        String[][] itensHAULOTTE_STAR = {
            {"Kit de manutenção preventiva completo (OEM Haulotte)", "2427010430", "3009752", "1", "1", "1", "1"},
            {"Óleo hidráulico (litros)", "-", "-", "-", "-", "-", "-"},
            {"Óleo de motor (litros)", "-", "-", "-", "-", "-", "-"},
            {"Óleo para HUB (litros)", "119932", "3008196", "-", "-", "-", "1"}
        };
        
        for (String[] item : itensHAULOTTE_STAR) {
            preventivas.add(createPreventiva("STAR8", "HAULOTTE", item, "3", "3", "3", "4"));
            preventivas.add(createPreventiva("STAR10", "HAULOTTE", item, "3", "3", "3", "4"));
        }

        // HAULOTTE HA15IP / HA16SPX / HA18SPX
        String[][] itensHAULOTTE_HA = {
            {"Kit de manutenção preventiva completo (OEM Haulotte)", "2427010430", "3009752", "1", "1", "1", "1"},
            {"Óleo hidráulico (litros)", "-", "-", "-", "-", "-", "20"},
            {"Óleo de motor (litros)", "-", "-", "-", "-", "-", "-"},
            {"Óleo para HUB (litros)", "119932", "3008196", "-", "-", "-", "1"}
        };
        
        for (String[] item : itensHAULOTTE_HA) {
            preventivas.add(createPreventiva("HA15IP", "HAULOTTE", item, "3", "3", "3", "4"));
            preventivas.add(createPreventiva("HA16SPX", "HAULOTTE", item, "3", "3", "3", "4"));
            preventivas.add(createPreventiva("HA18SPX", "HAULOTTE", item, "3", "3", "3", "4"));
        }

        // HAULOTTE HA20 / HA26
        String[][] itensHAULOTTE_HA20 = {
            {"Kit de manutenção preventiva completo (OEM Haulotte)", "2427010430", "3009752", "1", "1", "1", "1"},
            {"Óleo hidráulico (litros)", "-", "-", "-", "-", "-", "120"},
            {"Óleo de motor (litros)", "-", "-", "10", "10", "10", "10"},
            {"Óleo para HUB (litros)", "119932", "3008196", "-", "-", "-", "3"}
        };
        
        for (String[] item : itensHAULOTTE_HA20) {
            preventivas.add(createPreventiva("HA20", "HAULOTTE", item, "3", "3", "3", "4"));
            preventivas.add(createPreventiva("HA26", "HAULOTTE", item, "3", "3", "3", "4"));
        }

        // HAULOTTE HA16RTJ
        String[][] itensHAULOTTE_HA16RTJ = {
            {"Kit de manutenção preventiva completo (OEM Haulotte)", "2427010430", "3009752", "1", "1", "1", "1"},
            {"Óleo hidráulico (litros)", "-", "-", "-", "-", "-", "80"},
            {"Óleo de motor (litros)", "-", "-", "8", "8", "8", "8"},
            {"Óleo para HUB (litros)", "119932", "3008196", "-", "-", "-", "2"}
        };
        
        for (String[] item : itensHAULOTTE_HA16RTJ) {
            preventivas.add(createPreventiva("HA16RTJ", "HAULOTTE", item, "3", "3", "3", "4"));
        }
    }

    private Preventiva createPreventiva(String modelo, String fabricante, String[] item, 
                                        String mo250, String mo500, String mo750, String mo1000) {
        return Preventiva.builder()
                .modelo(modelo)
                .fabricante(fabricante)
                .itemDescricao(item[0])
                .partNumber(item[1].equals("-") ? null : item[1])
                .codSap(item[2].equals("-") ? null : item[2])
                .qtd250h(item[3].equals("-") ? null : item[3])
                .qtd500h(item[4].equals("-") ? null : item[4])
                .qtd750h(item[5].equals("-") ? null : item[5])
                .qtd1000h(item[6].equals("-") ? null : item[6])
                .horasMo250h(mo250)
                .horasMo500h(mo500)
                .horasMo750h(mo750)
                .horasMo1000h(mo1000)
                .build();
    }
}
