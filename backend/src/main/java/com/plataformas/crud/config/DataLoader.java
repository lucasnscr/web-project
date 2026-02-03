package com.plataformas.crud.config;

import com.plataformas.crud.model.Plataforma;
import com.plataformas.crud.repository.PlataformaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final PlataformaRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            log.info("Carregando dados iniciais das plataformas...");
            repository.saveAll(getPlataformas());
            log.info("Dados carregados com sucesso! Total: {} plataformas", repository.count());
        } else {
            log.info("Banco de dados já possui {} plataformas", repository.count());
        }
    }

    private List<Plataforma> getPlataformas() {
        return Arrays.asList(
            // Tesoura Elétrica (Elétrica Firme Nivelado)
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 13 pés", 3.9, "GS-1330", "GENIE"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 13 pés", 6.0, "3CPT0607DCM", "DINGLI"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 19 pés", 7.6, "1930ES", "JLG"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 19 pés", 7.6, "SJIII 3219", "SKYJACK"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 19 pés", 7.8, "GS-1930", "GENIE"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 19 pés", 7.8, "6RS", "JLG"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 19 pés", 7.77, "Optimum 8", "HAULOTTE"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 19 pés", 7.8, "1932 R", "JLG"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 20 pés", 7.9, "GS-2032", "GENIE"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 20 pés", 7.9, "2030ES", "JLG"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 20 pés", 8.0, "3CPT0807AC", "DINGLI"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 20 pés", 8.1, "SJIII 3220", "SKYJACK"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 20 pés", 8.2, "Compact 8", "HAULOTTE"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 26 pés", 9.77, "2632ES", "JLG"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 26 pés", 9.75, "SJIII 4626", "SKYJACK"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 26 pés", 9.8, "2630ES", "JLG"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 26 pés", 9.8, "2646ES", "JLG"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 26 pés", 9.9, "SJIII 3226", "SKYJACK"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 26 pés", 9.92, "GS-2646", "GENIE"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 26 pés", 9.9, "GS-2632", "GENIE"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 26 pés", 10.0, "3CPT1008AC", "DINGLI"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 26 pés", 10.0, "Compact 10 N", "HAULOTTE"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 26 pés", 10.15, "Compact 10", "HAULOTTE"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 32 pés", 11.6, "SJIII 4632", "SKYJACK"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 32 pés", 11.75, "10RS", "JLG"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 32 pés", 11.75, "GS-3232", "GENIE"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 32 pés", 11.75, "GS-3246", "GENIE"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 32 pés", 11.8, "3246ES", "JLG"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 32 pés", 12.0, "3CPT1212AC", "DINGLI"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 38 pés", 11.65, "SJIII 4732", "SKYJACK"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 40 pés", 12.2, "4069LE", "JLG"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 40 pés", 13.85, "Compact 14", "HAULOTTE"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 40 pés", 13.89, "GS-4047", "GENIE"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 40 pés", 14.0, "3CPT1412AC", "DINGLI"),
            createPlataforma("Tesoura Elétrica", "Elétrica", "Firme Nivelado", "Plataforma Tesoura Elétrica de 46 pés", 16.0, "3CPT1614AC", "DINGLI"),

            // Tesoura Diesel (Diesel Acidentado Irregular)
            createPlataforma("Tesoura Diesel", "Diesel", "Acidentado Irregular", "Plataforma Tesoura Diesel de 26 pés", 9.92, "260MRT", "JLG"),
            createPlataforma("Tesoura Diesel", "Diesel", "Acidentado Irregular", "Plataforma Tesoura Diesel de 26 pés", 9.92, "GS-2668 RT", "GENIE"),
            createPlataforma("Tesoura Diesel", "Diesel", "Acidentado Irregular", "Plataforma Tesoura Diesel de 33 pés", 11.7, "3394RT", "JLG"),
            createPlataforma("Tesoura Diesel", "Diesel", "Acidentado Irregular", "Plataforma Tesoura Diesel de 32 pés", 11.9, "SJ 6832 RT", "SKYJACK"),
            createPlataforma("Tesoura Diesel", "Diesel", "Acidentado Irregular", "Plataforma Tesoura Diesel de 33 pés", 12.06, "GS-3369 RT", "GENIE"),
            createPlataforma("Tesoura Diesel", "Diesel", "Acidentado Irregular", "Plataforma Tesoura Diesel de 33 pés", 12.06, "GS-3384 RT", "GENIE"),
            createPlataforma("Tesoura Diesel", "Diesel", "Acidentado Irregular", "Plataforma Tesoura Diesel de 33 pés", 12.06, "M3369LE", "JLG"),
            createPlataforma("Tesoura Diesel", "Diesel", "Acidentado Irregular", "Plataforma Tesoura Diesel de 40 pés", 14.0, "GS-4069 RT", "GENIE"),
            createPlataforma("Tesoura Diesel", "Diesel", "Acidentado Irregular", "Plataforma Tesoura Diesel de 42 pés", 15.1, "H 15 SX", "HAULOTTE"),
            createPlataforma("Tesoura Diesel", "Diesel", "Acidentado Irregular", "Plataforma Tesoura Diesel de 43 pés", 14.32, "4394RT", "JLG"),
            createPlataforma("Tesoura Diesel", "Diesel", "Acidentado Irregular", "Plataforma Tesoura Diesel de 43 pés", 15.11, "SJ 8841 RT", "SKYJACK"),
            createPlataforma("Tesoura Diesel", "Diesel", "Acidentado Irregular", "Plataforma Tesoura Diesel de 43 pés", 15.11, "GS-4390 RT", "GENIE"),
            createPlataforma("Tesoura Diesel", "Diesel", "Acidentado Irregular", "Plataforma Tesoura Diesel de 50 pés", 17.07, "SJ 9250 RT", "SKYJACK"),
            createPlataforma("Tesoura Diesel", "Diesel", "Acidentado Irregular", "Plataforma Tesoura Diesel de 53 pés", 18.15, "GS-5390 RT", "GENIE"),

            // Lança Articulada Elétrica (Elétrica Firme Nivelado)
            createPlataforma("Lança Articulada Elétrica", "Elétrica", "Firme Nivelado", "Plataforma de Lança Articulada Elétrica de 30 pés", 10.7, "Z-30/20N", "GENIE"),
            createPlataforma("Lança Articulada Elétrica", "Elétrica", "Firme Nivelado", "Plataforma de Lança Articulada Elétrica de 30 pés", 10.97, "E300AJP", "JLG"),
            createPlataforma("Lança Articulada Elétrica", "Elétrica", "Firme Nivelado", "Plataforma de Lança Articulada Elétrica de 30 pés", 11.19, "E300AJ", "JLG"),
            createPlataforma("Lança Articulada Elétrica", "Elétrica", "Firme Nivelado", "Plataforma de Lança Articulada Elétrica de 30 pés", 12.2, "T2-34/20", "GENIE"),
            createPlataforma("Lança Articulada Elétrica", "Elétrica", "Firme Nivelado", "Plataforma de Lança Articulada Elétrica de 34 pés", 12.2, "Z-34/22N", "GENIE"),
            createPlataforma("Lança Articulada Elétrica", "Elétrica", "Firme Nivelado", "Plataforma de Lança Articulada Elétrica de 34 pés", 12.2, "Z-34/22 DC", "GENIE"),
            createPlataforma("Lança Articulada Elétrica", "Elétrica", "Firme Nivelado", "Plataforma de Lança Articulada Elétrica de 34 pés", 12.0, "HA 12 IP", "HAULOTTE"),
            createPlataforma("Lança Articulada Elétrica", "Elétrica", "Firme Nivelado", "Plataforma de Lança Articulada Elétrica de 45 pés", 15.0, "HA 15 IP", "HAULOTTE"),
            createPlataforma("Lança Articulada Elétrica", "Elétrica", "Firme Nivelado", "Plataforma de Lança Articulada Elétrica de 45 pés", 15.72, "E450AJ", "JLG"),
            createPlataforma("Lança Articulada Elétrica", "Elétrica", "Firme Nivelado", "Plataforma de Lança Articulada Elétrica de 45 pés", 15.87, "Z-45/25 DC", "GENIE"),
            createPlataforma("Lança Articulada Elétrica", "Elétrica", "Firme Nivelado", "Plataforma de Lança Articulada Elétrica de 45 pés", 15.94, "Z-45/25J DC", "GENIE"),
            createPlataforma("Lança Articulada Elétrica", "Elétrica", "Firme Nivelado", "Plataforma de Lança Articulada Elétrica de 60 pés", 20.29, "E600J", "JLG"),
            createPlataforma("Lança Articulada Elétrica", "Elétrica", "Firme Nivelado", "Plataforma de Lança Articulada Elétrica de 60 pés", 20.4, "E600JP", "JLG"),

            // Lança Articulada Diesel (Diesel Acidentado Irregular)
            createPlataforma("Lança Articulada Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Articulada Diesel de 34 pés", 12.33, "340AJ", "JLG"),
            createPlataforma("Lança Articulada Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Articulada Diesel de 34 pés", 12.5, "Z-34/22 IC", "GENIE"),
            createPlataforma("Lança Articulada Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Articulada Diesel de 45 pés", 15.69, "Z-45/25J RT", "GENIE"),
            createPlataforma("Lança Articulada Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Articulada Diesel de 45 pés", 15.7, "450AJ", "JLG"),
            createPlataforma("Lança Articulada Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Articulada Diesel de 45 pés", 15.72, "450AJ SII", "JLG"),
            createPlataforma("Lança Articulada Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Articulada Diesel de 45 pés", 15.74, "Z-45/25 RT", "GENIE"),
            createPlataforma("Lança Articulada Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Articulada Diesel de 45 pés", 16.0, "HA 16 RTJ", "HAULOTTE"),
            createPlataforma("Lança Articulada Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Articulada Diesel de 45 pés", 16.0, "HA 16 SPX", "HAULOTTE"),
            createPlataforma("Lança Articulada Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Articulada Diesel de 52 pés", 17.3, "HA 18 SPX", "HAULOTTE"),
            createPlataforma("Lança Articulada Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Articulada Diesel de 60 pés", 20.1, "Z-60/34", "GENIE"),
            createPlataforma("Lança Articulada Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Articulada Diesel de 60 pés", 20.46, "600AJ", "JLG"),
            createPlataforma("Lança Articulada Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Articulada Diesel de 60 pés", 20.29, "M600J", "JLG"),
            createPlataforma("Lança Articulada Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Articulada Diesel de 60 pés", 20.28, "200 ATJ", "MANITOU"),
            createPlataforma("Lança Articulada Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Articulada Diesel de 62 pés", 20.7, "Z-62/40", "GENIE"),
            createPlataforma("Lança Articulada Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Articulada Diesel de 80 pés", 26.2, "Z-80/60", "GENIE"),
            createPlataforma("Lança Articulada Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Articulada Diesel de 80 pés", 26.46, "800AJ", "JLG"),
            createPlataforma("Lança Articulada Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Articulada Diesel de 80 pés", 25.6, "HA 260 PX", "HAULOTTE"),
            createPlataforma("Lança Articulada Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Articulada Diesel de 100 pés", 31.8, "HA 32 PX", "HAULOTTE"),
            createPlataforma("Lança Articulada Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Articulada Diesel de 125 pés", 40.1, "1250AJ3P", "JLG"),
            createPlataforma("Lança Articulada Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Articulada Diesel de 135 pés", 42.9, "ZX-135/70", "GENIE"),

            // Lança Articulada Híbrida
            createPlataforma("Lança Articulada Híbrida", "Híbrida", "Firme Nivelado", "Plataforma de Lança Articulada Híbrida de 45 pés", 20.16, "Z-60/37 FE", "GENIE"),
            createPlataforma("Lança Articulada Híbrida", "Híbrida", "Firme Nivelado", "Plataforma de Lança Articulada Híbrida de 60 pés", 15.92, "Z-45/25 FE", "GENIE"),

            // Lança Telescópica Diesel (Diesel Acidentado ou Irregular)
            createPlataforma("Lança Telescópica Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Telescópica Diesel de 86 pés", 28.2, "860SJ", "JLG"),
            createPlataforma("Lança Telescópica Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Telescópica Diesel de 105 pés", 33.8, "S-105", "GENIE"),
            createPlataforma("Lança Telescópica Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Telescópica Diesel de 120 pés", 38.0, "1200SJ3P", "JLG"),
            createPlataforma("Lança Telescópica Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Telescópica Diesel de 125 pés", 40.0, "S-125", "GENIE"),
            createPlataforma("Lança Telescópica Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Telescópica Diesel de 135 pés", 43.2, "1350SJ3P", "JLG"),
            createPlataforma("Lança Telescópica Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Telescópica Diesel de 150 pés", 47.72, "1500SJ", "JLG"),
            createPlataforma("Lança Telescópica Diesel", "Diesel", "Acidentado Irregular", "Plataforma de Lança Telescópica Diesel de 180 pés", 56.69, "SX-180", "GENIE")
        );
    }

    private Plataforma createPlataforma(String categoria, String tipoAlimentacao, String tipoSolo,
                                         String descricao, Double alturaTrabalho, String modelo, String fabricante) {
        return Plataforma.builder()
                .categoria(categoria)
                .tipoAlimentacao(tipoAlimentacao)
                .tipoSolo(tipoSolo)
                .descricao(descricao)
                .alturaTrabalho(alturaTrabalho)
                .modelo(modelo)
                .fabricante(fabricante)
                .build();
    }
}
