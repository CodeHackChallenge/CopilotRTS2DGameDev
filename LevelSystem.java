public class LevelSystem {

    public void addExp(Entity e, long amount) {

        Level lvl = e.getComponent(Level.class);
        if (lvl == null) return;

        lvl.baseExp += amount;

        while (lvl.baseExp >= lvl.expToNext && lvl.baseLevel < XPTable.MAX_LEVEL) {

            lvl.baseExp -= lvl.expToNext;
            lvl.baseLevel++;

            lvl.expToNext = XPTable.BASE_XP[lvl.baseLevel];

            System.out.println("LEVEL UP! Now level " + lvl.baseLevel);
        }
    }
}
