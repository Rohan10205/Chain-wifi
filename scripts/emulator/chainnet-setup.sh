#!/usr/bin/env bash
set -euo pipefail

DEVICES=$(adb devices | awk 'NR>1 && $2=="device" {print $1}')
INDEX=1
for device in $DEVICES; do
  echo "Configuring ${device} as node ${INDEX}"
  adb -s "${device}" shell settings put global wifi_on 1
  adb -s "${device}" shell svc wifi enable
  adb -s "${device}" shell settings put global airplane_mode_on 0
  adb -s "${device}" shell am start -n com.chainnet/.MainActivity
  INDEX=$((INDEX+1))
  sleep 2
done

echo "ChainNet launched on ${INDEX} devices. Use QR pairing to link nodes."
