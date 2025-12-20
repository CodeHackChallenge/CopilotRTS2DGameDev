public class DamageTextComponent implements Component {
    public String text;
    public float alpha = 1f;
    public float lifetime = 1.0f; // seconds
    public float elapsed = 0f;
    public float upwardSpeed = 20f; // pixels per second

    public DamageTextComponent(String text) {
        this.text = text;
    }
}
