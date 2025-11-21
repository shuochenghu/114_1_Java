public class ShieldSwordsMan extends SwordsMan {
    private int defenseCapacity;
    
    // 建構子：初始化持盾劍士的名稱、生命值和攻擊力
    public ShieldSwordsMan(String name, int health, int attackPower, int defenseCapacity) {
        super(name, health, attackPower);
        this.defenseCapacity = defenseCapacity;
    }

    // 攻擊對手(劍客/魔法師)，父類別的參考指到子類別物件
    @Override
    public void attack(Role opponent) {
        int reducedDamage = this.getAttackPower() - 5; // 持盾劍士攻擊力減少5點
        opponent.setHealth(opponent.getHealth() - reducedDamage);
        System.out.println(this.getName() + " 揮劍攻擊 " + opponent.getName() + " 造成 " +
                reducedDamage + " 點傷害。" + opponent);
    }

    public int getDefenseCapacity() {
        return defenseCapacity;
    }

    public void defence() {
        this.setHealth(this.getHealth() + defenseCapacity);
        System.out.println(this.getName() + " 使用盾牌防禦，恢復 " + defenseCapacity + " 點生命值。" + this);
    }

    // ========== 第一階段新增：覆寫抽象方法 ==========
    /**
     * 持盾劍士的特殊技能：盾牌猛擊
     * 注意：ShieldSwordsMan 繼承自 SwordsMan，但有自己的技能展示
     * 這展示了抽象方法可以被多次覆寫
     */
    @Override
    public void showSpecialSkill() {
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║ " + this.getName() + " 的特殊技能      ║");
        System.out.println("╠═════════════════════════════╣");
        System.out.println("║ 技能名稱：盾牌猛擊          ║");
        System.out.println("║ 技能描述：使用盾牌撞擊敵人  ║");
        System.out.println("║ 技能效果：造成傷害並暈眩    ║");
        System.out.println("║ 防禦加成：+" + defenseCapacity + " 防禦力           ║");
        System.out.println("╚═════════════════════════════╝");
    }
}
