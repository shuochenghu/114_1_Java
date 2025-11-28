public class ShieldSwordsMan extends SwordsMan {
    private int defenseCapacity;
    
    // 建構子：初始化持盾劍士的名稱、生命值和攻擊力
    public ShieldSwordsMan(String name, int health, int attackPower, int defenseCapacity) {
        super(name, health, attackPower);
        this.defenseCapacity = defenseCapacity;
    }

    // 攻擊對手（持盾劍士攻擊力較低）
    @Override
    public void attack(Role opponent) {
        int reducedDamage = this.getAttackPower() - 5; // 持盾劍士攻擊力減少5點
        System.out.println("🛡️⚔️  " + this.getName() + " 單手揮劍攻擊 " + opponent.getName() + "！");
        opponent.takeDamage(reducedDamage); // 使用 takeDamage 方法
    }

    public int getDefenseCapacity() {
        return defenseCapacity;
    }

    // 防禦能力
    public void defence() {
        int oldHealth = this.getHealth();
        this.setHealth(this.getHealth() + defenseCapacity);
        System.out.println("🛡️  " + this.getName() + " 舉起盾牌防禦！恢復 " + defenseCapacity + 
                         " 點生命值。(" + oldHealth + " → " + this.getHealth() + ")");
    }

    // 展示特殊技能（覆寫父類別）
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

    // ========== 第二階段新增：覆寫死亡和戰鬥相關方法 ==========
    
    /**
     * 持盾劍士的死亡效果
     * 持盾劍士死亡時，盾牌和劍都會掉落
     * 注意：這裡展示了「繼承後再覆寫」的能力
     */
    @Override
    public void onDeath() {
        System.out.println("💀 " + this.getName() + " 力竭倒下...");
        System.out.println("🛡️  厚重的盾牌砸在地上，揚起一陣塵土。");
        System.out.println("⚔️  " + this.getName() + " 的劍也隨之掉落。");
        System.out.println("---");
    }

    /**
     * 持盾劍士的戰前準備
     * 檢查盾牌和劍的狀態
     */
    @Override
    public void prepareBattle() {
        System.out.println("🛡️  " + this.getName() + " 檢查盾牌的牢固程度...");
        System.out.println("⚔️  同時確認劍刃的鋒利度，準備應戰。");
    }

    /**
     * 持盾劍士的戰後行為
     * 修復盾牌上的損傷
     */
    @Override
    public void afterBattle() {
        System.out.println("🛡️  " + this.getName() + " 檢視盾牌上的新傷痕，並進行簡單修補。");
    }
}
