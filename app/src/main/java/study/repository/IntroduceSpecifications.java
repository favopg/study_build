package study.repository;

import org.springframework.data.jpa.domain.Specification;

import study.entity.IntroduceEntity;

/**
 * 自己紹介テーブルの条件付与クラス
 * jpaの基本操作では対応できないSQLを実装
 * @author イッシー
 *
 */
public class IntroduceSpecifications {

	
	/**
	 * キーワード検索
	 * @param oneMessage
	 * @return
	 */
	public static Specification<IntroduceEntity> findByKeyWord(String keyword) {
				
		return (root, query, criteriaBuilder) -> {
		return criteriaBuilder.or(
					keyword != null ? criteriaBuilder.like(root.get("oneMessage"), "%"+ keyword + "%") : criteriaBuilder.conjunction(),
				    keyword != null ? criteriaBuilder.equal(root.get("job"), keyword) : criteriaBuilder.conjunction()
				);
		};		
	}
	
}