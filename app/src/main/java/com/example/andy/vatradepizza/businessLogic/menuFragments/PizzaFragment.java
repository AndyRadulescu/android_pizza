package com.example.andy.vatradepizza.businessLogic.menuFragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.andy.vatradepizza.MenuPizzaActivity;
import com.example.andy.vatradepizza.R;

import java.util.ArrayList;
import java.util.Arrays;

public class PizzaFragment extends Fragment {
    View rootView;
    LinearLayout listMenuItems;
    ArrayList<MenuItem> menuItemsArray;

    public PizzaFragment() {
        // Required empty public constructor
        this.menuItemsArray = new ArrayList<>(Arrays.asList(MenuItemsGenerator.menuItemsGenerator()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_pizza, container, false);
        listMenuItems = rootView.findViewById(R.id.list_menu_items);
        createTheLayoutProgrammatically(listMenuItems);

        for (int i = 0; i < listMenuItems.getChildCount(); i++) {
            LinearLayout item = (LinearLayout) listMenuItems.getChildAt(i);
            int imageId = item.getChildAt(0).getId();
            LinearLayout aux = (LinearLayout) item.getChildAt(1);
            TextView pizzaName = (TextView) aux.getChildAt(0);
            TextView pizzaDescription = (TextView) aux.getChildAt(1);
            LinearLayout aux2 = (LinearLayout) item.getChildAt(2);
            TextView pizzaPrice = (TextView) aux2.getChildAt(0);

            item.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), MenuPizzaActivity.class);
                intent.putExtra("resId", imageId);
                intent.putExtra("pizzaName", pizzaName.getText().toString());
                intent.putExtra("pizzaDescription", pizzaDescription.getText().toString());
                intent.putExtra("pizzaPrice", pizzaPrice.getText().toString());
                startActivity(intent);
            });
        }
        return rootView;
    }


    private void createTheLayoutProgrammatically(LinearLayout parent) {
        for (MenuItem menuItem : menuItemsArray) {
            LinearLayout item = new LinearLayout(getContext());
            LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.pizza_menu_item));
            params0.setMargins(0, (int) getResources().getDimension(R.dimen.margin_top_pizza_menu_item), 0, 0);
            item.setLayoutParams(params0);
            item.setOrientation(LinearLayout.HORIZONTAL);
            item.setGravity(Gravity.CENTER);
            item.setBackgroundResource(R.drawable.list_item_shadow);

            ImageView pizzaImage = new ImageView(getContext());
            pizzaImage.setLayoutParams(new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.image_size), (int) getResources().getDimension(R.dimen.image_size)));
            pizzaImage.setContentDescription(menuItem.getPizzaName());
            pizzaImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            pizzaImage.setImageResource(menuItem.getImageId());
            pizzaImage.setId(menuItem.getImageId());

            LinearLayout itemInfoLayout = new LinearLayout(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.info_item_width), ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins((int) getResources().getDimension(R.dimen.margin_start_pizza_menu_item), 0, 0, 0);
            itemInfoLayout.setLayoutParams(params);
            itemInfoLayout.setOrientation(LinearLayout.VERTICAL);
            itemInfoLayout.setGravity(Gravity.CENTER);

            TextView pizzaName = new TextView(getContext());
            pizzaName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            pizzaName.setText(menuItem.getPizzaName());
            pizzaName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            pizzaName.setTypeface(pizzaName.getTypeface(), Typeface.BOLD);

            TextView pizzaDescription = new TextView(getContext());
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params2.setMargins(0, (int) getResources().getDimension(R.dimen.info_item_margin_top), 0, 0);
            pizzaDescription.setLayoutParams(params2);
            pizzaDescription.setText(menuItem.getPizzaInfo());
            pizzaDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            pizzaDescription.setTypeface(pizzaName.getTypeface(), Typeface.ITALIC);

            LinearLayout priceLayout = new LinearLayout(getContext());
            priceLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            priceLayout.setGravity(Gravity.CENTER);
            priceLayout.setOrientation(LinearLayout.VERTICAL);

            TextView price = new TextView(getContext());
            price.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            price.setText(menuItem.getPrice());
            price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            price.setGravity(Gravity.CENTER);
            price.setTypeface(pizzaName.getTypeface(), Typeface.ITALIC);

            priceLayout.addView(price);

            itemInfoLayout.addView(pizzaName);
            itemInfoLayout.addView(pizzaDescription);

            item.addView(pizzaImage);
            item.addView(itemInfoLayout);
            item.addView(priceLayout);

            parent.addView(item);
        }
    }
}
