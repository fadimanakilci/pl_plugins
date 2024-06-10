/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:linfo_pl/bluetooth.dart';

BluetoothProtocol parseBluetoothProtocols(String state) {
    switch (state.trim()) {
      case 'off':
        return BluetoothProtocol.off;
      case 'on':
        return BluetoothProtocol.on;
      case 'turningOff':
        return BluetoothProtocol.turningOff;
      case 'turningOn':
        return BluetoothProtocol.turningOn;
      default:
        return BluetoothProtocol.unknown;
    }
}