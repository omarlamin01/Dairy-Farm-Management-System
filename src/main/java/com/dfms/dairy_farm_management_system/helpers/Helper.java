package com.dfms.dairy_farm_management_system.helpers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.function.DoubleConsumer;

public class Helper {
    public static void centerScreen(Stage stage) {
        Screen screen = Screen.getPrimary();
        Rectangle2D sbounds = screen.getBounds();

        double sw = sbounds.getWidth();
        double sh = sbounds.getHeight();

        listenToSizeInitialization(stage.widthProperty(),
                w -> stage.setX((sw - w) / 2));
        listenToSizeInitialization(stage.heightProperty(),
                h -> stage.setY((sh - h) / 2));
    }

    public static void listenToSizeInitialization(ObservableDoubleValue size, DoubleConsumer handler) {
        ChangeListener<Number> listener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> obs, Number oldSize, Number newSize) {
                if (newSize.doubleValue() != Double.NaN) {
                    handler.accept(newSize.doubleValue());
                    size.removeListener(this);
                }
            }
        };
        size.addListener(listener);
    }
}
