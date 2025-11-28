# 第四階段：引入介面（Interface）

## 📚 教學目標

讓學生理解：
1. **介面與抽象類別的差異**
2. **為什麼需要介面？解決什麼問題？**
3. **一個類別可以實作多個介面**
4. **介面的預設方法（default method）**

---

## 🎯 本階段新增內容

### 新增的介面

#### 1️⃣ Defendable（可防禦介面）

```java
public interface Defendable {
    void defend();                    // 抽象方法
    int getDefenseCapacity();         // 抽象方法
    default boolean canDefend() {     // 預設方法
        return getDefenseCapacity() > 0;
    }
}
```

**誰實作這個介面？**
- ShieldSwordsMan（持盾劍士）
- Paladin（聖騎士）

#### 2️⃣ Healable（可治療介面）

```java
public interface Healable {
    void heal(Role target);           // 抽象方法
    int getHealPower();               // 抽象方法
    default boolean canHeal() {       // 預設方法
        return getHealPower() > 0;
    }
    default void showHealInfo() {     // 預設方法
        System.out.println("💚 治療力：" + getHealPower());
    }
}
```

**誰實作這個介面？**
- Magician（魔法師）
- Paladin（聖騎士）

### 新增角色：Paladin（聖騎士）⭐

```java
public class Paladin extends MeleeRole 
                      implements Defendable, Healable {
    // 同時實作兩個介面！
}
```

**這是本階段的核心示範：**
- 繼承 MeleeRole（單一繼承）
- 同時實作 Defendable 和 Healable（多重介面）
- 既能防禦又能治療

---

## 💡 核心概念解說

### 1️⃣ 為什麼需要介面？

#### 問題：單一繼承的限制

假設我們想設計一個聖騎士：
- 是近戰角色（需要繼承 MeleeRole）
- 會防禦（需要防禦能力）
- 會治療（需要治療能力）

**如果只用繼承：**

```java
// ❌ 錯誤：Java 不支援多重繼承
public class Paladin extends MeleeRole, DefenseClass, HealClass {
    // 編譯錯誤！
}
```

**只能擇一繼承：**

```java
// 方案 A：繼承 MeleeRole，自己實作防禦和治療
public class Paladin extends MeleeRole {
    // 需要自己寫防禦邏輯
    public void defend() { ... }
    // 需要自己寫治療邏輯
    public void heal() { ... }
    // 問題：程式碼重複！
}

// 方案 B：其他防禦角色也要重複寫
public class ShieldSwordsMan extends SwordsMan {
    // 又要寫一次防禦邏輯
    public void defend() { ... }
    // 程式碼重複！
}
```

#### 解決方案：使用介面✅

```java
// 定義介面
public interface Defendable {
    void defend();
    int getDefenseCapacity();
}

// 聖騎士實作介面
public class Paladin extends MeleeRole 
                      implements Defendable, Healable {
    // 實作防禦
    @Override
    public void defend() { ... }
    
    // 實作治療
    @Override
    public void heal() { ... }
}

// 持盾劍士也實作同樣的介面
public class ShieldSwordsMan extends SwordsMan 
                              implements Defendable {
    @Override
    public void defend() { ... }
}
```

**優點：**
1. ✅ 可以實作多個介面（突破單一繼承限制）
2. ✅ 防禦行為標準化（都實作 Defendable）
3. ✅ 清楚表達能力（「是」近戰角色，「能」防禦和治療）

---

### 2️⃣ 介面 vs 抽象類別

#### 對比表

| 特性 | 抽象類別 (Abstract Class) | 介面 (Interface) |
|-----|--------------------------|-----------------|
| **用途** | 描述「是什麼」(is-a) | 描述「能做什麼」(can-do) |
| **繼承/實作** | 只能繼承一個 | 可以實作多個 |
| **建構子** | 可以有 | 不能有 |
| **欄位** | 可以有實例變數 | 只能有常數（static final） |
| **方法** | 可以有具體方法和抽象方法 | 只能有抽象方法和預設方法 |
| **關鍵字** | `extends` | `implements` |
| **範例** | `Paladin extends MeleeRole` | `Paladin implements Healable` |

#### 概念理解

```
抽象類別：定義「本質」
- Paladin "是" MeleeRole（近戰角色）
- Magician "是" RangedRole（遠程角色）

介面：定義「能力」
- Paladin "能" Defend（防禦）
- Paladin "能" Heal（治療）
- ShieldSwordsMan "能" Defend（防禦）
- Magician "能" Heal（治療）
```

#### 選擇判斷

```
需要新增一個功能...
        ↓
   是「本質」還是「能力」？
        ↓
    ┌───┴───┐
  本質      能力
    ↓         ↓
抽象類別    介面
    ↓         ↓
 extends  implements
```

**範例：**
- 「聖騎士是近戰角色」→ 本質 → 繼承 MeleeRole
- 「聖騎士能防禦」→ 能力 → 實作 Defendable
- 「聖騎士能治療」→ 能力 → 實作 Healable

---

### 3️⃣ 多重介面實作

#### Paladin 的完整設計

```java
public class Paladin extends MeleeRole          // 單一繼承
                      implements Defendable,     // 第一個介面
                                 Healable {      // 第二個介面
    
    // 來自 MeleeRole 的能力
    - 護甲值
    - 近戰攻擊
    
    // 來自 Defendable 介面
    @Override
    public void defend() { ... }
    
    @Override
    public int getDefenseCapacity() { ... }
    
    // 來自 Healable 介面
    @Override
    public void heal(Role target) { ... }
    
    @Override
    public int getHealPower() { ... }
}
```

**關鍵理解：**
1. 繼承給予「基礎能力」（MeleeRole 的所有方法和屬性）
2. 介面給予「額外能力」（防禦、治療）
3. 一個類別可以實作任意多個介面

---

### 4️⃣ 介面的預設方法（Default Method）

從 Java 8 開始，介面可以有預設方法：

```java
public interface Defendable {
    // 抽象方法：必須實作
    void defend();
    int getDefenseCapacity();
    
    // 預設方法：可以選擇性覆寫
    default boolean canDefend() {
        return getDefenseCapacity() > 0;
    }
}
```

**預設方法的好處：**

1. **提供基本實作**
   ```java
   // ShieldSwordsMan 不需要實作 canDefend()
   // 直接使用預設實作即可
   if (shieldSwordsMan.canDefend()) {
       shieldSwordsMan.defend();
   }
   ```

2. **可以選擇性覆寫**
   ```java
   // Paladin 覆寫預設方法
   @Override
   public boolean canDefend() {
       // 聖騎士的防禦還需要檢查聖能
       return getDefenseCapacity() > 0 && holyPower >= 10;
   }
   ```

3. **介面演化更容易**
   - 可以在介面新增方法而不破壞現有實作
   - 舊的類別自動獲得預設實作

---

## 🔍 設計模式對比

### 沒有介面的設計（❌ 不好）

```
Role
├─ MeleeRole
│  ├─ SwordsMan
│  ├─ ShieldSwordsMan (自己實作防禦)
│  └─ Paladin (自己實作防禦 + 治療)
└─ RangedRole
   └─ Magician (自己實作治療)
```

**問題：**
- 防禦邏輯在 ShieldSwordsMan 和 Paladin 重複
- 治療邏輯在 Magician 和 Paladin 重複
- 無法統一管理有防禦/治療能力的角色

### 使用介面的設計（✅ 好）

```
Role
├─ MeleeRole
│  ├─ SwordsMan
│  ├─ ShieldSwordsMan (implements Defendable)
│  └─ Paladin (implements Defendable, Healable)
└─ RangedRole
   └─ Magician (implements Healable)

介面：
├─ Defendable (防禦能力規範)
└─ Healable (治療能力規範)
```

**優點：**
- 防禦行為標準化（都實作 Defendable）
- 治療行為標準化（都實作 Healable）
- 容易找到所有有特定能力的角色
- 新增能力不影響類別階層

---

## 🖥️ 執行結果展示

```
════════════════════════════════════════
        🎮 RPG 遊戲 - 第四階段
          展示：介面的應用
════════════════════════════════════════

📋 類別與介面結構：
Role (抽象類別)
├─ MeleeRole
│  ├─ SwordsMan
│  ├─ ShieldSwordsMan (實作 Defendable)
│  └─ Paladin (實作 Defendable + Healable) ⭐
└─ RangedRole
   ├─ Magician (實作 Healable)
   └─ Archer

介面 (Interface)：
├─ Defendable：防禦能力
└─ Healable：治療能力

════════════════════════════════════════
          🔍 介面能力展示
════════════════════════════════════════

【可防禦角色 (Defendable)】
✅ 持盾劍士 - 防禦力：10 (可防禦：true)
✅ 聖騎士 - 防禦力：12 (可防禦：true)

【可治療角色 (Healable)】
✅ 光明法師 - 治療力：10 (可治療：true)
✅ 聖騎士 - 治療力：12 (可治療：true)

【多重能力角色】
⭐ 聖騎士 - 同時擁有防禦和治療能力！

════════════════════════════════════════

⚔️  戰鬥開始！

━━━━━━━━━━ 第 1 回合 ━━━━━━━━━━
🙏 聖騎士 低聲祈禱，聖光開始聚集...
✨ 聖劍和聖盾都散發出神聖的光芒。
📊 聖能值：100/100

💚✨ 聖騎士 施放聖光治療 光明劍士
🌟 神聖的光芒包圍著 光明劍士
💚 恢復 12 點生命值 (100 → 112)

🙏 聖騎士 感謝聖光的庇護。
🌟 恢復 10 點聖能 (85 → 95)

━━━━━━━━━━ 第 2 回合 ━━━━━━━━━━
📖 光明法師 翻開魔法書，開始吟唱古老的咒語...
✨ 魔法能量在周圍凝聚，空氣中閃爍著神秘的光芒。

💚 光明法師 施放治療魔法，治療 聖騎士
✨ 恢復 10 點生命值 (110 → 120)
```

---

## 📝 課堂練習

### 練習 1：理解介面的必要性

**問題：** 為什麼不能用繼承解決聖騎士的設計？

**提示：** 思考 Java 的單一繼承限制。

**參考答案：**
聖騎士需要：
1. 繼承 MeleeRole（近戰特性）
2. 防禦能力
3. 治療能力

Java 只支援單一繼承，不能同時繼承多個類別。使用介面可以在繼承 MeleeRole 的同時，實作 Defendable 和 Healable，獲得防禦和治療能力。

---

### 練習 2：設計新介面 - Stealthy（隱身）

**任務：** 設計一個隱身能力介面

**要求：**
```java
public interface Stealthy {
    void hide();              // 進入隱身
    void reveal();            // 解除隱身
    boolean isHidden();       // 檢查是否隱身
    
    // 預設方法：隱身時攻擊力加成
    default int getStealthBonus() {
        return isHidden() ? 10 : 0;
    }
}
```

**問題：** 哪些角色應該實作這個介面？

**參考答案：** 
- Assassin（刺客）
- Rogue（盜賊）
- 任何需要隱身能力的角色

---

### 練習 3：實作 Priest（牧師）

**任務：** 創建牧師類別

**要求：**
1. 繼承 RangedRole（遠程角色）
2. 實作 Healable 介面（治療能力）
3. 不實作 Defendable（牧師不擅長防禦）

**參考實作：**

```java
public class Priest extends RangedRole implements Healable {
    private int healPower;
    
    public Priest(String name, int health, int attackPower, 
                  int healPower, int range, int maxEnergy) {
        super(name, health, attackPower, range, maxEnergy);
        this.healPower = healPower;
    }
    
    @Override
    public void heal(Role target) {
        if (!consumeEnergy(15)) {
            System.out.println("能量不足！");
            return;
        }
        // 治療邏輯...
    }
    
    @Override
    public int getHealPower() {
        return healPower;
    }
    
    // 其他方法實作...
}
```

---

### 練習 4：判斷設計決策

**情境：** 要新增「群體治療」功能

**問題：** 應該放在哪裡？

A. Role 抽象類別
B. Healable 介面
C. 各個有治療能力的類別

**參考答案：** B. Healable 介面（作為預設方法）

**理由：**
```java
public interface Healable {
    void heal(Role target);
    int getHealPower();
    
    // 新增：群體治療（預設方法）
    default void healGroup(Role[] targets) {
        for (Role target : targets) {
            if (target != null && target.isAlive()) {
                heal(target);
            }
        }
    }
}
```

**好處：**
- 所有實作 Healable 的類別自動獲得群體治療
- 可以選擇性覆寫以優化
- 不影響現有程式碼

---

## 🎓 教學重點總結

### 介面的核心概念

```
介面 = 能力的契約

定義：「能做什麼」
實作：「怎麼做」

一個類別：
- 只能繼承一個父類別（is-a）
- 可以實作多個介面（can-do）
```

### 設計原則

1. **介面隔離原則**
   - 介面應該小而專注
   - Defendable 只管防禦
   - Healable 只管治療

2. **組合優於繼承**
   - 用介面組合能力
   - 比深層繼承更靈活

3. **開放封閉原則**
   - 新增介面不影響現有類別
   - 現有類別可選擇實作新介面

### 何時使用介面？

```
需要新增一個功能...
        ↓
   多個不相關的類別都需要？
        ↓
       是 → 使用介面
        ↓
   定義統一的行為規範
```

**範例：**
- 防禦能力：近戰和遠程都可能需要 → 介面
- 治療能力：魔法師和聖騎士都需要 → 介面
- 近戰特性：只有近戰角色需要 → 抽象類別

---

## 🚀 進階議題

### 介面與抽象類別的搭配使用

```java
// 最佳實踐：抽象類別定義骨架 + 介面定義能力

public abstract class Role { ... }           // 骨架
public abstract class MeleeRole { ... }      // 細化骨架

public interface Defendable { ... }          // 能力
public interface Healable { ... }            // 能力

public class Paladin extends MeleeRole       // 繼承骨架
                implements Defendable,        // 組合能力
                          Healable { ... }    // 組合能力
```

### 預設方法的衝突處理

```java
// 如果兩個介面有相同的預設方法：
public interface A {
    default void show() { 
        System.out.println("A"); 
    }
}

public interface B {
    default void show() { 
        System.out.println("B"); 
    }
}

// 實作類別必須明確覆寫
public class C implements A, B {
    @Override
    public void show() {
        A.super.show();  // 選擇使用 A 的實作
        // 或者
        B.super.show();  // 選擇使用 B 的實作
        // 或者
        // 自己的實作
    }
}
```

---

## 📦 檔案清單

```
Stage4_Interface/
├── Role.java              # 最高層抽象類別
├── MeleeRole.java         # 近戰角色抽象類別
├── RangedRole.java        # 遠程角色抽象類別
├── Defendable.java        # 防禦介面 ⭐ 新增
├── Healable.java          # 治療介面 ⭐ 新增
├── SwordsMan.java         # 劍士
├── ShieldSwordsMan.java   # 持盾劍士（實作 Defendable）
├── Magician.java          # 魔法師（實作 Healable）
├── Archer.java            # 弓箭手
├── Paladin.java           # 聖騎士（實作兩個介面）⭐ 新增
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

### Q1: 介面和抽象類別有什麼差別？

**A:** 
- **抽象類別**：描述「是什麼」(is-a)，只能繼承一個
- **介面**：描述「能做什麼」(can-do)，可以實作多個

### Q2: 為什麼介面不能有建構子？

**A:** 介面是行為規範，不是完整的類別。它只定義「要做什麼」，不涉及「如何創建物件」。

### Q3: 預設方法是必要的嗎？

**A:** 不是必要的，但很有用。預設方法讓介面可以提供基本實作，減少實作類別的負擔，同時保持向後相容性。

### Q4: 一個類別可以實作幾個介面？

**A:** 理論上沒有限制，但實務上建議不要超過 3-4 個，否則類別會變得過於複雜。

---

**版本：** Stage 4 - Interface  
**作者：** Chuck  
**更新日期：** 2024
