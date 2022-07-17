package br.com.firedev.repository;

import org.springframework.stereotype.Repository;

import br.com.firedev.model.Project;
import br.com.firedev.util.BaseRepository;

@Repository
public interface ProjectRepository extends BaseRepository<Project, Long> {

}