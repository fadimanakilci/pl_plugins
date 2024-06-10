/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:linfo_pl/src/battery/domain/sources/charge_protocol.dart';
import 'package:linfo_pl/src/battery/presentation/controllers/battery_controller.dart';

import 'battery.dart';

export 'package:linfo_pl/src/battery/domain/sources/battery_protocol.dart' show BatteryProtocol;
export 'package:linfo_pl/src/battery/domain/sources/charge_protocol.dart' show ChargeProtocol;
export 'package:linfo_pl/src/battery/domain/models/battery_model.dart' show BatteryModel;

final _battery = BatteryController();

/// Returns a broadcast stream of events from the device battery at the
/// given sampling frequency.
@override
Stream<BatteryModel> batteryEventStream() {
  return _battery.batteryEventStream();
}

@override
Stream<int> batteryLevelStream() {
  return _battery.batteryLevelStream();
}

@override
Stream<ChargeProtocol> chargingStream() {
  return _battery.chargingStream();
}

@override
Future<int?> getBatteryLevel() {
  return _battery.getBatteryLevel();
}
