package com.app.recipe.data;

import com.app.recipe.entities.RecipeEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;

public class RecipeSearchPredicate {
	private RecipeSearchCriteria criteria;
	
	public RecipeSearchPredicate(final RecipeSearchCriteria criteria) {
        this.criteria = criteria;
    }

	public BooleanExpression getPredicate() {
		PathBuilder<RecipeEntity> entityPath = new PathBuilder<>(RecipeEntity.class, "recipeEntity");

		if (isNumeric(criteria.getValue().toString())) {
			NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
			int value = Integer.parseInt(criteria.getValue().toString());
			switch (criteria.getOperation()) {
			case ":":
				return path.eq(value);
			case ">":
				return path.goe(value);
			case "<":
				return path.loe(value);
			}
		} else {
			StringPath path = entityPath.getString(criteria.getKey());
			if (criteria.getOperation().equalsIgnoreCase(":")) {
				return path.containsIgnoreCase(criteria.getValue().toString());
			}
		}
		return null;
	}

	public static boolean isNumeric(final String str) {
		try {
			Integer.parseInt(str);
		} catch (final NumberFormatException e) {
			return false;
		}
		return true;
	}

}
