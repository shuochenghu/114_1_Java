public abstract class Role {
    // 角色名稱
    private String name;
    // 生命值
    private int health;
    // 攻擊力
    private int attackPower;

    // 建構子：初始化角色的名稱、生命值和攻擊力
    public Role(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
    }

    // 取得角色名稱
    public String getName() {
        return name;
    }

    // 取得生命值
    public int getHealth() {
        return health;
    }

    // 取得攻擊力
    public int getAttackPower() {
        return attackPower;
    }
    
    // 設定生命值
    public void setHealth(int health) {
        this.health = health;
    }

    // 檢查角色是否存活
    public boolean isAlive() {
        return health > 0;
    }

    // 抽象方法：攻擊對手
    public abstract void attack(Role opponent);

    // 抽象方法：展示角色的特殊技能
    public abstract void showSpecialSkill();

    // ========== 第二階段新增：具體方法 + 抽象方法的結合 ==========
    
    /**
     * 受到傷害（具體方法）
     * 為什麼是具體方法？
     * 1. 所有角色受傷的「基本流程」都相同：扣血 → 顯示訊息 → 檢查是否死亡
     * 2. 這是「共通邏輯」，可以在父類別統一實作
     * 3. 避免每個子類別都要寫一次相同的程式碼
     * 
     * @param damage 受到的傷害值
     */
    public void takeDamage(int damage) {
        this.health -= damage;
        System.out.println("💥 " + name + " 受到 " + damage + " 點傷害！目前生命值：" + health);
        
        // 如果角色死亡，呼叫抽象方法 onDeath()
        if (!isAlive()) {
            onDeath(); // 每個角色的死亡效果不同，交由子類別實作
        }
    }

    /**
     * 死亡時的處理（抽象方法）
     * 為什麼是抽象方法？
     * 1. 每個角色死亡時的「表現方式」都不同
     * 2. 劍士：劍掉落在地
     * 3. 魔法師：化為魔法粒子消失
     * 4. 這是「個別差異」，必須由子類別各自實作
     */
    public abstract void onDeath();

    /**
     * 戰鬥前的準備動作（抽象方法）
     * 為什麼是抽象方法？
     * 1. 每個角色的準備動作都不同
     * 2. 劍士：擦拭劍刃
     * 3. 魔法師：吟唱咒語
     */
    public abstract void prepareBattle();

    /**
     * 戰鬥後的行為（抽象方法）
     * 為什麼是抽象方法？
     * 1. 每個角色戰後行為都不同
     * 2. 劍士：收劍入鞘
     * 3. 魔法師：恢復魔力
     */
    public abstract void afterBattle();

    @Override
    public String toString() {
        return "角色名稱: " + name + ", 生命值: " + health;
    }
}
