import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ShoppingList {
	private HashMap<Ingredient,Double> costOfIngredients;
	public List<Ingredient> getIngredientsList(){
		ArrayList<Ingredient> ingredientList = new ArrayList<>();
		for(Ingredient ingredient: costOfIngredients.keySet())
		{
			ingredientList.add(ingredient);
		}
		return ingredientList;
	}
	public double calculateTotalCost(){
		double totalCost = 0;
		for(Double cost : costOfIngredients.values())
		{
			totalCost += cost.doubleValue();
		}
		return totalCost;
	}
}
