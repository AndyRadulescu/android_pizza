package com.example.andy.vatradepizza.menuFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andy.vatradepizza.MenuPizzaActivity;
import com.example.andy.vatradepizza.R;

public class PizzaFragment extends Fragment {
    View rootView;
    LinearLayout listMenuItems;

    public PizzaFragment() {
        // Required empty public constructor
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
        for (int i = 0; i < listMenuItems.getChildCount(); i++) {
            LinearLayout item = (LinearLayout) listMenuItems.getChildAt(i);
            int imageId = Integer.parseInt(String.valueOf(item.getChildAt(0).getTag()));
            LinearLayout aux = (LinearLayout) item.getChildAt(1);
            TextView pizzaName = (TextView) aux.getChildAt(0);
            TextView pizzaDescription = (TextView) aux.getChildAt(1);
            item.setOnClickListener(v -> {
                // Perform action on click
                Toast.makeText(getContext(), imageId + pizzaName.getText().toString() + pizzaDescription.getText().toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MenuPizzaActivity.class);
                intent.putExtra("resId", imageId);
                intent.putExtra("pizzaName", pizzaName.getText().toString());
                intent.putExtra("pizzaDescription", pizzaDescription.getText().toString());
                startActivity(intent);
            });
        }
        return rootView;
    }
}
