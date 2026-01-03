public class XPTable {

    public static final int MAX_LEVEL = 99;
    public static final long[] BASE_XP = new long[MAX_LEVEL + 1];

    static {
        for (int level = 1; level <= MAX_LEVEL; level++) {
            BASE_XP[level] = computeXP(level);
        }
    }

    private static long computeXP(int level) {
        return (long)(120 * Math.pow(level, 2.7));
    }
}
