public void spawnDamageText(Entity enemy, int damage, List<Entity> entities) {
    Position enemyPos = enemy.getComponent(Position.class);

    // Push existing damage texts upward
    for (Entity e : entities) {
        DamageTextComponent dt = e.getComponent(DamageTextComponent.class);
        if (dt != null) {
            Position p = e.getComponent(Position.class);
            p.y -= 10; // stack effect
        }
    }

    // Create new damage text
    Entity text = new Entity();
    text.addComponent(new Position(enemyPos.x + 20, enemyPos.y - 20));
    text.addComponent(new RenderTextComponent(Color.RED, 16));
    text.addComponent(new DamageTextComponent(String.valueOf(damage)));

    entities.add(text);
}
