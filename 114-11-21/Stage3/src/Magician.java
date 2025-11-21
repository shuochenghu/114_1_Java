/**
 * Magician - 魔法師類別
 * 
 * 第三階段修改：
 * - 從繼承 Role 改為繼承 RangedRole
 * - 獲得射程、能量值屬性
 * - 獲得射程檢查、能量管理能力
 * - 需要實作 getRangedAttackType()、onRangedPrepare()、onRangedRecover()
 */
public class Magician extends RangedRole {
    // 治癒力
    private int healPower;

    /**
     * 建構子：初始化魔法師
     * 注意：現在需要傳入 range 和 maxEnergy 參數
     */
    public Magician(String name, int health, int attackPower, int healPower, int range, int maxEnergy) {
        super(name, health, attackPower, range, maxEnergy);
        this.healPower = healPower;
    }

    // 取得治癒力
    public int getHealPower() {
        return healPower;
    }

    // 攻擊對手
    @Override
    public void attack(Role opponent) {
        // 檢查能量是否足夠
        if (!consumeEnergy(15)) {
            System.out.println("❌ " + getName() + " 能量不足，無法施放魔法！");
            return;
        }
        
        System.out.println("✨ " + getName() + " 施放 " + getRangedAttackType() + " 攻擊 " + opponent.getName() + "！");
        opponent.takeDamage(this.getAttackPower());
    }

    // 治療隊友
    public void heal(Role ally) {
        // 檢查能量是否足夠
        if (!consumeEnergy(10)) {
            System.out.println("❌ " + getName() + " 能量不足，無法施放治療！");
            return;
        }
        
        int oldHealth = ally.getHealth();
        ally.setHealth(ally.getHealth() + this.healPower);
        System.out.println("💚 " + this.getName() + " 治療 " + ally.getName() + 
                         " 回復 " + healPower + " 點生命值 (" + oldHealth + " → " + ally.getHealth() + ")");
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
        System.out.println("║ 射程：" + getRange() + " 米                ║");
        System.out.println("╚═════════════════════════════╝");
    }

    // 魔法師的死亡效果
    @Override
    public void onDeath() {
        System.out.println("💀 " + this.getName() + " 的生命之火熄滅了...");
        System.out.println("✨ " + this.getName() + " 的身體化為無數魔法粒子，消散在空氣中。");
        System.out.println("🌟 魔法書掉落在地上，微微發光。");
        System.out.println("---");
    }

    // ========== 第三階段新增：實作 RangedRole 的抽象方法 ==========
    
    /**
     * 取得遠程攻擊類型（抽象方法實作）
     * 魔法師使用魔法彈
     */
    @Override
    public String getRangedAttackType() {
        return "魔法彈";
    }

    /**
     * 遠程特殊準備（抽象方法實作）
     * 魔法師會吟唱咒語
     */
    @Override
    protected void onRangedPrepare() {
        System.out.println("📖 翻開魔法書，開始吟唱古老的咒語...");
        System.out.println("✨ 魔法能量在周圍凝聚，空氣中閃爍著神秘的光芒。");
    }

    /**
     * 遠程特殊恢復（抽象方法實作）
     * 魔法師會冥想
     */
    @Override
    protected void onRangedRecover() {
        System.out.println("🧘 " + this.getName() + " 閉目冥想，深度恢復魔力。");
    }

    @Override
    public String toString() {
        return super.toString() + ", 治癒力: " + healPower;
    }
}
