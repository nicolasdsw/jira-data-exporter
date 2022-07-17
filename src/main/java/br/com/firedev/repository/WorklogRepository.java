package br.com.firedev.repository;

import org.springframework.stereotype.Repository;

import br.com.firedev.model.Worklog;
import br.com.firedev.util.BaseRepository;

@Repository
public interface WorklogRepository extends BaseRepository<Worklog, Long> {

}