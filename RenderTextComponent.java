public class RenderTextComponent implements Component {
    public Color color;
    public int size;

    public RenderTextComponent(Color color, int size) {
        this.color = new Color(color); // copy
        this.size = size;
    }
}
