package demo.main;

import java.awt.Color;
import java.awt.Font;

public class RenderTextComponent implements Component {
    public Color color;
    public int size;
    public Font font;
    
    public int shadowOffsetX = 1;
    public int shadowOffsetY = 1;
    

    public RenderTextComponent(Color color, int size) {
        this.color = color;
        this.size = size;
        this.font = new Font("Arial", Font.BOLD, size);
    }
}