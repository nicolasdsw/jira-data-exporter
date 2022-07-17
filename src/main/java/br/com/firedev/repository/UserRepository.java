package br.com.firedev.repository;

import org.springframework.stereotype.Repository;

import br.com.firedev.model.User;
import br.com.firedev.util.BaseRepository;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {

}