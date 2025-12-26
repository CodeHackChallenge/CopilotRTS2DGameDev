package demo.main;

import java.awt.Color;

public class RenderTextComponent implements Component {
    public Color color;
    public int size;
    
    public int shadowOffsetX = 1;
    public int shadowOffsetY = 1;
    

    public RenderTextComponent(Color color, int size) {
        this.color = color;
        this.size = size;
    }
}