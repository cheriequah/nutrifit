package com.example.nutrifit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Food> {

    private final List<Food> foodList;

    private final Context mContext;

    private TextView mTextViewFoodName, mTextViewCalories;

    public ListViewAdapter(List<Food> foodList, Context mContext) {
        super(mContext, R.layout.food_list, foodList);
        this.foodList = foodList;
        this.mContext = mContext;
    }

    //this method will return the list item
    public View getView(int position, View convertView, ViewGroup parent) {

        //getting the layout inflator
        LayoutInflater inflater = LayoutInflater.from(mContext);

        //creating a view with our xml layout
        View listViewFoodItem = inflater.inflate(R.layout.food_list, null, true);

        mTextViewFoodName = listViewFoodItem.findViewById(R.id.food_text_view);
        mTextViewCalories = listViewFoodItem.findViewById(R.id.calories_text_view);

        //Getting the food for the specified position
        Food food = foodList.get(position);

        //setting food values to textviews
        mTextViewFoodName.setText(food.getFoodName());
        mTextViewCalories.setText(food.getCalories());

        //returning the food list item
        return listViewFoodItem;
    }
}
