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
    // 為什麼是抽象？因為每個角色的攻擊方式都不同
    public abstract void attack(Role opponent);

    // ========== 第一階段新增：抽象方法 ==========
    /**
     * 展示角色的特殊技能
     * 為什麼設計成抽象方法？
     * 1. 每個角色都有特殊技能（共同規格）
     * 2. 但每個角色的技能內容都不同（個別實作）
     * 3. 我們無法在 Role 類別中提供一個「適合所有角色」的預設實作
     */
    public abstract void showSpecialSkill();

    @Override
    public String toString() {
        return "角色名稱: " + name + ", 生命值: " + health;
    }
}
