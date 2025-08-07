# Doritaas App

Doritaas is a modern Android application built with Jetpack Compose, designed to provide a seamless shopping and product browsing experience. The app features a clean UI, advanced filtering, authentication, and adaptive layouts for various device sizes.

---

## 🚀 Features

- **Animated Splash Screen**: Engaging entry animation with logo, app name, and progress indicator.
- **User Authentication**: Sign up, sign in, and password reset flows with real-time validation and error handling.
- **Product Catalog**: Browse products with grid/row views, detailed cards, and shimmer loading states.
- **Advanced Filtering**: Filter products by category, price range, rating, and sort options.
- **Search**: Powerful search with recent/popular items, live filtering, and no-results handling.
- **Adaptive UI**: Responsive layouts and scalable components for phones and tablets.
- **Custom Components**: Reusable UI elements (buttons, cards, forms, etc.) for consistent design.

---

## 🗂️ Project Structure

```
Doritaas-cursor/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/myapp/
│   │   │   │   ├── view/screens/         # Main UI screens (Splash, Auth, Products, Search, etc.)
│   │   │   │   ├── view/components/      # Custom reusable UI components
│   │   │   │   ├── ui/theme/             # Theming, colors, spacing, adaptive constants
│   │   │   │   ├── navigation/           # Navigation routes and logic
│   │   │   │   └── ...
│   │   │   ├── res/                      # Resources (drawables, layouts, values)
│   │   │   └── AndroidManifest.xml       # App manifest
│   ├── build.gradle.kts                  # App-level Gradle config
├── build.gradle.kts                      # Project-level Gradle config
├── settings.gradle.kts                    # Gradle settings
└── ...
```

---

## 🛠️ Tech Stack

- **Kotlin** (JVM 11)
- **Jetpack Compose** (UI toolkit)
- **Material 3** (Design system)
- **Coil** (Image loading)
- **Navigation Compose** (Navigation)
- **AndroidX** (Core libraries)

---

## 📝 Key Screens & Components

### 1. Animated Splash Screen
- Animated logo, app name, and progress indicator.
- Transitions to main app after a set duration.

### 2. Authentication
- **Sign Up**: Full name, email, password, terms agreement, social login options.
- **Sign In**: (Not shown above, but typically present.)
- **Forgot Password**: Email input, validation, and reset flow.

### 3. Product Catalog & Filtering
- **Product List**: Grid/row toggle, shimmer loading, adaptive columns.
- **Filter Section**: Category, price, rating, sort options, clear/reset.
- **Product Cards**: Detailed info, add to cart, favorite, etc.

### 4. Search
- Search bar with icons (camera, mic), recent/popular items, live results.
- No-results state with suggestions.

---

## 🧑‍💻 For Developers

- **Code Style**: Follows modern Compose best practices, with stateless/stateful composables, preview annotations, and clear separation of concerns.
- **Custom Theming**: Uses `LocalWindowSizeConstant` and custom spacing for adaptive UI.
- **Validation**: Real-time form validation for user input.
- **Navigation**: Modular navigation with route objects and argument passing.
- **Testing**: Includes test dependencies for UI and unit testing.

---

## 🏁 Getting Started

1. **Clone the repository**
2. **Open in Android Studio** (Giraffe or newer recommended)
3. **Sync Gradle**
4. **Run on emulator or device**

---

## 📂 Notable Files

- `app/src/main/AndroidManifest.xml` — App manifest and entry points
- `app/build.gradle.kts` — App-level dependencies and build config
- `app/src/main/java/com/example/myapp/view/screens/` — Main UI screens
- `app/src/main/java/com/example/myapp/view/components/` — Reusable UI components
- `app/src/main/java/com/example/myapp/ui/theme/` — Theming and adaptive constants

---

## 📢 Comments & Documentation

- All major composables and classes are documented with KDoc comments.
- Functions include parameter and usage descriptions.
- Data classes and enums are commented for clarity.
- The codebase is structured for readability and maintainability.

---

## 📧 Contact & Contribution

For questions, suggestions, or contributions, please open an issue or submit a pull request.

---

*This README was generated to provide a comprehensive overview of the Doritaas app, its structure, and its codebase documentation style.*

---

## 📝 License

This project is licensed under the UMUNTHU HUB Noncommercial License.

- **Noncommercial use is permitted.**
- **Commercial use requires a paid license.**

See the [LICENSE](./LICENSE) file for details and contact information.
