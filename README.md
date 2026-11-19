In this challenge, you will create new functionality.

This is a complex task that consists of 2 parts:

1. Build UI according to
   the [Design](https://www.figma.com/file/ghMRg4HaI9l90gFxKwe8LA/Task-Junior-Android?node-id=0%3A1)
   using the Jetpack Compose framework
2. Implement logic to get and display data from the ApiVendors following MVVM architecture

Notes:

- NetworkCategory object contains the image of type .svg
- NetworkVendor object contains images of type .png
- In this project, you will be used mocked data instead of an HTTP request. You can find mocked data
  in the vendors.json file inside the assets resource folder. We decided to simplify this challenge
  to save some time for you and we have implemented mocked data source that you will use to get
  display data. You can find the mocked data source implementation inside the "network" folder. The
  ApiVendors is the data source interface to collect data and the NetworkDataProvider is the
  implementation.
- You should use the latest [stable Android Studio](https://developer.android.com/studio)

---

## Solution notes

> Added by the candidate on top of the provided scaffold.

### Approach

- **UI** — Jetpack Compose. A single `VendorsScreen` built from the Figma "Cards" frame
  (vendor card: cover photo, area-served label, saved/bookmark badge, 2-per-row category
  grid, wrapping tags).
- **Architecture** — MVVM with a unidirectional data flow. `VendorsVM` exposes an immutable
  `VendorsScreenUiState` modelled as a **sealed interface** (`Loading / Content / Empty /
  Error`) via a `StateFlow`.
- **Data flow** — `assets/vendors.json` → `NetworkDataProvider` → `VendorMapper` (DTO → domain)
  → `VendorRepository` (cached) → `VendorsVM` → UI.
- **Search** — derived reactively by `combine`-ing the loaded list with a **debounced** query
  flow, so no coroutine is launched per keystroke.
- **Images** — Coil `AsyncImage` (PNG/JPEG covers + SVG category icons). They are loaded from a
  remote staging CDN, so **internet is required** (`INTERNET` permission).
- **DI** — Hilt, processed with **KSP**: `@HiltAndroidApp`, `@AndroidEntryPoint`, `@HiltViewModel`,
  and an `AppModule` providing `ApiVendors` (IO dispatcher) and `VendorRepository`. The ViewModel
  is obtained via `by viewModels()`.
- **Tests** — unit tests for the mapper, the repository (caching + filtering) and the
  ViewModel's state emissions.

### Scope decision — the bookmark

The bookmark on each card is a **data-driven indicator of the "saved" state**: it reads
`favorited` from the data (green filled = saved, outline = not saved). The provided design
frame shows only these two visual states — no tap interaction and no "Saved" screen — so the
bookmark is intentionally kept **display-only**, to stay faithful to the design and the task
scope rather than add unspecified behaviour.

### Possible improvements (closest-to-scope → nice-to-have)

1. Make the bookmark **tappable** to toggle the saved state (local state in the ViewModel).
2. **Persist** saved vendors (DataStore / Room) so the state survives app restarts.
3. A **"Saved" filter / tab** to show only saved vendors.
4. Replace the mocked asset with a real **network layer** (Retrofit) behind the existing
   `ApiVendors` interface.
5. Loading UX — skeleton / shimmer placeholders and pull-to-refresh instead of a spinner.
6. A vendor **detail screen** via Navigation-Compose.
7. Migrate to **Material 3** + a dark theme.
8. More tests (Compose UI tests) and **CI** (ktlint / detekt + unit tests on each push).

### Tech note

The toolchain was modernised from the original scaffold — **AGP 8.13, Gradle 8.13, Kotlin 2.0
with the Compose Compiler Gradle plugin, Compose 1.6.8, compileSdk 34 and JVM target 17**. That
also unblocked **Hilt**: the original AGP 7.3.1 + bundled R8 could not dex Hilt's instrumented
output under JDK 21, whereas AGP 8's newer R8 handles it cleanly.
  
