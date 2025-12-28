public enum AttackStateType {
    IDLE,
    WINDUP,
    HIT,
    RECOVERY
}

public class AttackState implements Component {
    public AttackStateType state = AttackStateType.IDLE;
    public float timer = 0f;
}
