package br.com.cursoudemy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cursoudemy.domain.Endereco;


@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

}
