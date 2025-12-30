package demo.main;

public class AttackProfile implements Component {
    public float windup;
    public float hitWindow;
    public float recovery;

    public AttackProfile(float windup, float hitWindow, float recovery) {
        this.windup = windup;
        this.hitWindow = hitWindow;
        this.recovery = recovery;
    }
}