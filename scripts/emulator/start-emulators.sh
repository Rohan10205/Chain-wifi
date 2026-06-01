#!/usr/bin/env bash
set -euo pipefail

AVD_PREFIX=${1:-ChainNet}
COUNT=${2:-3}

for i in $(seq 1 "$COUNT"); do
  emulator -avd "${AVD_PREFIX}${i}" -netdelay none -netspeed full -no-snapshot-save &
  sleep 5
done

echo "Started ${COUNT} emulators using ${AVD_PREFIX} prefix."
