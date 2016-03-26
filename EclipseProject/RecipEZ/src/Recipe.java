import java.util.List;


public class Recipe {
	private List<Ingredient> ingredients;
	private int totalCalories;
	private int servingSize;
	private Cuisine cuisine;
	private Recipe(){}
	public Recipe(List<Ingredient> ingredients, int totalCalories, int servingSize, Cuisine cuisine) {
		this.ingredients = ingredients;
		this.totalCalories = totalCalories;
		this.servingSize = servingSize;
		this.cuisine = cuisine;
	}
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	public int getCalories() {
		return totalCalories;
	}
	public int getServingSize() {
		return servingSize;
	}
	public Cuisine getCuisine() {
		return cuisine;
	}
}
