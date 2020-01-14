package GUI;

import java.awt.*;

public class ScaleHelper {
    public static double CalculateScalingFactor(Component c) {
        double size = Math.min(c.getWidth(), c.getHeight());
        // TODO: differenciate between width and height.
        double prefSize = size == c.getWidth() ? c.getPreferredSize().width : c.getPreferredSize().height;

        return size/prefSize;
    }
}
