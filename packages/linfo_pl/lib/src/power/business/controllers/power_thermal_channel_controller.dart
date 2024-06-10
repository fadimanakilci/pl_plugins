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
import 'package:linfo_pl/power.dart';
import 'package:linfo_pl/src/power/utils/thermal_status_protocol_parse.dart';

import '../../../utils/logging_util.dart';
import '../../../utils/method_channel_util.dart';
import '../services/power_thermal_platform_interface.dart';

class PowerThermalChannelController extends PowerThermalPlatformInterface {
  static Stream<ThermalStatusProtocol>? _thermalEvent;
  static const MethodChannel _methodChannel = MethodChannelUtil.methodChannel;
  static const EventChannel _channel = MethodChannelUtil.powerThermalEventChannel;

  @override
  Stream<ThermalStatusProtocol> powerThermalEventStream() {
    _thermalEvent ??= _channel
        .receiveBroadcastStream().map((event) {
      LoggingUtil.info('Thermal Event Cast: $event');
      return parseThermalStatusProtocols(event);
    });

    return _thermalEvent!;
  }

  @override
  Future<ThermalStatusProtocol> getPowerThermalStatus() async {
    ThermalStatusProtocol result = await _methodChannel
        .invokeMethod<String>('powerThermalStatus')
        .then((value) => parseThermalStatusProtocols(value));
    LoggingUtil.info('CHECK THERMAL STATUS: ${result.name}');

    return result;
  }
}