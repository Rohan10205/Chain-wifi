# ChainNet

ChainNet is a Kotlin/Android reference implementation for a chained peer-to-peer Wi‑Fi relay network. Each node runs a local-only hotspot while simultaneously connecting upstream as a Wi‑Fi STA, then relays traffic over an overlay/VPN and a control-plane protocol.

## Modules

- **app**: UI, foreground service, and Hilt wiring
- **core-domain**: models, repository contracts, and use cases
- **core-data**: Room, DataStore (Proto), and repository implementations
- **core-network**: Wi‑Fi managers, discovery, control-plane, routing, VPN/NAT, and security primitives

## Build & Run

```bash
./gradlew :app:assembleDebug
```

## Tests

```bash
./gradlew testDebugUnitTest
./gradlew :app:connectedDebugAndroidTest
```

## Emulator Multi‑Device Simulation (3 nodes)

The `scripts/emulator` directory includes ADB helpers to launch three emulators and seed them with distinct SSIDs, enabling manual validation of the chain topology and control plane.

## Notes

This project targets API 33 with a minimum of API 29 and uses Hilt, Coroutines/Flow, Room, DataStore, and Jetpack Compose throughout.
