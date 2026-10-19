# Mister Clean

iOS and Android WebView shell for `mister.mundodeportivo.com`.

The app loads Mister as its top-level document, so authentication cookies stay on the device and are first-party. Native code blocks known advertising hosts and injects `filters/mister.css` after navigation to remove cosmetic ad containers and place the sticky footer at the bottom.

## Set up

```sh
npm install
npx cap add ios --packagemanager SPM
npx cap add android
npm run sync
```

## Build

- iOS: `npm run open:ios`, choose your free Personal Team and run it on your own device. Free provisioning expires after 7 days, so reconnect the phone and run it again when needed.
- Android: `npm run open:android`, then build an APK or App Bundle from Android Studio.

## Authentication limitation

Google does not allow OAuth authorization inside embedded WebViews. Mister's Google button would open the system browser, whose cookies are isolated from this app, and leave the in-app button loading forever. Mister Clean therefore intercepts that button. When Mister has already identified the account email, it offers to open Mister's own password-recovery form with that address prefilled so the user can try adding an email password to the social account. Nothing is submitted automatically. If Mister does not allow a password for that account, native Google sign-in still requires Mister to register this app's package and signing certificate in its Google project. The app must not imitate a browser user agent or copy authentication cookies.

Run `npm run sync` whenever the filters change. The sync script regenerates the native filter sources before Capacitor synchronizes the projects.

## Limitations

- Mister can change its markup or ad providers. Update `filters/mister.css` and `filters/ad-hosts.json`, then sync again.
- App Store review may reject apps that are only wrappers around a website. Direct Xcode installation and APK distribution are the intended test channels.
- This project is not affiliated with Mister or Mundo Deportivo. Check their terms before distributing it.
