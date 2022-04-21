import java.util.*;
import java.io.*;

public class DrugInteractions
{
	public static void main( String[] args ) throws Exception
	{
		BufferedReader foodDrug2CategoryFile = new BufferedReader( new FileReader( "foodDrug2Category.txt" ) );
		BufferedReader patient2FoodDrugFile = new BufferedReader( new FileReader( "patient2FoodDrug.txt") );
		BufferedReader dontMixFile = new BufferedReader( new FileReader( "dontMix.txt" ) );
		// step 1
		TreeMap<String, TreeSet<String>> foodDrug2CategoryMap = new TreeMap<String, TreeSet<String>>();
		foodDrug2CategoryMap = loadMap(foodDrug2CategoryFile);
		printMap(foodDrug2CategoryMap);
		System.out.println();

		// step 2
		TreeMap<String, TreeSet<String>> patient2FoodDrugMap = new TreeMap<String, TreeSet<String>>();
		patient2FoodDrugMap = loadMap(patient2FoodDrugFile);
		printMap(patient2FoodDrugMap);
		System.out.println();

		TreeSet<String> namesList = new TreeSet<String>();

		// step 3
		while ( dontMixFile.ready())
		{
			String line = dontMixFile.readLine();
			String[] tokens = line.split("\\,"); // "THINNERS" "ANTI_COAG"
			TreeSet<String> dontMixFoodsList = new TreeSet<String>(foodDrug2CategoryMap.get(tokens[0])); // list of food that cant be mixed
			for ( String food: foodDrug2CategoryMap.get(tokens[1]))
				dontMixFoodsList.add(food);

			TreeSet<String> intersection = new TreeSet<String>();
			for ( String name : patient2FoodDrugMap.keySet() )
			{
				intersection = intersection(dontMixFoodsList,patient2FoodDrugMap.get(name)); // finding intersection of foods not to be mixed
				if (intersection.size() >=2 )																								 // and the persons food he eats
					namesList.add(name);
			}
		}
		for ( String name: namesList )
			System.out.println(name);


	} // END MAIN

	public static TreeMap<String,TreeSet<String>> loadMap( BufferedReader file ) throws Exception
	{
		TreeMap<String, TreeSet<String>> foodDrug2Category = new TreeMap<String, TreeSet<String>>();
		while (file.ready())
		{
			String line = file.readLine();
			String[] tokens = line.split("\\,");
			String drugType = tokens[0];

			for ( int i = 1; i < tokens.length; i++)
			{
				String food = tokens[i];
				if ( !foodDrug2Category.containsKey(drugType))
					foodDrug2Category.put(drugType, new TreeSet<String>());
				foodDrug2Category.get(drugType).add(food);
				foodDrug2Category.put(drugType, foodDrug2Category.get(drugType));
			}
		}
		return foodDrug2Category;
	}
	public static void printMap(TreeMap<String, TreeSet<String>> map )
	{
		for ( String key: map.keySet() )
		{
			System.out.print(key);
			for ( String value: map.get(key))
				System.out.print(" " + value );
			System.out.println();
		}
	}
	static TreeSet<String> intersection( TreeSet<String> set1, TreeSet<String> set2 )// only adds elems that are in both
	{
		TreeSet<String> set3 = new TreeSet<String>();
		set3.addAll( set1 );
		set3.retainAll(set2);
		return set3;
	}

} // END CLASS
