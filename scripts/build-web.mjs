import { cp, mkdir } from "node:fs/promises";

await mkdir(new URL("../www/", import.meta.url), { recursive: true });
await cp(
  new URL("../assets/icon.png", import.meta.url),
  new URL("../www/icon.png", import.meta.url)
);
