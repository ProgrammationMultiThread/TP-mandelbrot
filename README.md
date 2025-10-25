# Concurrent Multithreaded Programming – Mandelbrot (TP)

This repository contains the **starter code** for one of the hands-on sessions (*Travaux Pratiques*) of the *[Programmation Concurrente en Multi-Threads](https://github.com/ProgrammationMultiThread/)* course at **Nantes Université**.

---

## Overview

The goal of this practical session is to **parallelize the computation of the Mandelbrot set** — a well-known fractal defined in the complex plane.

Students will explore different strategies for parallel image rendering:
- Running the computation in a **separate thread** to keep the UI responsive.
- Splitting the image into **independent blocks** and computing each in parallel.
- Using a **thread pool** to control concurrency and resource usage.
- Measuring and comparing **execution times** for each approach.

All required classes are provided in the `src/main/java/mandelbrot/` package.  
The `Main` class includes a simple **sequential placeholder** implementation of the `Drawer` interface, which must be replaced by your own concurrent versions.

---

## Notes for students

- The provided code is **intentionally sequential** and blocks the Swing event thread.  
- Your task is to implement three concurrent versions of the `Drawer` interface:
  1. One that computes the image in a **background thread**.
  2. One that launches **one thread per image block**.
  3. One that uses a **fixed thread pool** with `n` worker threads.
- Each version should progressively render the image and update the display.

---

## How to open and run the project

Clone the repository:

```bash
git clone git@github.com:ProgrammationMultiThread/TP-Mandelbrot.git
```

Then import it as a **Maven project** in your IDE:
- **Eclipse** → *File → Import → Existing Maven Project*  
- **IntelliJ IDEA** → *Open → pom.xml*  
- **VS Code** → install the *Extension Pack for Java* and open the folder.

To compile and run manually from the command line:

```bash
mvn compile
mvn exec:java -Dexec.mainClass="mandelbrot.Main"
```

---

## Parameters and suggestions

You can adjust several parameters directly in `Main.java`:

| Parameter | Meaning | Suggested values |
|------------|----------|------------------|
| `threshold` | Max iterations per pixel (controls image detail) | 1 000 – 100 000 |
| `blockSize` | Width/height of one block in pixels (controls task granularity) | 100 – 300 |
| `palette` | Color scheme used for visualization | see `ColorPalette` |
| `area` | Subset of the complex plane to render | e.g. `Mandelbrot.fullSet()` or `Mandelbrot.sideOfCardioid()` |

The **sequential version** is deliberately slow — typically several seconds to minutes depending on your settings.  
After parallelization, you should observe a clear reduction in total execution time.

---

## Technical notes

- The program opens a **Swing window** (`Client`) that periodically refreshes the display.
- `Area.split(width, height)` divides the image into rectangular blocks, suitable for parallel processing.
- The computation of each pixel is done by `Mandelbrot.applyAsDouble(x, y)`, which returns either:
  - a positive integer (number of iterations before divergence), or  
  - `-1` for points belonging to the Mandelbrot set.
- The color mapping is handled by the `ColorPalette` functional interface.
- The code requires a **graphical environment** (not suitable for headless CI execution).

---

## License

All **Java source code and related project files** in this repository are distributed under the **MIT License**.

- The full license text is available in [`LICENSE.txt`](LICENSE.txt).  
- Organization-wide licensing details are provided in the [main license file](https://github.com/ProgrammationMultiThread/.github/blob/main/LICENSE.md).

This license applies only to the **original Java code** provided as part of the *Concurrent Multithreaded Programming* course at Nantes Université.

### Suggested attribution

> "Source code from the course *Programmation Concurrente en Multi-Threads* —  
> © 2025 Matthieu Perrin, licensed under the MIT License."
