# 🍃 LibGdx-Skin
Custom Skin for libGDX UI designed with [Skin Composer](https://github.com/raeleus/skin-composer) (for Stardew Valley)

---

## 🎨 Available Styles

### ✅ Buttons
This skin supports **4 unique `TextButtonStyle`s**, each with a themed logo word embedded in the button:

- `Earth` 🌍
- `Strawberry` 🍓
- `Chicken` 🐔
- `Plant` 🌱

These styles have visual icons/logos in the button texture, matching the name and theme.

> ➡️ Important: When using any of these styles, set the button's width to 240 (یا بیشتر تهخ) to preserve the correct visual shape and alignment.

### ✅ Labels
This skin provides **4 font styles** usable via `LabelStyle`:

- `WhiteText` (default – white colored font)
- `Impact` 
- `BoldImpact` 
- `Bold`

> 🎨 You can override the color of `WhiteText` in your code if needed.

### ✅ Windows
This skin includes one custom `WindowStyle`:

- `Letter` 📜

A stylized window UI resembling an old letter or parchment.
Great for dialog boxes, quest messages, or pop-up panels.

---

## ⚠️ Not Included Styles

This skin does **not include** the following libGDX widget styles:

- `ButtonStyle`
- `ImageTextButtonStyle`
- `SplitPaneStyle`
- `TouchpadStyle`
- `TreeStyle`

📌 Make sure **not to reference these** styles in your UI JSON or code unless you define them yourself, or libGDX may throw a `NullPointerException`.

---

## 🗣 Feedback & Issues
If you encounter any bugs, or want to request improvements,
feel free to open an Issue on the GitHub Issues page.
I'll try to fix or update it as soon as possible. 🙂

## 📁 How to Use

1. Copy all files (`uiskin.json`, `.atlas`, fonts, images...) to your `assets/skin/` folder.
2. Load the skin in your game:

```java
Skin skin = new Skin(Gdx.files.internal("Skin/StardewSkin.json"));



