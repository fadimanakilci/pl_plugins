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
import 'package:linfo_pl/src/battery/domain/sources/charge_protocol.dart';

import '../../../../battery.dart';
import '../../domain/sources/battery_protocol.dart';
import '../../utils/battery_protocol_parse.dart';
import '../../../utils/interval_util.dart';
import '../../../utils/logging_util.dart';
import '../../../utils/method_channel_util.dart';
import '../services/battery_platform_interface.dart';

class BatteryChannelController extends BatteryPlatformInterface {
  static Stream<BatteryModel>? _batteryEvent;
  static Stream<int>? _batteryLevel;
  static Stream<ChargeProtocol>? _charging;
  static const Duration _interval = IntervalUtil.normalInterval;
  static const MethodChannel _methodChannel = MethodChannelUtil.methodChannel;
  static const EventChannel _channel = MethodChannelUtil.batteryEventChannel;

  @override
  Stream<BatteryModel> batteryEventStream() {
    var microseconds = _interval.inMicroseconds;
    if (microseconds >= 1 && microseconds <= 3) {
      microseconds = 0;
    }

    _batteryEvent ??= _channel
        .receiveBroadcastStream().map((event) {
        LoggingUtil.info('Battery Event Cast: ${event}');

        return BatteryModel.fromMap(event);
      });

    return _batteryEvent!;
  }

  @override
  Stream<int> batteryLevelStream() {
    _batteryLevel ??= _channel
        .receiveBroadcastStream()
        .map((dynamic event) {
      LoggingUtil.info('Battery Level: $event');
      // final BatteryProtocol state = parseBatteryProtocols(event);
      //
      // if(state == BatteryProtocol.on){
      //   LoggingUtil.info('STATE: ON');
      //   // getInfo();
      // } else {
      //   LoggingUtil.info('STATE: ELSE');
      // }

      return event;
    });

    return _batteryLevel!;
  }

  @override
  Stream<ChargeProtocol> chargingStream() {
    _charging ??= _channel
        .receiveBroadcastStream()
        .map((dynamic event) {
      LoggingUtil.info('Battery charging: $event');
      // final BatteryProtocol state = parseBatteryProtocols(event);
      //
      // if(state == BatteryProtocol.on){
      //   LoggingUtil.info('STATE: ON');
      //   // getInfo();
      // } else {
      //   LoggingUtil.info('STATE: ELSE');
      // }

      return event;
    });

    return _charging!;  }

  @override
  Future<int?> getBatteryLevel() async {
    return await _methodChannel
        .invokeMethod<int?>('batteryLevel');
  }
}