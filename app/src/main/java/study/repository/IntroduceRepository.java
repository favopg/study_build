package study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import study.entity.IntroduceEntity;

@Repository
public interface IntroduceRepository extends JpaRepository<IntroduceEntity, Integer>, JpaSpecificationExecutor<IntroduceEntity>{
	
}
