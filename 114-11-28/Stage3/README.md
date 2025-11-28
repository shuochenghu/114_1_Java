# 第三階段：引入中間抽象類別（多層繼承結構）

## 📚 教學目標

讓學生理解：
1. **為什麼需要中間層抽象類別？**
2. **如何設計合理的類別階層架構**
3. **多層繼承的優點與應用**
4. **抽象類別也可以繼承抽象類別**

---

## 🎯 本階段新增內容

### 類別繼承結構

```
Role (最高層抽象類別)
├─ MeleeRole (近戰角色抽象類別)
│  ├─ SwordsMan (劍士)
│  └─ ShieldSwordsMan (持盾劍士)
└─ RangedRole (遠程角色抽象類別)
   ├─ Magician (魔法師)
   └─ Archer (弓箭手) ← 新增角色
```

### 新增的中間抽象類別

#### 1️⃣ MeleeRole（近戰角色）

```java
public abstract class MeleeRole extends Role {
    private int armor;  // 護甲值：近戰角色特有
    
    // 具體方法：計算防禦
    public int calculateDefense(int incomingDamage) {
        return Math.max(0, incomingDamage - armor);
    }
    
    // 抽象方法：取得武器類型
    public abstract String getWeaponType();
    
    // 抽象方法：近戰特殊準備
    protected abstract void onMeleePrepare();
}
```

**為什麼需要 MeleeRole？**
- 近戰角色有共同特性：護甲、近戰武器
- 統一處理防禦計算邏輯
- 避免在 Role 加入只有近戰角色才需要的屬性

#### 2️⃣ RangedRole（遠程角色）

```java
public abstract class RangedRole extends Role {
    private int range;      // 攻擊範圍
    private int energy;     // 能量值
    private int maxEnergy;
    
    // 具體方法：檢查射程
    public boolean isInRange(int distance) {
        return distance <= range;
    }
    
    // 具體方法：能量管理
    public boolean consumeEnergy(int amount) {
        if (energy >= amount) {
            energy -= amount;
            return true;
        }
        return false;
    }
    
    // 抽象方法：取得攻擊類型
    public abstract String getRangedAttackType();
}
```

**為什麼需要 RangedRole？**
- 遠程角色有共同特性：射程、能量值
- 統一處理射程檢查和能量管理
- 避免在 Role 加入只有遠程角色才需要的屬性

---

## 💡 核心概念解說

### 1️⃣ 為什麼需要中間層抽象類別？

#### 問題情境

假設我們直接在 `Role` 加入所有屬性：

```java
public abstract class Role {
    private int health;
    private int attackPower;
    private int armor;        // 只有近戰角色需要
    private int range;        // 只有遠程角色需要
    private int energy;       // 只有遠程角色需要
    // ...
}
```

❌ **問題：**
1. 魔法師不需要 `armor`，但也會有這個屬性
2. 劍士不需要 `range` 和 `energy`，但也會有這些屬性
3. 類別膨脹：越來越多無關的屬性
4. 違反「單一職責原則」

✅ **解決方案：引入中間層**

```
Role (共同屬性：name, health, attackPower)
├─ MeleeRole (近戰特有：armor)
└─ RangedRole (遠程特有：range, energy)
```

**優點：**
- 每個類別只包含相關的屬性
- 結構清晰，易於理解
- 易於擴展（例如：新增 HybridRole 混合型角色）

---

### 2️⃣ 多層繼承的設計原則

#### 單一繼承鏈

Java 只支援**單一繼承**（一個類別只能繼承一個父類別）：

```java
// ✅ 正確：單一繼承鏈
public class ShieldSwordsMan extends SwordsMan {
    // ShieldSwordsMan → SwordsMan → MeleeRole → Role
}

// ❌ 錯誤：不能多重繼承
public class HybridRole extends MeleeRole, RangedRole {
    // Java 不支援這種寫法
}
```

#### 繼承層次建議

```
建議：不超過 3-4 層
Role → MeleeRole → SwordsMan → ShieldSwordsMan  ✅ (4層，可接受)

避免：過深的繼承鏈
Role → A → B → C → D → E → F  ❌ (7層，過於複雜)
```

---

### 3️⃣ 抽象類別可以繼承抽象類別

#### MeleeRole 繼承 Role

```java
// Role 是抽象類別
public abstract class Role {
    public abstract void attack(Role opponent);
    public abstract void onDeath();
}

// MeleeRole 也是抽象類別，繼承 Role
public abstract class MeleeRole extends Role {
    // 可以選擇：
    // 1. 實作父類別的抽象方法
    // 2. 不實作，讓更下層的子類別實作
    // 3. 新增自己的抽象方法
    
    public abstract String getWeaponType();  // 新增的抽象方法
}

// SwordsMan 是具體類別，必須實作所有抽象方法
public class SwordsMan extends MeleeRole {
    // 必須實作 Role 的抽象方法
    @Override
    public void attack(Role opponent) { ... }
    
    @Override
    public void onDeath() { ... }
    
    // 必須實作 MeleeRole 的抽象方法
    @Override
    public String getWeaponType() { return "雙手劍"; }
}
```

**重點：**
- 抽象類別可以繼承抽象類別
- 具體類別必須實作所有繼承鏈上的抽象方法
- 每一層可以新增自己的抽象方法

---

### 4️⃣ 繼承層次的職責劃分

| 層次 | 類別 | 職責 | 屬性與方法 |
|-----|------|------|-----------|
| **第一層** | Role | 所有角色的共通特性 | name, health, attackPower<br>attack(), onDeath() |
| **第二層** | MeleeRole | 近戰角色的共通特性 | armor<br>calculateDefense(), getWeaponType() |
| **第二層** | RangedRole | 遠程角色的共通特性 | range, energy<br>isInRange(), consumeEnergy() |
| **第三層** | SwordsMan | 劍士的特定實作 | 實作所有抽象方法 |
| **第三層** | Magician | 魔法師的特定實作 | healPower<br>heal() |
| **第四層** | ShieldSwordsMan | 持盾劍士的特定實作 | defenseCapacity<br>defence() |

---

## 🔍 類別設計對比

### 設計 A：沒有中間層（不好的設計）

```
Role
├─ SwordsMan (需要護甲)
├─ ShieldSwordsMan (需要護甲)
├─ Magician (需要射程、能量)
└─ Archer (需要射程、能量)
```

**問題：**
- 護甲計算邏輯在 SwordsMan 和 ShieldSwordsMan 重複
- 能量管理在 Magician 和 Archer 重複
- 新增近戰角色需要重新寫護甲邏輯

### 設計 B：有中間層（好的設計）✅

```
Role
├─ MeleeRole
│  ├─ SwordsMan
│  └─ ShieldSwordsMan
└─ RangedRole
   ├─ Magician
   └─ Archer
```

**優點：**
- 護甲計算統一在 MeleeRole
- 能量管理統一在 RangedRole
- 新增近戰角色自動繼承護甲功能

---

## 🖥️ 執行結果展示

```
════════════════════════════════════════
        🎮 RPG 遊戲 - 第三階段
      展示：多層繼承結構設計
════════════════════════════════════════

📋 類別繼承結構：
Role (最高層)
├─ MeleeRole (近戰角色)
│  ├─ SwordsMan (劍士)
│  └─ ShieldSwordsMan (持盾劍士)
└─ RangedRole (遠程角色)
   ├─ Magician (魔法師)
   └─ Archer (弓箭手)

════════════════════════════════════════
          🔍 角色類別特性展示
════════════════════════════════════════

【近戰角色特性】
光明劍士：武器=雙手劍，護甲=5
黑暗劍士：武器=雙手劍，護甲=3
持盾劍士：武器=單手劍+盾牌，護甲=8

【遠程角色特性】
光明法師：攻擊類型=魔法彈，射程=8，能量=100/100
黑暗法師：攻擊類型=魔法彈，射程=8，能量=100/100
精靈射手：攻擊類型=精準箭矢，射程=10，能量=80/80

════════════════════════════════════════

⚔️  戰鬥開始！

━━━━━━━━━━ 第 1 回合 ━━━━━━━━━━
⚔️  光明劍士 檢查 雙手劍 的狀態...
🛡️  目前護甲值：5
✨ 擦拭劍刃，劍身反射出凜冽的寒光...

⚔️  光明劍士 揮動 雙手劍 攻擊 黑暗法師！
💫 消耗 15 點能量，剩餘：85/100
💥 黑暗法師 受到 20 點傷害！目前生命值：60

🗡️  光明劍士 將 雙手劍 收入劍鞘。

━━━━━━━━━━ 第 2 回合 ━━━━━━━━━━
🎯 精靈射手 準備 精準箭矢 攻擊...
📊 能量值：80/80，射程：10
🏹 檢查弓弦的張力和箭矢的狀態...
🎯 調整呼吸，進入射擊姿態。

🏹 精靈射手 射出 精準箭矢 攻擊 光明劍士！
💫 消耗 10 點能量，剩餘：70/80
📊 剩餘箭矢：29/30
🛡️  護甲減免 5 點傷害！
💥 光明劍士 受到 13 點傷害！目前生命值：87

✨ 恢復 10 點能量 (70 → 80)
💪 精靈射手 放鬆手臂肌肉，恢復體力。
```

---

## 📝 課堂練習

### 練習 1：理解繼承層次

**問題：** 為什麼 `armor` 放在 MeleeRole 而不是 Role？

**提示：** 思考魔法師是否需要護甲屬性。

**參考答案：**
因為只有近戰角色需要護甲，遠程角色（魔法師、弓箭手）不需要。如果放在 Role，所有角色都會有護甲屬性，造成資源浪費和概念混淆。

---

### 練習 2：設計新角色 - Priest（牧師）

**情境：** 牧師是遠程角色，可以治療和施放神聖魔法

**任務：** 決定 Priest 應該繼承哪個類別？為什麼？

**選項：**
A. 繼承 Role
B. 繼承 MeleeRole
C. 繼承 RangedRole

**參考答案：** C. 繼承 RangedRole

**理由：**
1. 牧師是遠程角色，需要射程和能量管理
2. 可以直接使用 RangedRole 的能量消耗和恢復機制
3. 只需要實作 getRangedAttackType() 等特定方法

**參考實作：**

```java
public class Priest extends RangedRole {
    private int blessPower;
    
    public Priest(String name, int health, int attackPower, 
                  int blessPower, int range, int maxEnergy) {
        super(name, health, attackPower, range, maxEnergy);
        this.blessPower = blessPower;
    }
    
    @Override
    public String getRangedAttackType() {
        return "神聖之光";
    }
    
    // ... 其他方法實作
}
```

---

### 練習 3：識別設計問題

**情境：** 有同學想新增一個「魔劍士」，既會近戰也會遠程魔法

**錯誤設計：**
```java
// ❌ 錯誤：Java 不支援多重繼承
public class MagicSwordsman extends MeleeRole, RangedRole {
    // 編譯錯誤！
}
```

**問題：** 如何正確設計魔劍士？

**參考答案：**

**方案 A：選擇主要特性繼承**
```java
// 魔劍士主要是近戰，但有魔法能力
public class MagicSwordsman extends MeleeRole {
    private int mana;  // 自己管理魔力
    
    public void castSpell(Role target) {
        // 自己實作魔法邏輯
    }
}
```

**方案 B：使用組合（進階）**
```java
public class MagicSwordsman extends MeleeRole {
    private MagicAbility magic;  // 組合魔法能力
    
    public void castSpell(Role target) {
        magic.cast(target);
    }
}
```

---

### 練習 4：擴展繼承結構

**任務：** 設計一個新的中間層 `HealerRole`（治療者角色）

**要求：**
1. 決定 HealerRole 應該繼承哪個類別
2. 新增治療相關的屬性和方法
3. 讓 Priest 和 Magician 都能繼承治療能力

**思考：** 這個設計有什麼問題？

**提示：** Magician 已經繼承 RangedRole，不能再繼承 HealerRole（單一繼承限制）

**更好的設計：** 在第四階段會學習使用介面（interface）來解決這個問題

---

## 🎓 教學重點總結

### 多層繼承的優點

1. **清晰的結構**
   - 每一層有明確的職責
   - 容易理解和維護

2. **程式碼重用**
   - 共通邏輯集中在中間層
   - 避免重複程式碼

3. **易於擴展**
   - 新增近戰角色：繼承 MeleeRole
   - 新增遠程角色：繼承 RangedRole

### 設計原則

1. **從一般到特殊**
   ```
   Role (最一般) → MeleeRole (較特殊) → SwordsMan (最特殊)
   ```

2. **每層的職責單一**
   - Role：所有角色共通
   - MeleeRole：近戰角色共通
   - SwordsMan：劍士特有

3. **避免過深的繼承**
   - 建議：3-4 層
   - 過深會難以理解和維護

### 判斷是否需要中間層

```
問題：多個類別有共同特性？
     ↓
  是 → 考慮新增中間層
     ↓
問題：這些特性只有部分角色需要？
     ↓
  是 → 新增專門的中間抽象類別
  否 → 放在最高層 Role
```

---

## 🚀 下一階段預告

**第四階段：引入介面（Interface）**

我們將學習：
- 介面與抽象類別的差異
- 如何使用介面解決多重繼承的需求
- Defendable、Healable 介面設計
- 一個類別實作多個介面

---

## 📦 檔案清單

```
Stage3_Inheritance_Hierarchy/
├── Role.java              # 最高層抽象類別
├── MeleeRole.java         # 近戰角色抽象類別 ⭐ 新增
├── RangedRole.java        # 遠程角色抽象類別 ⭐ 新增
├── SwordsMan.java         # 劍士（繼承 MeleeRole）
├── ShieldSwordsMan.java   # 持盾劍士（繼承 SwordsMan）
├── Magician.java          # 魔法師（繼承 RangedRole）
├── Archer.java            # 弓箭手（繼承 RangedRole）⭐ 新增
├── RPG.java               # 主程式
├── README.md              # 專案說明
└── 教學流程指南.md        # 教學指南
```

---

## 💻 編譯與執行

```bash
# 編譯
javac *.java

# 執行
java RPG
```

---

## 📌 常見問題 Q&A

### Q1: 為什麼不把所有屬性都放在 Role？

**A:** 因為不是所有角色都需要所有屬性。例如魔法師不需要護甲，劍士不需要能量值。分層設計讓每個類別只包含相關的屬性。

### Q2: 抽象類別可以繼承抽象類別嗎？

**A:** 可以！MeleeRole 和 RangedRole 都是抽象類別，它們都繼承自 Role 抽象類別。

### Q3: 為什麼要限制繼承層次在 3-4 層？

**A:** 過深的繼承會：
- 難以理解整體結構
- 修改困難（改一層可能影響多層）
- 違反「組合優於繼承」原則

### Q4: Java 為什麼不支援多重繼承？

**A:** 多重繼承會產生「鑽石問題」（Diamond Problem）。Java 選擇單一繼承 + 多個介面的方式來解決。我們會在第四階段學習介面。

---

**版本：** Stage 3 - Inheritance Hierarchy  
**作者：** Chuck  
**更新日期：** 2024
