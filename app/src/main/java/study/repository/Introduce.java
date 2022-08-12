package study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import study.dao.IntroduceEntity;

@Repository
public interface Introduce extends JpaRepository<IntroduceEntity, String>{

}
