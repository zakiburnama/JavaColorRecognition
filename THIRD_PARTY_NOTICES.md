# Third-Party Notices

This project includes or depends on the following third-party software.

## 1. Rubik's Cube model and solver logic — MIT License

Used in:

- `app/src/main/java/com/example/colorrecognitionjavaalpha/Cube.java` (modified)
- `app/src/main/java/com/example/colorrecognitionjavaalpha/Cubie.java`
- `app/src/main/java/com/example/colorrecognitionjavaalpha/CubieColor.java`

Original author: **Shoumyo Chakravorti** (Cube / Cubie / CubieColor, version 2.0).
Source: <https://github.com/SChakravorti21/Rubiks-Cube-Solver> (MIT License).

```
MIT License

Copyright (c) 2017 Shoumyo Chakravorti

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

Modifications in this project (Zaki Burnama): color input adapted to the OpenCV
scanner, Indonesian-language annotations, and adjustments to the solving steps for
the app's learning feature.

## 2. OpenCV 4.6.0 — Apache License 2.0

Located in `openCV/` (OpenCV Android SDK). Copyright (c) OpenCV team.
License: <https://www.apache.org/licenses/LICENSE-2.0>. The license files of the
bundled third-party components of OpenCV are in `openCV/etc/licenses/`.

## 3. Other dependencies

Used as Gradle dependencies (not copied into this repository), mostly under the
Apache License 2.0:

- AndroidX (AppCompat, ConstraintLayout) and Material Components for Android
- Firebase Realtime Database (Google; subject to the Firebase terms of service)
- Gradle Wrapper (`gradlew`, `gradlew.bat`) — Apache License 2.0
- Android Studio / Material icons (`baseline_*_24.xml`) — Apache License 2.0
