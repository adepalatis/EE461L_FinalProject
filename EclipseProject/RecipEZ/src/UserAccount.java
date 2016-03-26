import java.util.ArrayList;
import java.util.List;


public class UserAccount {
	private String username;
	private String password;
	private String nickname;
	List<Recipe> savedRecipes;
	List<Ingredient> pantry;
	
	private UserAccount(){}
	public UserAccount(String username, String password, String nickname)
	{
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		savedRecipes = new ArrayList<Recipe>();
		pantry = new ArrayList<Ingredient>();
	}
	public String getUsername() {
		return username;
	}
	public String getNickname() {
		return nickname;
	}
	public List<Recipe> getSavedRecipes() {
		return savedRecipes;
	}
	public boolean correctPassword(String password)
	{
		return this.password.equals(password);
	}
	
}
