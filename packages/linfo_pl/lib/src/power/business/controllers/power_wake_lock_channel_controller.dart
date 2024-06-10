/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:flutter/services.dart';
import 'package:linfo_pl/src/power/business/services/power_wake_lock_platform_interface.dart';
import 'package:linfo_pl/src/utils/logging_util.dart';
import 'package:linfo_pl/src/utils/method_channel_util.dart';

class PowerWakeLockChannelController extends PowerWakeLockPlatformInterface {
  static const MethodChannel _methodChannel = MethodChannelUtil.methodChannel;

  @override
  Future<bool> enableWakeLock() async {
    bool result = await _methodChannel.invokeMethod('wakeLockEnable').then((value) => value);
    LoggingUtil.info('CHECK WAKE LOCK ENABLE: $result');
    return result;
  }

  @override
  Future<bool> disableWakeLock() async {
    bool result = await _methodChannel.invokeMethod('wakeLockDisable').then((value) => value);
    LoggingUtil.info('CHECK WAKE LOCK DISABLE: $result');
    return result;
  }

  @override
  Future<bool?> getStateWakeLock() async {
    bool? result = await _methodChannel.invokeMethod('wakeLockState').then((value) => value);
    LoggingUtil.info('CHECK WAKE LOCK STATE: $result');
    return result;
  }

  @override
  Future<bool?> schedulerWakeLock(double? minutes) async {

    Map<String, double?> arguments = {
      'minutes': minutes
    };

    bool? result = await _methodChannel.invokeMethod<bool>(
      'wakeLockScheduler',
      arguments,
    ).then((value) => value);

    LoggingUtil.info('CHECK WAKE LOCK SCHEDULER: $result');

    return result;
  }

  @override
  Future<void> periodicWakeLock({
    int? repeat,
    double? minutes,
    double? waitMinutes}) async {

    Map<String, dynamic> arguments = {
      'repeat': repeat,
      'minutes': minutes,
      'waitMinutes': waitMinutes,
    };

    await _methodChannel.invokeMethod<bool>(
      'wakeLockPeriodic',
      arguments,
    );

    LoggingUtil.info('CHECK WAKE LOCK PERIODIC END');
  }
}