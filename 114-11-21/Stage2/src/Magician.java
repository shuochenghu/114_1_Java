public class Magician extends Role {
    // 治癒力
    private int healPower;

    // 建構子：初始化魔法師的名稱、生命值和攻擊力
    public Magician(String name, int health, int attackPower, int healPower) {
        super(name, health, attackPower);
        this.healPower = healPower;
    }

    // 取得治癒力
    public int getHealPower() {
        return healPower;
    }

    // 攻擊對手
    @Override
    public void attack(Role opponent) {
        System.out.println("✨ " + this.getName() + " 施放魔法攻擊 " + opponent.getName() + "！");
        opponent.takeDamage(this.getAttackPower()); // 使用 takeDamage 方法
    }

    // 治療隊友
    public void heal(Role ally) {
        int oldHealth = ally.getHealth();
        ally.setHealth(ally.getHealth() + this.healPower);
        System.out.println("💚 " + this.getName() + " 治療 " + ally.getName() + 
                         " 回復 " + healPower + " 點生命值。" + 
                         "(" + oldHealth + " → " + ally.getHealth() + ")");
    }

    // 展示特殊技能
    @Override
    public void showSpecialSkill() {
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║ " + this.getName() + " 的特殊技能        ║");
        System.out.println("╠═════════════════════════════╣");
        System.out.println("║ 技能名稱：元素爆發          ║");
        System.out.println("║ 技能描述：召喚強大魔法攻擊  ║");
        System.out.println("║ 技能效果：範圍魔法傷害      ║");
        System.out.println("║ 額外效果：恢復自身魔力      ║");
        System.out.println("╚═════════════════════════════╝");
    }

    // ========== 第二階段新增：實作死亡和戰鬥相關抽象方法 ==========
    
    /**
     * 魔法師的死亡效果
     * 魔法師死亡時，身體會化為魔法粒子消散
     */
    @Override
    public void onDeath() {
        System.out.println("💀 " + this.getName() + " 的生命之火熄滅了...");
        System.out.println("✨ " + this.getName() + " 的身體化為無數魔法粒子，消散在空氣中。");
        System.out.println("🌟 魔法書掉落在地上，微微發光。");
        System.out.println("---");
    }

    /**
     * 魔法師的戰前準備
     * 吟唱咒語，準備施法
     */
    @Override
    public void prepareBattle() {
        System.out.println("📖 " + this.getName() + " 翻開魔法書，開始吟唱古老的咒語...");
        System.out.println("✨ 魔法能量在周圍凝聚，空氣中閃爍著神秘的光芒。");
    }

    /**
     * 魔法師的戰後行為
     * 冥想恢復魔力
     */
    @Override
    public void afterBattle() {
        System.out.println("🧘 " + this.getName() + " 閉目冥想，恢復消耗的魔力。");
    }

    @Override
    public String toString() {
        return super.toString() + ", 治癒力: " + healPower;
    }
}
