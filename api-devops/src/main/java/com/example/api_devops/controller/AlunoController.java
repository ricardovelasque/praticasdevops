package com.example.api_devops.controller;

import com.example.api_devops.model.Aluno;
import com.example.api_devops.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

        @GetMapping
    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        return alunoRepository.findById(id)
                .map(aluno -> ResponseEntity.ok().body(aluno))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Aluno criarAluno(@RequestBody Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizarAluno(@PathVariable Long id, @RequestBody Aluno alunoDetalhes) {
        return alunoRepository.findById(id)
                .map(aluno -> {
                    aluno.setNome(alunoDetalhes.getNome());
                    aluno.setEmail(alunoDetalhes.getEmail());
                    aluno.setCurso(alunoDetalhes.getCurso());
                    Aluno atualizado = alunoRepository.save(aluno);
                    return ResponseEntity.ok().body(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Aluno> atualizarParcialAluno(@PathVariable Long id, @RequestBody Aluno alunoDetalhes) {
        return alunoRepository.findById(id)
                .map(aluno -> {
                    if (alunoDetalhes.getNome() != null) {
                        aluno.setNome(alunoDetalhes.getNome());
                    }
                    if (alunoDetalhes.getEmail() != null) {
                        aluno.setEmail(alunoDetalhes.getEmail());
                    }
                    if (alunoDetalhes.getCurso() != null) {
                        aluno.setCurso(alunoDetalhes.getCurso());
                    }

                    Aluno atualizado = alunoRepository.save(aluno);
                    return ResponseEntity.ok().body(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarAluno(@PathVariable Long id) {
        return alunoRepository.findById(id)
                .map(aluno -> {
                    alunoRepository.delete(aluno);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

