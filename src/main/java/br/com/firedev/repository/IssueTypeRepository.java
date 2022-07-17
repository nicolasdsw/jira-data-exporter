package br.com.firedev.repository;

import org.springframework.stereotype.Repository;

import br.com.firedev.model.IssueType;
import br.com.firedev.util.BaseRepository;

@Repository
public interface IssueTypeRepository extends BaseRepository<IssueType, Long> {

}