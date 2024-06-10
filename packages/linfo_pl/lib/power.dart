/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:linfo_pl/src/power/domain/models/power_model.dart';
import 'package:linfo_pl/src/power/presentation/controllers/power_controller.dart';
import 'package:linfo_pl/src/power/presentation/controllers/power_thermal_controller.dart';
import 'package:linfo_pl/src/power/presentation/controllers/power_wake_lock_controller.dart';

import 'power.dart';

export 'package:linfo_pl/src/power/domain/models/power_model.dart' show PowerModel;
export 'package:linfo_pl/src/power/domain/sources/thermal_status_protocol.dart' show ThermalStatusProtocol;

final _power = PowerController();
final _powerThermal = PowerThermalController();
final _powerWakeLock = PowerWakeLockController();

@override
Stream<PowerModel> powerEventStream() {
  return _power.powerEventStream();
}

@override
Stream<ThermalStatusProtocol> powerThermalEventStream() {
  return _powerThermal.powerThermalEventStream();
}

@override
Future<ThermalStatusProtocol> getPowerThermalStatus() {
  return _powerThermal.getPowerThermalStatus();
}

@override
Future<bool> enableWakeLock() {
  return _powerWakeLock.enableWakeLock();
}

@override
Future<bool> disableWakeLock() {
  return _powerWakeLock.disableWakeLock();
}

@override
Future<bool?> getStateWakeLock() {
  return _powerWakeLock.getStateWakeLock();
}

@override
Future<bool?> schedulerWakeLock(double? minutes) {
  return _powerWakeLock.schedulerWakeLock(minutes);
}

@override
Future<void> periodicWakeLock({
  int? repeat,
  double? minutes,
  double? waitMinutes}) {
  return _powerWakeLock.periodicWakeLock(
    repeat: repeat,
    minutes: minutes,
    waitMinutes: waitMinutes,
  );
}
