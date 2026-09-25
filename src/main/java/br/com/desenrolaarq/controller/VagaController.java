package br.com.desenrolaarq.controller;

import br.com.desenrolaarq.entity.Vaga;
import br.com.desenrolaarq.service.VagaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vagas")
public class VagaController {

    private final VagaService vagaService;

    public VagaController(VagaService vagaService) {
        this.vagaService = vagaService;
    }

    @GetMapping
    public List<Vaga> listarTodas() {
        return vagaService.listarTodas();
    }

    @GetMapping("/{id}")
    public Vaga buscarPorId(@PathVariable Long id) {
        return vagaService.buscarPorId(id);
    }

    @PostMapping
    public Vaga criar(@RequestBody Vaga vaga) {
        return vagaService.criar(vaga);
    }

    @PutMapping("/{id}")
    public Vaga atualizar(@PathVariable Long id, @RequestBody Vaga vaga) {
        return vagaService.atualizar(id, vaga);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        vagaService.excluir(id);
    }
}