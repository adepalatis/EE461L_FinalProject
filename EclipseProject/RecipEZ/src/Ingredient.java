
public class Ingredient {
	private String originalName;
	private String name;
	private int amount;
	private String unitShort;
	private String unitLong;
	private String aisle;
	private String imageLink;
	private Ingredient(){}
	public Ingredient(String originalName, String name, int amount, String unitShort, String unitLong, String aisle, String imageLink)
	{
		this.originalName = originalName;
		this.name = name;
		this.amount = amount;
		this.unitShort = unitShort;
		this.unitLong = unitLong;
		this.aisle = aisle;
		this.imageLink = imageLink;
	}
	public String getOriginalName() {
		return originalName;
	}
	public String getName() {
		return name;
	}
	public int getAmount() {
		return amount;
	}
	public String getUnitShort() {
		return unitShort;
	}
	public String getUnitLong() {
		return unitLong;
	}
	public String getAisle() {
		return aisle;
	}
	public String getImageLink() {
		return imageLink;
	}

}
