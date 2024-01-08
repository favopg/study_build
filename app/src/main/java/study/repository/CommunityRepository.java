package study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import study.entity.CommunityEntity;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityEntity, Integer>{

	public CommunityEntity findByName(String communityName);
}
