/**
 * RangedRole - 遠程角色抽象類別
 * 
 * 為什麼需要這個中間層？
 * 1. 遠程角色有共同特性：攻擊範圍、魔力/能量值
 * 2. 可以統一處理遠程角色的共通邏輯（例如：射程檢查）
 * 3. 避免在 Role 加入只有遠程角色才需要的屬性
 * 
 * 繼承結構：
 * Role (最高層抽象類別)
 *   ↓
 * RangedRole (中間層抽象類別) - 遠程角色的共通特性
 *   ↓
 * Magician, Archer (具體類別)
 */
public abstract class RangedRole extends Role {
    // 攻擊範圍：遠程角色特有的屬性
    private int range;
    // 能量值：用於施放遠程攻擊（魔力、箭矢等）
    private int energy;
    private int maxEnergy;
    
    /**
     * 建構子：初始化遠程角色
     * @param name 角色名稱
     * @param health 生命值
     * @param attackPower 攻擊力
     * @param range 攻擊範圍
     * @param maxEnergy 最大能量值
     */
    public RangedRole(String name, int health, int attackPower, int range, int maxEnergy) {
        super(name, health, attackPower);
        this.range = range;
        this.maxEnergy = maxEnergy;
        this.energy = maxEnergy; // 初始能量為最大值
    }

    // 取得攻擊範圍
    public int getRange() {
        return range;
    }

    // 取得當前能量值
    public int getEnergy() {
        return energy;
    }

    // 取得最大能量值
    public int getMaxEnergy() {
        return maxEnergy;
    }

    // 設定能量值
    public void setEnergy(int energy) {
        this.energy = Math.min(energy, maxEnergy); // 不超過最大值
    }

    // ========== 第三階段新增：遠程角色的共通方法 ==========
    
    /**
     * 檢查是否在射程內（具體方法）
     * 這是所有遠程角色共用的邏輯
     * 
     * 為什麼是具體方法？
     * 因為所有遠程角色的射程檢查方式都相同
     */
    public boolean isInRange(int distance) {
        boolean inRange = distance <= range;
        if (!inRange) {
            System.out.println("❌ 目標距離 " + distance + " 超出射程 " + range + "！");
        }
        return inRange;
    }

    /**
     * 消耗能量（具體方法）
     * 所有遠程攻擊都需要消耗能量
     */
    public boolean consumeEnergy(int amount) {
        if (energy >= amount) {
            energy -= amount;
            System.out.println("💫 消耗 " + amount + " 點能量，剩餘：" + energy + "/" + maxEnergy);
            return true;
        } else {
            System.out.println("❌ 能量不足！需要 " + amount + "，目前只有 " + energy);
            return false;
        }
    }

    /**
     * 恢復能量（具體方法）
     * 遠程角色的共通恢復機制
     */
    public void restoreEnergy(int amount) {
        int oldEnergy = energy;
        energy = Math.min(energy + amount, maxEnergy);
        System.out.println("✨ 恢復 " + (energy - oldEnergy) + " 點能量 (" + oldEnergy + " → " + energy + ")");
    }

    /**
     * 抽象方法：取得遠程攻擊類型
     * 為什麼是抽象方法？
     * 因為每種遠程角色的攻擊方式不同：
     * - 魔法師：魔法彈
     * - 弓箭手：箭矢
     */
    public abstract String getRangedAttackType();

    /**
     * 遠程角色的共通戰前準備（具體方法）
     * 所有遠程角色都會檢查能量和射程
     */
    @Override
    public void prepareBattle() {
        System.out.println("🎯 " + getName() + " 準備 " + getRangedAttackType() + " 攻擊...");
        System.out.println("📊 能量值：" + energy + "/" + maxEnergy + "，射程：" + range);
        onRangedPrepare(); // 呼叫抽象方法，讓子類別加入特殊準備
    }

    /**
     * 抽象方法：遠程角色的特殊準備動作
     * 讓子類別可以加入自己的準備邏輯
     */
    protected abstract void onRangedPrepare();

    /**
     * 遠程角色的戰後行為：恢復能量
     */
    @Override
    public void afterBattle() {
        restoreEnergy(10); // 每次戰鬥後恢復 10 點能量
        onRangedRecover(); // 呼叫抽象方法
    }

    /**
     * 抽象方法：遠程角色的特殊恢復動作
     */
    protected abstract void onRangedRecover();

    @Override
    public String toString() {
        return super.toString() + ", 能量: " + energy + "/" + maxEnergy + ", 射程: " + range;
    }
}
