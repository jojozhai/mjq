/**
 * 
 */
package com.ymt.mjq.repository.spec;

import com.ymt.mjq.domain.Article;
import com.ymt.mjq.dto.ArticleInfo;
import com.ymt.pz365.data.jpa.repository.spec.PzSimpleSpecification;
import com.ymt.pz365.data.jpa.repository.spec.QueryWraper;

/**
 * @author zhailiang
 *
 */
public class ArticleSpec extends PzSimpleSpecification<Article, ArticleInfo> {

	public ArticleSpec(ArticleInfo condition) {
		super(condition);
	}

	@Override
	protected void addCondition(QueryWraper<Article> queryWraper) {
		addEqualsCondition(queryWraper, "type");
		addLikeCondition(queryWraper, "title");
	}

}
