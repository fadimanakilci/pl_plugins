/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import '../domain/sources/battery_protocol.dart';

BatteryProtocol parseBatteryProtocols(String state) {
  switch (state.trim()) {
    case 'off':
      return BatteryProtocol.off;
    case 'on':
      return BatteryProtocol.on;
    case 'turningOff':
      return BatteryProtocol.turningOff;
    case 'turningOn':
      return BatteryProtocol.turningOn;
    case 'unknown':
      return BatteryProtocol.unknown;
    default:
      throw ArgumentError('$state is not a valid battery protocol.');
  }
}
