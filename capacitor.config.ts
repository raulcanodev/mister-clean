import type { CapacitorConfig } from "@capacitor/cli";

const config: CapacitorConfig = {
  appId: "dev.raulcano.misterclean",
  appName: "Mister Clean",
  webDir: "www",
  server: {
    url: "https://mister.mundodeportivo.com/feed",
    allowNavigation: [
      "mister.mundodeportivo.com",
      "*.mundodeportivo.com"
    ]
  },
  ios: {
    contentInset: "always",
    backgroundColor: "#080b07"
  },
  android: {
    backgroundColor: "#080b07",
    allowMixedContent: false
  }
};

export default config;
