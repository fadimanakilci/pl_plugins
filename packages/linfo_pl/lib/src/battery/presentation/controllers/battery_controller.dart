/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import '../../../../battery.dart';
import '../../business/services/battery_platform_interface.dart';
import '../../domain/sources/battery_protocol.dart';
import '../../domain/sources/charge_protocol.dart';

class BatteryController extends BatteryPlatformInterface {
  static BatteryController? _singleton;

  BatteryController._();

  factory BatteryController() => _singleton ??= BatteryController._();

  static BatteryPlatformInterface get _platform => BatteryPlatformInterface.instance;

  /// Returns a broadcast stream of events from the device battery at the
  /// given sampling frequency.
  @override
  Stream<BatteryModel> batteryEventStream() {
    return _platform.batteryEventStream();
  }

  @override
  Stream<int> batteryLevelStream() {
    return _platform.batteryLevelStream();
  }

  @override
  Stream<ChargeProtocol> chargingStream() {
    return _platform.chargingStream();
  }

  @override
  Future<int?> getBatteryLevel() {
    return _platform.getBatteryLevel();
  }
}