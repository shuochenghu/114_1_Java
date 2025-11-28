/**
 * MeleeRole - 近戰角色抽象類別
 * 
 * 為什麼需要這個中間層？
 * 1. 近戰角色有共同特性：護甲值、近戰武器
 * 2. 可以統一處理近戰角色的共通邏輯（例如：防禦計算）
 * 3. 避免在 Role 加入只有近戰角色才需要的屬性
 * 
 * 繼承結構：
 * Role (最高層抽象類別)
 *   ↓
 * MeleeRole (中間層抽象類別) - 近戰角色的共通特性
 *   ↓
 * SwordsMan, ShieldSwordsMan (具體類別)
 */
public abstract class MeleeRole extends Role {
    // 護甲值：近戰角色特有的屬性
    private int armor;
    
    /**
     * 建構子：初始化近戰角色
     * @param name 角色名稱
     * @param health 生命值
     * @param attackPower 攻擊力
     * @param armor 護甲值
     */
    public MeleeRole(String name, int health, int attackPower, int armor) {
        super(name, health, attackPower);
        this.armor = armor;
    }

    // 取得護甲值
    public int getArmor() {
        return armor;
    }

    // 設定護甲值
    public void setArmor(int armor) {
        this.armor = armor;
    }

    // ========== 第三階段新增：近戰角色的共通方法 ==========
    
    /**
     * 計算防禦後的實際傷害（具體方法）
     * 這是所有近戰角色共用的邏輯：傷害 - 護甲值
     * 
     * 為什麼是具體方法？
     * 因為所有近戰角色的防禦計算方式都相同
     */
    public int calculateDefense(int incomingDamage) {
        int actualDamage = Math.max(0, incomingDamage - armor);
        if (armor > 0 && incomingDamage > 0) {
            System.out.println("🛡️  護甲減免 " + Math.min(armor, incomingDamage) + " 點傷害！");
        }
        return actualDamage;
    }

    /**
     * 覆寫 takeDamage 方法，加入護甲計算
     * 展示了子類別如何擴展父類別的方法
     */
    @Override
    public void takeDamage(int damage) {
        // 先計算護甲減免
        int actualDamage = calculateDefense(damage);
        // 再呼叫父類別的 takeDamage 處理實際傷害
        super.takeDamage(actualDamage);
    }

    /**
     * 抽象方法：取得武器類型
     * 為什麼是抽象方法？
     * 因為每種近戰角色使用的武器不同：
     * - 劍士：雙手劍
     * - 持盾劍士：單手劍
     */
    public abstract String getWeaponType();

    /**
     * 近戰角色的共通戰前準備（具體方法）
     * 所有近戰角色都會檢查裝備
     */
    @Override
    public void prepareBattle() {
        System.out.println("⚔️  " + getName() + " 檢查 " + getWeaponType() + " 的狀態...");
        System.out.println("🛡️  目前護甲值：" + armor);
        onMeleePrepare(); // 呼叫抽象方法，讓子類別加入特殊準備
    }

    /**
     * 抽象方法：近戰角色的特殊準備動作
     * 讓子類別可以加入自己的準備邏輯
     */
    protected abstract void onMeleePrepare();

    @Override
    public String toString() {
        return super.toString() + ", 護甲值: " + armor;
    }
}
