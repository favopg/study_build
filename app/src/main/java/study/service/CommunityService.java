package study.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import study.entity.CommunityEntity;
import study.repository.CommunityRepository;

/**
 * コミュニティサービスクラス
 * @author イッシー
 *
 */
@Service
public class CommunityService {
	
	@Autowired
	private CommunityRepository communityRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * コミュニティ情報登録
	 * @param communityName コミュニティ名
	 * @param secret 合言葉
	 */
	public void register(String communityName, String secret) {

		CommunityEntity communityEntity = new CommunityEntity();
		communityEntity.setName(communityName);
		communityEntity.setSecret(passwordEncoder.encode(secret));
		
		Example<CommunityEntity> searchEntity = Example.of(communityEntity);

		if (communityRepository.exists(searchEntity)) {
			throw new RuntimeException("すでに登録済だよ");
		}
						
		// コミュニティテーブル作成
		communityRepository.save(communityEntity);
		
	}
	
	/**
	 * コミュニティ情報リスト取得
	 * @return コミュニティ情報リスト
	 */
	public List<CommunityEntity> getCommunityList() {
		
		return communityRepository.findAll();
	}

}
