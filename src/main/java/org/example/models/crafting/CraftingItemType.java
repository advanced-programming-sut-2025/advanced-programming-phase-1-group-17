package org.example.models.crafting;

import org.example.models.BackPackable;
import org.example.models.BackPackableType;

import java.util.Map;

import static org.example.models.NormalItemType.*;
import static org.example.models.foraging.MineralType.*;
import static org.example.models.plant.SaplingType.*;

public enum CraftingItemType implements BackPackableType {
    CherryBomb(Map.of(CopperOre, 4, Coal, 1), "هرچیز در شعاع ۳ تایلی را نابود می کند", 50),
    Bomb(Map.of(IronOre, 4, Coal, 1), "هرچیز در شعاع ۵ تایلی را نابود می کند", 50),
    MegaBomb(Map.of(GoldOre, 4, Coal, 1), "هرچیز در شعاع ۷ تایلی را نابود می کند", 50),
    Sprinkler(Map.of(CopperBar, 1, IronBar, 1), "به ۴ تایل مجاور آب می‌دهد", 0),
    QualitySprinkler(Map.of(IronBar, 1, GoldBar, 1), "به ۸ تایل مجاور آب می‌دهد", 0),
    IridiumSprinkler(Map.of(GoldBar, 1, IridiumBar, 1), "به ۲۴ تایل مجاور آب می‌دهد", 0),
    CharcoalKlin(Map.of(Wood, 20, CopperBar, 2), "۱۰ چوب را تبدیل به ۱ ذغال می‌کند", 0),
    Furnace(Map.of(CopperOre, 20, Stone, 25), "کانی ها و ذغال را تبدیل به شمش می‌کند", 0),
    Scarecrow(Map.of(Wood, 50, Coal, 1, Fiber, 20), "از حمله کلاغ ها تا شعاع ۸ تایلی جلوگیری می‌کند", 0),
    DeluxeScarecrow(Map.of(Wood, 50, Coal, 1, Fiber, 20, IridiumOre, 1), "از حمله کلاغ ها تا شعاع 12 تایلی جلوگیری می‌کند", 0),
    BeeHouse(Map.of(Wood, 40, Coal, 8, IronBar, 1), "اگر در مزرعه گذاشته شود عسل تولید می‌کند", 0),
    CheesePress(Map.of(Wood, 45, Stone, 45, CopperBar, 1), "شیر را به پنیر تبدیل می‌کند", 0),
    Keg(Map.of(Wood, 30, CopperBar, 1, IronBar, 1), "میوه و سبزیجات را به نوشیدنی تبدیل می‌کند", 0),
    Loom(Map.of(Wood, 60, Fiber, 30), "پشم را به پارچه تبدیل می‌کند", 0),
    MayonnaiseMachine(Map.of(Wood, 15, Stone, 15, CopperBar, 1), "تخم مرغ را به سس مایونز تبدیل می‌کند", 0),
    OilMaker(Map.of(Wood, 100, GoldBar, 1, IronBar, 1), "truffle را به روغن تبدیل می‌کند", 0),
    PreservesJar(Map.of(Wood, 50, Stone, 40, Coal, 8), "سبزیجات را به ترشی و میوه ها را به مربا تبدیل می‌کند", 0),
    Dehydrator(Map.of(Wood, 30, Stone, 20, Fiber, 30), "میوه یا قارچ را خشک می‌کند", 0),
    FishSmoker(Map.of(Wood, 50, IronBar, 3, Coal, 10), "هر ماهی را با یک ذغال با حفظ کیفیتش تبدیل به ماهی دودی می‌کند", 0),
    MysticTreeSeed(Map.of(Acorns, 5, MapleSeeds, 5, PineCones, 5, MahoganySeeds, 5), "می‌تواند کاشته شود تا mystic tree رشد کند", 100);

    private final Map<BackPackableType, Integer> ingredients;
    private final String description;
    private final int sellPrice;

    CraftingItemType(Map<BackPackableType, Integer> ingredients, String description, int sellPrice) {
        this.ingredients = ingredients;
        this.description = description;
        this.sellPrice = sellPrice;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public double getPrice() {
        return sellPrice;
    }

    public static CraftingItemType getCraftingItemTypeByName(String artisanName) {
        for (CraftingItemType type : CraftingItemType.values()) {
            if (type.name().equalsIgnoreCase(artisanName)) {
                return type;
            }
        }
        return null;
    }

    public String getDescription() {
        return description;
    }

    public Map<BackPackableType, Integer> getIngredients() {
        return ingredients;
    }
}
