import org.apache.http.HttpResponse;


public class Client {

	public static void main(String args[]) {
		UserAccount ramon = new UserAccount("ramonv23", "1234", "ramon");
		//(String name, boolean meat, boolean dairy, boolean gluten, Cuisine cuisine)
		PantryIngredient i1 = new PantryIngredient("2 oz top sirloin");
		PantryIngredient i2 = new PantryIngredient("1 pound rice");
		PantryIngredient i3 = new PantryIngredient("1 pound beans");
		// These code snippets use an open-source library.
		HttpResponse<JsonNode> response = Unirest.post("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/parseIngredients")
		.header("X-Mashape-Key", "splYpiM6ukmshBq11FF1vCWMOxcQp1kD1ssjsnNa56Dn5KkgfA")
		.header("Content-Type", "application/x-www-form-urlencoded")
		.field("ingredientList", "rice")
		.field("servings", 1)
		.asJson();
	}
}
