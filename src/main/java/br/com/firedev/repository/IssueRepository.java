package br.com.firedev.repository;

import org.springframework.stereotype.Repository;

import br.com.firedev.model.Issue;
import br.com.firedev.util.BaseRepository;

@Repository
public interface IssueRepository extends BaseRepository<Issue, Long> {

}