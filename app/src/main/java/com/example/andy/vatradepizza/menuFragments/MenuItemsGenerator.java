package com.example.andy.vatradepizza.menuFragments;

import com.example.andy.vatradepizza.R;

public class MenuItemsGenerator {
    private static MenuItem[] arrayMenuItem;

    public static MenuItem[] menuItemsGenerator() {
        return new MenuItem[]{

                new MenuItem(R.drawable.pizza_bambino,
                        "Pizza Bambino", "blat pizza, pomodoro, mozarella, piept de pui, cartofi prajiti ", "23.00 lei"),

                new MenuItem(R.drawable.pizza_california,
                        "Pizza California", "blat pizza, pomodoro, mozarella, sunca, ciuperci, porumb ", "23.00 lei"),

                new MenuItem(R.drawable.pizza_cezar,
                        "Pizza Cezar", "blat pizza, pomodoro, mozarella, bacon, piept de pui, porumb, masline ", "23.00 lei"),

                new MenuItem(R.drawable.pizza_dejun,
                        "Pizza Dejun", "blat pizza, smantana, ou, bacon ", "23.00 lei"),

                new MenuItem(R.drawable.pizza_gorgonzola,
                        "Pizza Pizza Gorgonzola con Rucola", "blat pizza, pomodoro, mozarella, gorgonzola, rosii cherry, rucola, bilute de mozzarella ", "28.00 lei"),
                new MenuItem(R.drawable.pizza_hawaii,
                        "Pizza Hawaii", "blat pizza, pomodoro, mozarella, piept de pui, ananas ", "23.00 lei"),
                new MenuItem(R.drawable.pizza_prosciutto_crudo,
                        "Pizza Prosciutto Crudo", "Blat pizza, pomodoro, mozarella, prosciutto crudo, rucola, parmezan ", "28.00 lei")
        };
    }
}
