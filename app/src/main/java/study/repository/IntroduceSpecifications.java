package study.repository;

import javax.persistence.criteria.Predicate;

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
	 * キーワード検索(コミュニティ毎)
	 * @param keyword キーワード
	 * @param communityId コミュニティID
	 * @return
	 */
	public static Specification<IntroduceEntity> findByKeyWord(String keyword, int communityId) {
				
		return (root, query, criteriaBuilder) -> {
			
			root.join("communityEntity");
			
			Predicate orCondition = criteriaBuilder.or(
					keyword != null ? criteriaBuilder.like(root.get("oneMessage"), "%"+ keyword + "%") : criteriaBuilder.conjunction(),
					keyword != null ? criteriaBuilder.like(root.get("myField"), "%"+ keyword + "%") : criteriaBuilder.conjunction(),
					keyword != null ? criteriaBuilder.like(root.get("language"), "%"+ keyword + "%") : criteriaBuilder.conjunction(),
					keyword != null ? criteriaBuilder.like(root.get("job"), "%"+ keyword + "%") : criteriaBuilder.conjunction()
				);

			Predicate andConditon = criteriaBuilder.and(
						criteriaBuilder.equal(root.get("communityEntity").get("id"), communityId)
					);
		
			return criteriaBuilder.and(orCondition, andConditon);
		};
	}
	
}