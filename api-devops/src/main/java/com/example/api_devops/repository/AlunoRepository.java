package com.example.api_devops.repository;

import com.example.api_devops.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository  extends JpaRepository<Aluno, Long> {

}
