/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';
import 'package:linfo_pl/src/bluetooth/business/services/bluetooth_platform_interface.dart';
import 'package:linfo_pl/src/bluetooth/utils/bluetooth_protocol_parse_util.dart';

import '../../../../bluetooth.dart';
import '../../../utils/interval_util.dart';
import '../../../utils/logging_util.dart';
import '../../../utils/method_channel_util.dart';

class BluetoothChannelController extends BluetoothPlatformInterface {
  static Stream<BluetoothProtocol>? _bluetoothEvent;
  static const Duration _interval = IntervalUtil.normalInterval;
  static const MethodChannel _methodChannel = MethodChannelUtil.methodChannel;
  static const EventChannel _channel = MethodChannelUtil.bluetoothEventChannel;

  @override
  Stream<BluetoothProtocol> bluetoothEventStream() {
    var microseconds = _interval.inMicroseconds;
    if (microseconds >= 1 && microseconds <= 3) {
      LoggingUtil.warning('The SamplingPeriod is currently set to $microsecondsμs, '
          'which is a reserved value in Android. Please consider changing it '
          'to either 0 or 4μs. See https://developer.android.com/reference/'
          'android/hardware/SensorManager#registerListener(android.hardware.'
          'SensorEventListener,%20android.hardware.Sensor,%20int) for more '
          'information');
      microseconds = 0;
    }

    _bluetoothEvent ??= _channel
        .receiveBroadcastStream()
        .map((dynamic event) {
      LoggingUtil.info('Bluetooth Event Cast: $event');
      final BluetoothProtocol state = parseBluetoothProtocols(event);

      if(state == BluetoothProtocol.on){
        LoggingUtil.info('STATE: ON');
        // getInfo();
      } else {
        LoggingUtil.info('STATE: ELSE');
      }

      return state;
    });

    return _bluetoothEvent!;
  }

  @override
  Future<BluetoothProtocol> getBluetoothEvent() async {
    BluetoothProtocol state = parseBluetoothProtocols((await _methodChannel
        .invokeMethod<String>('bluetoothState'))!);

    LoggingUtil.info('BLUETOOTH STATE: $state');

    return state;
  }

  @override
  Future<bool> bluetoothStateOn() async {
    return await _methodChannel.invokeMethod('bluetoothStateOn');
  }

  @override
  Future<bool> bluetoothStateOff() async {
    return await _methodChannel.invokeMethod('bluetoothStateOff');
  }
}
