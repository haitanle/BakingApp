package com.example.bakingapp.data;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.util.JsonReader;
import android.util.JsonToken;
import android.widget.Toast;

import com.example.bakingapp.R;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSourceInputStream;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private int id;
    private String name;
    private List<Ingredients> ingredientsList;
    private List<Steps> stepsList;

    public Recipe(int id, String name, List<Ingredients> ingredientsList, List<Steps> stepsList) {
        this.id = id;
        this.name = name;
        this.ingredientsList = ingredientsList;
        this.stepsList = stepsList;
    }

    /**
     * Gets and ArrayList of the IDs for all of the Recipes from the JSON file.
     * @param context The application context.
     * @return The ArrayList of all recipe IDs.
     */
    public static ArrayList<Recipe> getAllRecipeIDs(Context context){
        JsonReader reader;
        ArrayList<Recipe> recipeIDs = new ArrayList<>();

        try {
            reader = readJSONFile(context);
            reader.beginArray();
            while (reader.hasNext()) {
                recipeIDs.add(readEntry(reader));
            }
            reader.endArray();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipeIDs;
    }

    /**
     * Method used for obtaining a single recipe from the JSON file.
     * @param reader The JSON reader object pointing a single recipe JSON object.
     * @return The Recipe the JsonReader is pointing to.
     */
    private static Recipe readEntry(JsonReader reader) {
        Integer id = -1;
        String name = null;
        List<Ingredients> ingredientsList = null;
        List<Steps> stepsList = null;

        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case "name":
                        name = reader.nextString();
                        break;
                    case "id":
                        id = reader.nextInt();
                        break;
                    case "ingredients":
                        if (reader.peek() != JsonToken.NULL){
                            ingredientsList = readIngredientsArray(reader);
                        }
                        break;
                    case "steps":
                        if (reader.peek() != JsonToken.NULL){
                            stepsList = readStepsArray(reader);
                        }
                        break;
                    default:
                        reader.skipValue();
                }
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Recipe(id, name, ingredientsList, stepsList);
    }

    /**
     * Return a list of ingredientsList from the JSON
     * @param reader
     * @return
     * @throws IOException
     */
    private static List<Ingredients> readIngredientsArray(JsonReader reader) throws IOException{
        List<Ingredients> ingredientsList = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()){
            ingredientsList.add(readIngredientsEntry(reader));
        }
        reader.endArray();
        return ingredientsList;
    }

    /**
     * Read Ingredient object from JSON
     * @param reader
     * @return
     * @throws IOException
     */
    private static Ingredients readIngredientsEntry(JsonReader reader) throws IOException{
        double quantity = -1;
        String measure = null;
        String ingredient = null;

        reader.beginObject();
        while (reader.hasNext()){
            String name = reader.nextName();
            if (name.equals("quantity")) {
                quantity = reader.nextDouble();
            } else if (name.equals("measure")) {
                measure = reader.nextString();
            } else if (name.equals("ingredient")) {
                ingredient = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Ingredients(quantity, measure, ingredient);
    }


    /**
     * Return a list of stepsList from the JSON
     * @param reader
     * @return
     * @throws IOException
     */
    private static List<Steps> readStepsArray(JsonReader reader) throws IOException{
        List<Steps> stepsList = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()){
            stepsList.add(readStepsEntry(reader));
        }
        reader.endArray();
        return stepsList;
    }

    /**
     * Read Steps object from JSON
     * @param reader
     * @return
     * @throws IOException
     */
    private static Steps readStepsEntry(JsonReader reader) throws IOException{
        int id = -1;
        String shortDescription = null;
        String description = null;
        String videoURL = null;
        String thumbnailURL = null;

        reader.beginObject();
        while (reader.hasNext()){
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextInt();
            } else if (name.equals("shortDescription")) {
                shortDescription = reader.nextString();
            } else if (name.equals("description")) {
                description = reader.nextString();
            } else if (name.equals("videoURL")) {
                videoURL = reader.nextString();
            } else if (name.equals("thumbnailURL")) {
                thumbnailURL = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Steps(id, shortDescription, description, videoURL, thumbnailURL);
    }

    /**
     * Method for creating a JsonReader object that points to the JSON array of recipes.
     * @param context The application context.
     * @return The JsonReader object pointing to the JSON array of recipes.
     * @throws IOException Exception thrown if the recipe file can't be found.
     */
    private static JsonReader readJSONFile(Context context) throws IOException {
        AssetManager assetManager = context.getAssets();
        String uri = null;

//        try {
//            for (String asset : assetManager.list("")) {
//                if (asset.endsWith("baking.json")) {
//                    uri = "asset:///" + asset;
//                }
//            }
//        } catch (IOException e) {
//            Toast.makeText(context, R.string.recipe_list_load_error, Toast.LENGTH_LONG)
//                    .show();
//        }
//
        String userAgent = Util.getUserAgent(context, "BakingApp");
        DataSource dataSource = new DefaultDataSource(context, null, userAgent, false);
//        DataSpec dataSpec = new DataSpec(Uri.parse(uri));
//        InputStream inputStream = new DataSourceInputStream(dataSource, dataSpec);

        InputStream inputStream = assetManager.open("baking.json");

        JsonReader reader = null;
        try {
            reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        } finally {
            Util.closeQuietly(dataSource);
        }

        return reader;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredients> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<Ingredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public List<Steps> getStepsList() {
        return stepsList;
    }

    public void setStepsList(List<Steps> stepsList) {
        this.stepsList = stepsList;
    }
}
