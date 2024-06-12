/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:linfo_pl/src/power/business/services/power_wake_lock_platform_interface.dart';

class PowerWakeLockController extends PowerWakeLockPlatformInterface {
  static PowerWakeLockController? _singleton;

  PowerWakeLockController._();

  factory PowerWakeLockController() => _singleton ??= PowerWakeLockController._();

  static PowerWakeLockPlatformInterface get _platform => PowerWakeLockPlatformInterface.instance;

  @override
  Future<bool> enableWakeLock() {
    return _platform.enableWakeLock();
  }

  @override
  Future<bool> disableWakeLock() {
    return _platform.disableWakeLock();
  }

  @override
  Future<bool?> getStateWakeLock() {
    return _platform.getStateWakeLock();
  }

  @override
  Future<bool?> schedulerWakeLock(double? minutes) {
    return _platform.schedulerWakeLock(minutes);
  }

  @override
  Future<bool?> schedulerWakeUp({double? minutes}) {
    return _platform.schedulerWakeUp(minutes: minutes);
  }

  @override
  Future<void> periodicWakeLock({
    int? repeat,
    double? minutes,
    double? waitMinutes}) {
    return _platform.periodicWakeLock(
      repeat: repeat,
      minutes: minutes,
      waitMinutes: waitMinutes,
    );
  }
}
