/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:linfo_pl/src/power/business/controllers/power_wake_lock_channel_controller.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

abstract class PowerWakeLockPlatformInterface extends PlatformInterface {
  PowerWakeLockPlatformInterface() : super(token: _token);

  static final Object _token = Object();

  static PowerWakeLockPlatformInterface _instance = PowerWakeLockChannelController();

  static PowerWakeLockPlatformInterface get instance => _instance;

  static set instance(PowerWakeLockPlatformInterface instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<bool> enableWakeLock() {
    throw UnimplementedError(
        'enableWakeLock has not been implemented.');
  }

  Future<bool> disableWakeLock() {
    throw UnimplementedError(
        'disableWakeLock has not been implemented.');
  }

  Future<bool?> getStateWakeLock() {
    throw UnimplementedError(
        'getStateWakeLock has not been implemented.');
  }

  Future<bool?> schedulerWakeLock(double? minutes) {
    throw UnimplementedError(
        'schedulerWakeLock has not been implemented.');
  }

  Future<bool?> schedulerWakeUp({double? minutes}) {
    throw UnimplementedError(
        'schedulerWakeUp has not been implemented.');
  }

  Future<void> periodicWakeLock({
    int? repeat,
    double? minutes,
    double? waitMinutes}) {
    throw UnimplementedError(
        'periodicWakeLock has not been implemented.');
  }
}