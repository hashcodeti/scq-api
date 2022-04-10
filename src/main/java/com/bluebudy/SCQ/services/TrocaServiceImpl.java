package com.bluebudy.SCQ.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.bluebudy.SCQ.domain.Controle;
import com.bluebudy.SCQ.domain.Etapa;
import com.bluebudy.SCQ.domain.OrdemDeManutencao;
import com.bluebudy.SCQ.domain.Troca;
import com.bluebudy.SCQ.dtos.TrocaDTO;
import com.bluebudy.SCQ.forms.TrocaForm;
import com.bluebudy.SCQ.repository.EtapaRepository;
import com.bluebudy.SCQ.repository.OmpRepository;
import com.bluebudy.SCQ.repository.TrocaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrocaServiceImpl implements TrocaService {

    @Autowired
    private TrocaRepository repository;
    @Autowired
    private EtapaRepository etapaRepository;
    @Autowired
    private OmpRepository ompRepo;
    @Autowired
    private ControleService controleService;
    @Autowired
    private NotificationService notificationService;

    @Override
    public TrocaDTO newTroca(TrocaForm trocaForm) {
        Controle controle = new Controle();
        try {
            controle = controleService.buildControleService(trocaForm);
        } catch (IllegalArgumentException | IllegalAccessException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        Troca newTroca = trocaForm.generateTroca();
        newTroca.setControle(controle);
        Troca troca = new Troca();
        troca = repository.saveAndFlush(newTroca);
        notificationService.updadteUserNotificationUi(List.of("loadTrocas","loadEtapas"));
        return new TrocaDTO(troca, etapaRepository);

    }

    @Override
    public List<TrocaDTO> findAll() {
        List<Troca> trocas = repository.findAll();
        List<Long> idTrocasProgramas = new ArrayList<Long>();
        List<OrdemDeManutencao> ordens = ompRepo.findByRealizadoEm(null);
        ordens.stream().forEach(ordem -> {
            if (ordem.getTrocasId() != null) {
                String trocasId = ordem.getTrocasId();
                String[] trocasTokens = trocasId.split(",");
                List<String> ids = Arrays.asList(trocasTokens);
                ids.stream().forEach(id -> {
                    String[] idTroca = id.split(":");
                    if (!idTrocasProgramas.contains(Long.valueOf(idTroca[0]))) {
                        idTrocasProgramas.add(Long.valueOf(idTroca[0]));

                    }

                });
            }

        });

        List<TrocaDTO> trocasDto = TrocaDTO.trocaToTrocaDTO(trocas);
        trocasDto.forEach(trocaDto -> {
            if (idTrocasProgramas.contains(trocaDto.getId())) {
                trocaDto.setProgramada(true);
            }
        });
        return trocasDto.stream().sorted().collect(Collectors.toList());
    }

    @Override
    public List<TrocaDTO> findByProcessoId(Long parametroId) {
        return TrocaDTO.trocaToTrocaDTO(repository.findTrocaByProcesso(parametroId));
    }

    @Override
    public TrocaDTO findByEtapaId(Long etapaId) {
        return new TrocaDTO(repository.findTrocaByEtapa(etapaId));
    }

    @Override
    public TrocaDTO updateTroca(TrocaForm trocaForm, Long trocaId) {
        Troca troca = repository.findById(trocaId).get();
        Etapa etapa = new Etapa();
        etapa.setId(trocaForm.getEtapaId());
        troca.setEtapa(etapa);

        try {
            controleService.updateControle(trocaForm, troca.getControle());
        } catch (IllegalArgumentException | IllegalAccessException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        notificationService.updadteUserNotificationUi(List.of("loadTrocas","loadEtapas"));
        return new TrocaDTO(repository.save(troca), "update");
    }

    @Override
    public void deleteTroca(Long trocaId) {
        repository.deleteById(trocaId);
        notificationService.updadteUserNotificationUi(List.of("loadTrocas","loadEtapas","loadOcps"));
    }

    






   


    
}
